/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapnil.machinelearning.waf;

import com.alibaba.fastjson.JSONArray;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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
            String payload_name = "", payload_label = "", input_dataset_name = "", mod_of_tran = "";
            HttpSession session = request.getSession(true);
            String context_path = session.getServletContext().getRealPath("/");
            String input_dataset_path = context_path.replace(File.separator, "/");
            String final_input_dataset_path = input_dataset_path + input_dataset_name;
            if (ServletFileUpload.isMultipartContent(request)) {
                try {
                    List<FileItem> multiparts = new ServletFileUpload(
                            new DiskFileItemFactory()).parseRequest(request);
                    for (FileItem item : multiparts) {
                        if (!item.isFormField()) {
                            System.out.println("file data ");

                            input_dataset_name = new File(item.getName()).getName();
                            item.write(new File(final_input_dataset_path));
                        } else {
                            if (item.getFieldName().equals("payload_name")) {
                                payload_name = item.getString();
                                System.out.println("payload_name is " + payload_name);
                            } else if (item.getFieldName().equals("payload_label")) {
                                payload_label = item.getString();
                                System.out.println("payload_label is " + payload_label);
                            }

                        }

                    }

                    BufferedReader readFile = new BufferedReader(new FileReader(final_input_dataset_path));
                    String readFilerow;
                    int count = 0;
                    int payload_index = 0, label_index = 0;
                    ArrayList<String> rows = new ArrayList<String>();
                    rows.add("" + payload_name + "," + payload_label + "");
                    rows.add("\n");
                    while ((readFilerow = readFile.readLine()) != null) {
                        List<String> wordList = Arrays.asList(readFilerow.split(","));

                        if (count == 0) {
                            payload_index = wordList.indexOf(payload_name);
                            label_index = wordList.indexOf(payload_label);
                            continue;
                        }
                        Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import live_verna_detection; live_verna_detection=live_verna_detection('" + context_path.replace(File.separator, "/") + "','" + wordList.get(payload_index) + "');print(json.dumps([str(live_verna_detection)]))"});
                        p.waitFor();
                        String stdout = IOUtils.toString(p.getInputStream());
                        JSONArray syspathRaw = JSONArray.parseArray(stdout);
                        String versify_result1 = "";
                        for (int i = 0; i < syspathRaw.size(); i++) {
                            versify_result1 = syspathRaw.getString(i);

                        }

                        if (!wordList.get(label_index).equals(versify_result1)) {

                            rows.add("" + wordList.get(payload_index) + "," + wordList.get(label_index) + "");
                            rows.add("\n");
                        }
                        ++count;
                        //System.out.println(data[0] + "|" + data[1] + "|" + data[2]);
                    }

                    readFile.close();

                    Iterator<String> iter = rows.iterator();
                    while (iter.hasNext()) {
                        String outputString = (String) iter.next();
                        response.getOutputStream().print(outputString);
                    }

                    response.getOutputStream().flush();

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
