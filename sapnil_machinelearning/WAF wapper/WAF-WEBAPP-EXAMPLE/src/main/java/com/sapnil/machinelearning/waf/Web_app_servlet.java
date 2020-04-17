/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapnil.machinelearning.waf;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.alibaba.fastjson.JSONArray;
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
            String user_name = request.getParameter("user_name");
            String password = request.getParameter("password");
          
            //Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import live_verna_detection; live_verna_detection=live_verna_detection('D:/bitbuket sapnil machinelearning/sapnil_machinelearning/sapnil_machinelearning','"+user_name+"');print(json.dumps([str(live_verna_detection)]))"});
            
             HttpSession session = request.getSession(true);
             String context_path=session.getServletContext().getRealPath("/");
             
             //System.out.println("context_path is "+context_path.replace("\\", "/"));
            Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import live_verna_detection; live_verna_detection=live_verna_detection('"+context_path.replace(File.separator, "/")+"','"+user_name+"');print(json.dumps([str(live_verna_detection)]))"});
            p.waitFor();

           String stdout = IOUtils.toString(p.getInputStream());
           JSONArray syspathRaw = JSONArray.parseArray(stdout);
            for (int i = 0; i < syspathRaw.size(); i++) {
                String path = syspathRaw.getString(i);
                System.out.println("the param verify result " + path);
               
            }
            //System.out.println("the json is "+stdout);
            
            Process p1 = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import live_verna_detection; live_verna_detection=live_verna_detection('"+context_path.replace(File.separator, "/")+"','"+password+"');print(json.dumps([str(live_verna_detection)]))"});
            p1.waitFor();

           String stdout1 = IOUtils.toString(p1.getInputStream());
           
           JSONArray syspathRaw1 = JSONArray.parseArray(stdout1);
            for (int i = 0; i < syspathRaw1.size(); i++) {
                String path = syspathRaw1.getString(i);
                System.out.println("the param verify result " + path);
               
            }
           
            //System.out.println("the json is "+stdout1);
            
          /*  JSONArray syspathRaw = JSONArray.parseArray(stdout);
            for (int i = 0; i < syspathRaw.size(); i++) {
                String path = syspathRaw.getString(i);
                System.out.println("the accuracy is " + path);
               
            }*/
            
            jsonObject.put("Response", "complaint is taken");

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
