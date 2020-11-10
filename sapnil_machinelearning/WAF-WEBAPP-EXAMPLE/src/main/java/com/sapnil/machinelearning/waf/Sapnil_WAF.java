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
import java.io.FileWriter;
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
            String model_path = session.getServletContext().getRealPath("/").replace(File.separator, "/") + "train_model/";
            String context_path = session.getServletContext().getRealPath("/").replace(File.separator, "/");
            String file_path = context_path + param_file_path;
            System.out.println("context pathr is " + model_path);
            System.out.println("file_path is " + file_path);
            p = Runtime.getRuntime().exec(new String[]{"python", "-c", "from classifier.train_model import live_verna_detection; live_verna_detection=live_verna_detection('" + model_path + "','" + file_path + "','payload');print(live_verna_detection)"});
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

                if (versify_result1.equals("true")) {
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
        Process p = null;
        String accuracy = "";
        String final_input_dataset_path = param_ob.getInput_dataset_path() + param_ob.getInput_dataset_filename();
        String train_model_path = param_ob.getInput_dataset_path() + "train_model/";

        try {

            if (param_ob.getMod_of_tran().equals("NEW TRAINING MODEL")) {
                CSVReader reader = new CSVReader(new FileReader(final_input_dataset_path));
                CSVReader reader1 = new CSVReader(new FileReader(param_ob.getInput_dataset_path() + "normdatapayload.csv"));
                //String csv = "cmd_payload.csv";
                ArrayList<String[]> result_list = new ArrayList<String[]>();
                result_list.add(new String[]{"payload", "label"});
                String input_file_path=param_ob.getInput_dataset_path() + param_ob.getInput_dataset_filename() + "inputdataset.csv";
                CSVWriter writer = new CSVWriter(new FileWriter(input_file_path));

                writer.writeAll(result_list);
                String[] record = null;
                int count = 0;
                while ((record = reader1.readNext()) != null) {
                    // System.out.println("payload " + record[0]);
                    // System.out.println("label " + record[1]);
                    if (count == 0) {
                        ++count;
                        continue;
                    }
                    writer.writeNext(record);

                    ++count;

                }

                int count1 = 0;
                while ((record = reader.readNext()) != null) {
                    // System.out.println("payload " + record[0]);
                    // System.out.println("label " + record[1]);
                    if (count1 == 0) {
                        ++count1;
                        continue;
                    }
                    writer.writeNext(record);

                    ++count;

                }

                reader.close();
                reader1.close();
                writer.close();

                System.out.println("param_ob.getInput_dataset_path() is " + param_ob.getInput_dataset_path());
                //System.out.println("train_model_path is " + train_model_path);
                //train_model_write('E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/target/WAF-WEBAPP-EXAMPLE-1.0-SNAPSHOT/Command Injection.csv','E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/target/WAF-WEBAPP-EXAMPLE-1.0-SNAPSHOT/train_model/',"payload","label")
                //p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import train_model_write; accuracy_score=train_model_write('" + param_ob.getInput_dataset_path() + "','" + train_model_path + "','" + param_ob.getPayload_name() + "','" + param_ob.getPayload_label() + "'); print(json.dumps([str(accuracy_score)]))"});
                //p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import train_model_write; accuracy_score=train_model_write('E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/target/WAF-WEBAPP-EXAMPLE-1.0-SNAPSHOT/Command Injection.csv','E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/target/WAF-WEBAPP-EXAMPLE-1.0-SNAPSHOT/train_model/','payload','label'); print(json.dumps([str(accuracy_score)]))"});
                p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import train_model_write; accuracy_score=train_model_write('" +input_file_path+ "','" + train_model_path + "','payload','label'); print(json.dumps([str(accuracy_score)]))"});

                p.waitFor();
                String stdout = IOUtils.toString(p.getInputStream());
                System.out.println("the stdout is " + stdout);
                JSONArray syspathRaw = JSONArray.parseArray(stdout);
                for (int i = 0; i < syspathRaw.size(); i++) {
                    accuracy = syspathRaw.getString(i);

                }
            }
            /*else if (param_ob.getMod_of_tran().equals("APPEND TRAINING MODEL")) {
                p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import train_model_write; accuracy_score=train_model_write('" + final_input_dataset_path + "','" + param_ob.getInput_dataset_path() + "','" + param_ob.getPayload_name() + "','" + param_ob.getPayload_label() + "','append'); print(json.dumps([str(accuracy_score)]))"});

                p.waitFor();
                String stdout = IOUtils.toString(p.getInputStream());
                System.out.println("the stdout is " + stdout);
                JSONArray syspathRaw = JSONArray.parseArray(stdout);
                for (int i = 0; i < syspathRaw.size(); i++) {
                    accuracy = syspathRaw.getString(i);

                }
            }*/
        } catch (Exception e) {
            // exception handling
            p.destroy();
            e.printStackTrace();
        } finally {
           // Utility.del_file(param_ob.getInput_dataset_path() + param_ob.getInput_dataset_filename() + "inputdataset.csv");
          //  p.destroy();
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
        Process p = null;
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
            p = Runtime.getRuntime().exec(new String[]{"python", "-c", "from classifier.train_model import bulk_live_verna_detection; live_verna_detection1=bulk_live_verna_detection('" + final_input_dataset_path + "','" + param_ob.getInput_dataset_path().replace(File.separator, "/") + "','" + param_ob.getPayload_name() + "','" + param_ob.getPayload_label() + "');print(live_verna_detection1)"});
            p.waitFor();
            String stdout = IOUtils.toString(p.getInputStream());
            System.out.println("stdout result is ----" + stdout);
            JSONArray syspathRaw = JSONArray.parseArray(stdout);

            result_list.add(new String[]{"payload_name", "payload_label"});

            String versify_result1 = "";
            //CSVWriter csvWriter = new CSVWriter(new FileWriter("new.csv"), ",", "'","/", "\n");
            System.out.println("the label size is " + label_list.size());
            System.out.println("the result array size is " + syspathRaw.size());
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
            p.destroy();
            e.printStackTrace();
        }
        p.destroy();
        return result_list;
        //return null;
    }

}
