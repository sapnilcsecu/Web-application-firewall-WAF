/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapnil.machinelearning.waf;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.alibaba.fastjson.JSONArray;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import live_verna_detection; live_verna_detection=live_verna_detection('D:/bitbuket sapnil machinelearning/sapnil_machinelearning/sapnil_machinelearning','"+user_name+"');print(json.dumps([str(live_verna_detection)]))"});
            //Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;print(json.dumps(['the is accuracy_score']))"});

            p.waitFor();

            String stdout = IOUtils.toString(p.getInputStream());
            System.out.println("the json is "+stdout);
            JSONArray syspathRaw = JSONArray.parseArray(stdout);
            for (int i = 0; i < syspathRaw.size(); i++) {
                String path = syspathRaw.getString(i);
                System.out.println("the accuracy is " + path);
                /* if (path.contains("site-packages") || path.contains("dist-packages")) {
                    System.out.println("the  path is "+path);
                   // intr.exec(String.format("sys.path.insert(0, '%s')", path));
                }*/
            }
            
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
