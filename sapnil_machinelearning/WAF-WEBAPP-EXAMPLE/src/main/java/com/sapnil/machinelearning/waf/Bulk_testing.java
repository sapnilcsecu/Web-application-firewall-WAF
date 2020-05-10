/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapnil.machinelearning.waf;

import com.alibaba.fastjson.JSONArray;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

/**
 *
 * @author Nasir(programmer)
 */
public class Bulk_testing extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            JSONObject jsonObject = new JSONObject();
            String payload_name = "payload", payload_label = "label", input_dataset_name = "", mod_of_tran = "";
            HttpSession session = request.getSession(true);
            String context_path = session.getServletContext().getRealPath("/");
            String input_dataset_path = context_path.replace(File.separator, "/");
            String final_input_dataset_path = "";
            if (ServletFileUpload.isMultipartContent(request)) {
                try {
                    List<FileItem> multiparts = new ServletFileUpload(
                            new DiskFileItemFactory()).parseRequest(request);
                    for (FileItem item : multiparts) {
                        if (!item.isFormField()) {
                            System.out.println("file data ");

                            input_dataset_name = new File(item.getName()).getName();
                            final_input_dataset_path = input_dataset_path + input_dataset_name;
                            item.write(new File(final_input_dataset_path));
                        } else {
                            System.out.println("not file data ");
                            if (item.getFieldName().equals("payload_name")) {
                                payload_name = item.getString();
                                System.out.println("payload_name is " + payload_name);
                            } else if (item.getFieldName().equals("payload_label")) {
                                payload_label = item.getString();
                                System.out.println("payload_label is " + payload_label);
                            }

                        }

                    }
                    // payload_name = "payload";
                    //payload_label = "label";
                    //BufferedReader readFile = new BufferedReader(new FileReader(final_input_dataset_path));
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
                            payload_index = wordList.indexOf(payload_name);
                            label_index = wordList.indexOf(payload_label);

                            ++co;
                            continue;
                        }
                        payload_list.add(wordList.get(payload_index));
                        label_list.add(wordList.get(label_index));
                    }
                    // System.out.println("count is "+co);
                    /* if (co == 0) {
                            payload_index = wordList.indexOf(payload_name);
                            label_index = wordList.indexOf(payload_label);
                            ++co;
                            continue;
                        }
                        ++co;
                        String payloadis = wordList.get(payload_index);
                        System.out.println("wordList.get(payload_index) result is ----" + payloadis);
                        Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import live_verna_detection; live_verna_detection=live_verna_detection('" + context_path.replace(File.separator, "/") + "','" + payloadis + "');print(json.dumps([str(live_verna_detection)]))"});
                        p.waitFor();
                        String stdout = IOUtils.toString(p.getInputStream());
                        System.out.println("stdout result is ----" + stdout);
                        JSONArray syspathRaw = JSONArray.parseArray(stdout);
                        String versify_result1 = "";
                        for (int i = 0; i < syspathRaw.size(); i++) {
                            versify_result1 = syspathRaw.getString(i);
                            System.out.println("versify_result1 result is ----" + versify_result1);

                        }

                        if (!wordList.get(label_index).equals(versify_result1)) {

                            rows.add("" + wordList.get(payload_index) + "," + wordList.get(label_index) + "");
                            rows.add("\n");
                        }

                        //System.out.println(data[0] + "|" + data[1] + "|" + data[2]);
                    }

                    readFile.close();*/
                    Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import bulk_live_verna_detection; bulk_verna_detect_result=bulk_live_verna_detection('" + final_input_dataset_path + "','" + context_path.replace(File.separator, "/") + "','" + payload_name + "','" + payload_label + "');print(bulk_verna_detect_result)"});

                    //Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import bulk_live_verna_detection; bulk_verna_detect_result=bulk_live_verna_detection('E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/src/main/webapp/xss_payload_33.csv','E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/src/main/webapp/','payload','label');print(bulk_verna_detect_result)"});
                    p.waitFor();
                    String stdout = IOUtils.toString(p.getInputStream());
                    System.out.println("stdout result is ----" + stdout);
                    JSONArray syspathRaw = JSONArray.parseArray(stdout);
                    ArrayList<String[]> result_list = new ArrayList<String[]>();
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

                    /* response.setContentType("text/csv");
                    String reportName = "GenerateCSV_Report_"
                            + System.currentTimeMillis() + ".csv";
                    response.setHeader("Content-disposition", "attachment; "
                            + "filename=" + reportName);

                    Iterator<String> iter = result_list.iterator();
                    while (iter.hasNext()) {
                        String outputString = (String) iter.next();
                        response.getOutputStream().print(outputString);
                    }
                    OutputStreamWriter osw = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
                    CSVWriter writer = new CSVWriter(osw,CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
                    response.getOutputStream().flush();*/
                    response.setContentType("text/csv");
                    String reportName = "GenerateCSV_Report_"
                            + System.currentTimeMillis() + ".csv";
                    response.setHeader("Content-disposition", "attachment; "
                            + "filename=" + reportName);

                    BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
                    CSVWriter writer = new CSVWriter(buff);
                    writer.writeAll(result_list);
                    writer.close();

                } catch (Exception e) {
                    // exception handling
                    e.printStackTrace();
                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
