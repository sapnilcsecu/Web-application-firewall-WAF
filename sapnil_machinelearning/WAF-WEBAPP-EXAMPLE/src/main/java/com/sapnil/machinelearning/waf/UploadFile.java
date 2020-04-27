/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapnil.machinelearning.waf;

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
        
        final String UPLOAD_DIRECTORY = "C:/uploads";
        
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> multiparts = new ServletFileUpload(
                        new DiskFileItemFactory()).parseRequest(request);
                for (FileItem item : multiparts) {
                    if (!item.isFormField()) {
                        System.out.println("file data ");
                        File fileSaveDir = new File(UPLOAD_DIRECTORY);
                        if (!fileSaveDir.exists()) {
                            fileSaveDir.mkdir();
                        }
                        String name = new File(item.getName()).getName();
                        item.write(new File(UPLOAD_DIRECTORY + File.separator + name));
                    } else {
                        String payload_name = item.getString();
                        System.out.println("payload_name is "+payload_name);
                    }
                    
                }
                
            } catch (Exception e) {
                // exception handling
                e.printStackTrace();
            }
            
            PrintWriter out = response.getWriter();
            out.print("{\"status\":1}");
        }
        /*ServletContext sc = request.getSession().getServletContext();
         String dir = sc.getRealPath("/");
         String name = null;
       
         HttpSession session = request.getSession(true);
         System.out.println("context path is " + dir);
         boolean isMultipart = ServletFileUpload.isMultipartContent(request);

         // process only if its multipart content
         if (isMultipart) {
         // Create a factory for disk-based file items
         FileItemFactory factory = new DiskFileItemFactory();

         // Create a new file upload handler
         ServletFileUpload upload = new ServletFileUpload(factory);
         try {
         // Parse the request
         List<FileItem> multiparts = upload.parseRequest(request);

         for (FileItem item : multiparts) {
         if (!item.isFormField()) {

         String file_name = new File(item.getName()).getName();

         System.out.println("dir+name is " + (dir + File.separator + file_name));
         item.write(new File(dir + file_name));
         }
         }
         session.setAttribute("filename", "" + name);
              
         } catch (Exception e) {
         e.printStackTrace();
         }
         }*/
        
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
