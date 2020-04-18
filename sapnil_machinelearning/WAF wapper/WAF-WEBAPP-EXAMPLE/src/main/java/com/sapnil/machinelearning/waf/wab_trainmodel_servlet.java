/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapnil.machinelearning.waf;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

/**
 *
 * @author Nasir(programmer)
 */
public class wab_trainmodel_servlet extends HttpServlet {

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

            HttpSession session = request.getSession(true);
            String context_path = session.getServletContext().getRealPath("/");
            String train_modelpath = context_path.replace(File.separator, "/");
            Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import train_model; accuracy_score=train_model('" + train_modelpath + "verfullpayload.csv','" + train_modelpath + "xss_test.csv','" + train_modelpath + "','payload','label');print(json.dumps([str(accuracy_score)]))"});
            //Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;print(json.dumps(['the is accuracy_score']))"});

            p.waitFor();

            String stdout = IOUtils.toString(p.getInputStream());
            JSONArray syspathRaw = JSONArray.parseArray(stdout);
            for (int i = 0; i < syspathRaw.size(); i++) {
                String path = syspathRaw.getString(i);
                System.out.println("the accuracy is " + path);

            }

            jsonObject.put("Response", "training process is completed");

            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json");
            mapper.writeValue(response.getOutputStream(), jsonObject.toMap());

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
