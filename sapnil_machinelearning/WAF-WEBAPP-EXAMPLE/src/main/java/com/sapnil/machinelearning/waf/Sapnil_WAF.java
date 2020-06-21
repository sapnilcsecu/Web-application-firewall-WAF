/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapnil.machinelearning.waf;

import com.alibaba.fastjson.JSONArray;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.sapnil.machinelearning.model.Train_model_param;
import com.sapnil.machinelearning.utility.Encode_detection;
import com.sapnil.machinelearning.utility.Utility;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

/**
 *
 * @author Nasir uddin
 */
public class Sapnil_WAF {

    public static boolean detect_verna_param(HttpServletRequest request, String param_file_path) {
        boolean is_vernable = false;
        Process p = null;
        try {

            //Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import live_verna_detection; live_verna_detection=live_verna_detection('D:/bitbuket sapnil machinelearning/sapnil_machinelearning/sapnil_machinelearning','"+user_name+"');print(json.dumps([str(live_verna_detection)]))"});
            HttpSession session = request.getSession(true);
            String context_path = session.getServletContext().getRealPath("/").replace(File.separator, "/");

            String file_path = context_path + param_file_path;
            // System.out.println("context pathr is " + context_path);

            p = Runtime.getRuntime().exec(new String[]{"python", "-c", "from classifier.train_model import live_verna_detection; live_verna_detection=live_verna_detection('" + context_path + "','" + file_path + "','payload');print(live_verna_detection)"});
            p.waitFor();

            String stdout = IOUtils.toString(p.getInputStream());
            System.out.println("versify_result1 is 11 " + stdout);
            if (stdout == null) {
                p.destroy();
                return true;
            }
            JSONArray syspathRaw = JSONArray.parseArray(stdout);
            String versify_result1 = "";
            if (syspathRaw == null) {
                p.destroy();
                return true;
            }
            for (int i = 0; i < syspathRaw.size(); i++) {
                versify_result1 = syspathRaw.getString(i);

                if (versify_result1.equals("anom")) {
                    System.out.println("param is anom ");
                    is_vernable = true;
                }
            }

        } catch (Exception ex) {
            System.out.println("the exception is " + ex.getMessage());
            p.destroy();
            return true;
        } finally {
            p.destroy();
        }

        return is_vernable;
    }

    public static String write_append_model(Train_model_param param_ob) {

        String accuracy = "";
        String final_input_dataset_path = param_ob.getInput_dataset_path() + param_ob.getInput_dataset_filename();
        try {

            if (param_ob.getMod_of_tran().equals("NEW TRAINING MODEL")) {

                // System.out.println("input_dataset_path is " + input_dataset_path);
                Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import train_model_write; accuracy_score=train_model_write('" + final_input_dataset_path + "','" + param_ob.getInput_dataset_path() + "','" + param_ob.getPayload_name() + "','" + param_ob.getPayload_label() + "','write'); print(json.dumps([str(accuracy_score)]))"});

                p.waitFor();
                String stdout = IOUtils.toString(p.getInputStream());
                System.out.println("the stdout is " + stdout);
                JSONArray syspathRaw = JSONArray.parseArray(stdout);
                for (int i = 0; i < syspathRaw.size(); i++) {
                    accuracy = syspathRaw.getString(i);

                }
            } else if (param_ob.getMod_of_tran().equals("APPEND TRAINING MODEL")) {
                Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import train_model_write; accuracy_score=train_model_write('" + final_input_dataset_path + "','" + param_ob.getInput_dataset_path() + "','" + param_ob.getPayload_name() + "','" + param_ob.getPayload_label() + "','append'); print(json.dumps([str(accuracy_score)]))"});

                p.waitFor();
                String stdout = IOUtils.toString(p.getInputStream());
                System.out.println("the stdout is " + stdout);
                JSONArray syspathRaw = JSONArray.parseArray(stdout);
                for (int i = 0; i < syspathRaw.size(); i++) {
                    accuracy = syspathRaw.getString(i);

                }
            }
        } catch (Exception e) {
            // exception handling
            e.printStackTrace();
        }

        return accuracy;
    }

    public static List<String[]> bulktest(Train_model_param param_ob) {
       /* try {
            Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "from classifier.train_model import bulk_live_verna_detection; live_verna_detection1=bulk_live_verna_detection('E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/target/WAF-WEBAPP-EXAMPLE-1.0-SNAPSHOT/xss_payload_33.csv','E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/target/WAF-WEBAPP-EXAMPLE-1.0-SNAPSHOT/','payload','label');print(live_verna_detection1)"});
            p.waitFor();
            String stdout = IOUtils.toString(p.getInputStream());
            System.out.println("stdout result is ----" + stdout);
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/

           
       ArrayList<String[]> result_list = new ArrayList<String[]>();
        try {
            String final_input_dataset_path = param_ob.getInput_dataset_path() + param_ob.getInput_dataset_filename();
            CSVReader reader = new CSVReader(new FileReader(final_input_dataset_path));
            String readFilerow;

            int payload_index = 0, label_index = 0;
            ArrayList<String> payload_list = new ArrayList<String>();
            ArrayList<String> label_list = new ArrayList<String>();
            int co = 0;
            String[] line;
            while ((line = reader.readNext()) != null) {
                List<String> wordList = Arrays.asList(line);
                if (co == 0) {
                    payload_index = wordList.indexOf(param_ob.getPayload_name());
                    label_index = wordList.indexOf(param_ob.getPayload_label());

                    ++co;
                    continue;
                }
                payload_list.add(wordList.get(payload_index));
                label_list.add(wordList.get(label_index));
            }

            //Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import bulk_live_verna_detection; bulk_verna_detect_result=bulk_live_verna_detection('" + final_input_dataset_path + "','" + param_ob.getInput_dataset_path().replace(File.separator, "/") + "','" + param_ob.getPayload_name() + "','" + param_ob.getPayload_label() + "');print(bulk_verna_detect_result)"});
            Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "from classifier.train_model import bulk_live_verna_detection; live_verna_detection1=bulk_live_verna_detection('" + final_input_dataset_path + "','"+param_ob.getInput_dataset_path().replace(File.separator, "/")+"','"+param_ob.getPayload_name()+"','"+param_ob.getPayload_label()+"');print(live_verna_detection1)"});
            p.waitFor();
            String stdout = IOUtils.toString(p.getInputStream());
            System.out.println("stdout result is ----" + stdout);
            JSONArray syspathRaw = JSONArray.parseArray(stdout);

            result_list.add(new String[]{"payload_name", "payload_label"});

            String versify_result1 = "";
            //CSVWriter csvWriter = new CSVWriter(new FileWriter("new.csv"), ",", "'","/", "\n");
            for (int i = 0; i < syspathRaw.size(); i++) {
                versify_result1 = syspathRaw.getString(i);
                if (!label_list.get(i).trim().equals(versify_result1.trim())) {
                    System.out.println(label_list.get(i).trim());
                    result_list.add(new String[]{payload_list.get(i), versify_result1});

                } else {
                    System.out.println("match");
                }

                // System.out.println("versify_result1 result is ----" + versify_result1);
            }

        } catch (Exception e) {
            // exception handling
            e.printStackTrace();
        }

        return result_list;
        //return null;
    }

}
