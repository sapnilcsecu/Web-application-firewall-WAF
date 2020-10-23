/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapnil.machinelearning.example;

import com.alibaba.fastjson.JSONArray;
import com.opencsv.CSVWriter;
import com.sapnil.machinelearning.utility.Encode_detection;
import com.sapnil.machinelearning.utility.Utility;
import com.sapnil.machinelearning.waf.Sapnil_WAF;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Nasir(programmer)
 */
public class Web_app_servlet extends HttpServlet {

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
        String final_cvs_path = null;
        try {
            JSONObject jsonObject = new JSONObject();
            String emp_name1 = request.getParameter("emp_name1");
            // System.out.println("user_name is " + emp_name1);
            String reg = request.getParameter("reg");
            // System.out.println("reg is " + reg);
            String Date = request.getParameter("Date");
            //System.out.println("Date is " + Date);
            String emp_mobile = request.getParameter("emp_mobile");
            // System.out.println("emp_mobile is " + emp_mobile);
            ArrayList<String[]> result_list = new ArrayList<String[]>();
            result_list.add(new String[]{"payload"});
            //String decode_param = Encode_detection.decode(paramlist.get(count));

            if (!Utility.is_empty_string(emp_name1)) {
                System.out.println("emp_name1 is " + emp_name1);
                result_list.add(new String[]{Encode_detection.decode(emp_name1.trim())});
            }
            if (!Utility.is_empty_string(reg)) {
                System.out.println("reg is " + reg);
                result_list.add(new String[]{Encode_detection.decode(reg.trim())});
            }
            if (!Utility.is_empty_string(Date)) {
                System.out.println("date is " + Date);
                result_list.add(new String[]{Encode_detection.decode(Date.trim())});
            }
            if (!Utility.is_empty_string(emp_mobile)) {
                String decode_val = Encode_detection.decode(emp_mobile.trim());
                System.out.println("emp_mobile is " + emp_mobile);
                result_list.add(new String[]{Encode_detection.decode(emp_mobile.trim())});
            }

            HttpSession session = request.getSession(true);
            String csv_file = session.getId() + ".csv";
            String context_path = session.getServletContext().getRealPath("/");
            final_cvs_path = context_path + csv_file;
            CSVWriter writer = new CSVWriter(new FileWriter(final_cvs_path));
            writer.writeAll(result_list);
            writer.close();
            if (result_list.size() > 1) {
                boolean is_vernable = Sapnil_WAF.detect_verna_param(request, csv_file);
                if (is_vernable) {
                  //  Utility.del_file(final_cvs_path);
                    System.out.println("Parameter contain script");
                    response.getWriter().write("Parameter contain script");
                    return;
                } else {
                  //  Utility.del_file(final_cvs_path);
                    System.out.println("this is normal parameter");
                    response.getWriter().write("this is normal parameter");
                    return;
                }
            } else {
               // Utility.del_file(final_cvs_path);
                System.out.println("empty parameter");
                response.getWriter().write("empty parameter");
                return;
            }

            // Utility.del_file(final_cvs_path);
        } catch (Exception ex) {

            ex.getMessage();
           // Utility.del_file(final_cvs_path);
            System.out.println("Parameter contain script");
            response.getWriter().write("Parameter contain script");
            return;
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
