/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapnil.machinelearning.example;

import com.alibaba.fastjson.JSONArray;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.sapnil.machinelearning.model.Train_model_param;
import com.sapnil.machinelearning.waf.Sapnil_WAF;
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
            }

            Train_model_param param_ob = new Train_model_param();
            param_ob.setInput_dataset_filename(input_dataset_name);
            param_ob.setInput_dataset_path(input_dataset_path);
            param_ob.setPayload_label(payload_label);
            param_ob.setPayload_name(payload_name);
            List<String[]> result_list = Sapnil_WAF.bulktest(param_ob);
            response.setContentType("text/csv");
            String reportName = "GenerateCSV_Report_"
                    + System.currentTimeMillis() + ".csv";
            response.setHeader("Content-disposition", "attachment; "
                    + "filename=" + reportName);

            BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
            CSVWriter writer = new CSVWriter(buff);
            writer.writeAll(result_list);
            writer.close();
        } catch (org.apache.commons.io.FileExistsException ex) {
            response.getWriter().write("File already exist.please rename this file");
            return;
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
