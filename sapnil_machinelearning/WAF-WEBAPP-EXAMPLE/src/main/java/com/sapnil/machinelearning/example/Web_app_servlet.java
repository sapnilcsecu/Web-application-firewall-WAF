/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapnil.machinelearning.example;

import com.alibaba.fastjson.JSONArray;
import com.sapnil.machinelearning.waf.Sapnil_WAF;
import java.io.File;
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

        try {
            JSONObject jsonObject = new JSONObject();
            String emp_name1 = request.getParameter("emp_name1");
            System.out.println("user_name is " + emp_name1);
            String reg = request.getParameter("reg");
            System.out.println("reg is " + reg);
            String Date = request.getParameter("Date");
            System.out.println("Date is " + Date);
            String emp_mobile = request.getParameter("emp_mobile");
            System.out.println("emp_mobile is " + emp_mobile);

            List<String> param_list = new ArrayList<String>();
            param_list.add(emp_name1);
            param_list.add(reg);
            param_list.add(Date);
            param_list.add(emp_mobile);
            boolean is_vernable = Sapnil_WAF.detect_verna_param(request, param_list);
            if (is_vernable) {

                response.getWriter().write("Parameter contain script");
                return;
            } else {

                response.getWriter().write("this is normal parameter");
                return;
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