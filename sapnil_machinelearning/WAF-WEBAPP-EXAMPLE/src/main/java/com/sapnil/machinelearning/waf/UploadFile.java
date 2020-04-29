/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapnil.machinelearning.waf;

import com.alibaba.fastjson.JSONArray;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

/**
 *
 * @author Nasir uddin
 */
public class UploadFile extends HttpServlet {

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

        JSONObject jsonObject = new JSONObject();
        String payload_name = "", payload_label = "", input_dataset_name = "", mod_of_tran = "";
        HttpSession session = request.getSession(true);
        String context_path = session.getServletContext().getRealPath("/");
        String input_dataset_path = context_path.replace(File.separator, "/");
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        System.out.println("file data ");
                        /*File fileSaveDir = new File(UPLOAD_DIRECTORY);
                         if (!fileSaveDir.exists()) {
                         fileSaveDir.mkdir();
                         }*/
                        input_dataset_name = new File(item.getName()).getName();
                        item.write(new File(input_dataset_path + input_dataset_name));
                    } else {
                        if (item.getFieldName().equals("payload_name")) {
                            payload_name = item.getString();
                            System.out.println("payload_name is " + payload_name);
                        } else if (item.getFieldName().equals("payload_label")) {
                            payload_label = item.getString();
                            System.out.println("payload_label is " + payload_label);
                        } else if (item.getFieldName().equals("mod_of_tran")) {
                            mod_of_tran = item.getString();
                            System.out.println("mod_of_tran is " + mod_of_tran);
                        }

                    }

                }
                String final_input_dataset_path = input_dataset_path + input_dataset_name;
                ///train_model(input_dataset,vocabulary_path,payload_col_name,payload_label,mode)
                //System.out.println("context_path is "+context_path.replace("\\", "/"));
                if (mod_of_tran.equals("NEW TRAINING MODEL")) {
                    //Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import train_model; accuracy_score=train_model(" + final_input_dataset_path + "," + input_dataset_path + "," + payload_name + "," + payload_label + ",'write');print(json.dumps([str(accuracy_score)]))"});
                    //Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import train_model; accuracy_score=train_model('E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/src/main/webapp/verfullpayload.csv','E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/src/main/webapp/','payload','label','write'); print(json.dumps([str(accuracy_score)]))"});
                    System.out.println("input_dataset_path is " + input_dataset_path);
                    Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import train_model; accuracy_score=train_model('" + final_input_dataset_path + "','" + input_dataset_path + "','" + payload_name + "','" + payload_label + "','write'); print(json.dumps([str(accuracy_score)]))"});
                    //Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import train_model; accuracy_score=train_model('"+final_input_dataset_path+"','E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/src/main/webapp/','"+payload_name+"','"+payload_label+"','write'); print(json.dumps([str(accuracy_score)]))"});

                    p.waitFor();
                    String stdout = IOUtils.toString(p.getInputStream());
                    System.out.println("the stdout is " + stdout);
                    JSONArray syspathRaw = JSONArray.parseArray(stdout);
                    for (int i = 0; i < syspathRaw.size(); i++) {
                        String path = syspathRaw.getString(i);
                        System.out.println("the accuracy is " + path);

                    }
                } else if (mod_of_tran.equals("NEW TRAINING MODEL")) {
                    Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import train_model; accuracy_score=train_model('" + final_input_dataset_path + "','" + input_dataset_path + "','" + payload_name + "','" + payload_label + "','append'); print(json.dumps([str(accuracy_score)]))"});
                    //Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import train_model; accuracy_score=train_model('"+final_input_dataset_path+"','E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/src/main/webapp/','"+payload_name+"','"+payload_label+"','write'); print(json.dumps([str(accuracy_score)]))"});

                    p.waitFor();
                    String stdout = IOUtils.toString(p.getInputStream());
                    System.out.println("the stdout is " + stdout);
                    JSONArray syspathRaw = JSONArray.parseArray(stdout);
                    for (int i = 0; i < syspathRaw.size(); i++) {
                        String path = syspathRaw.getString(i);
                        System.out.println("the accuracy is " + path);

                    }
                }

            } catch (Exception e) {
                // exception handling
                e.printStackTrace();
            }

            jsonObject.put("Response", "sucessful");

            /*PrintWriter out = response.getWriter();

             out.print(jsonObject);
             out.flush();*/
            //response.setContentType("application/json");
            response.getWriter().write("training process completed");
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
