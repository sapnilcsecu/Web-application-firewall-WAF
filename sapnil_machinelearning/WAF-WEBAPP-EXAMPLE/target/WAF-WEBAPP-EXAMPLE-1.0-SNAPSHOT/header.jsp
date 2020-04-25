<%-- 
    Document   : header.jsp
    Created on : Dec 1, 2016, 3:29:26 PM
    Author     : red5-nasir
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Retirement Benefit</title>

        <!-- Bootstrap Core CSS -->
        <!-- <link href="css/bootstrap.min.css" rel="stylesheet"> -->

        <!-- Custom CSS -->
        <!-- <link href="css/sb-admin.css" rel="stylesheet"> -->

        <!-- Morris Charts CSS -->
        <!-- <link href="css/plugins/morris.css" rel="stylesheet">-->

        <!-- Custom Fonts -->
        <!--<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"> -->



    </head>
    <body>

        <script language="javascript">
            function logout() {
                //request.getRequestDispatcher("/login.jsp").forward(request, response);
               /* var method = "post"; // Set method to post by default if not specified.
                 var path = "Signin";
                 
                 
                 var form = document.createElement("form");
                 
                 form.setAttribute("method", method);
                 form.setAttribute("action", path);
                 document.body.appendChild(form);*/
                 document.location.replace("login.jsp");
                // form.submit();
                 
            

                



            }
            
            
            

            $(window).load(function() {

                var emp_name = "<%=(String) session.getAttribute("emp_name")%>";
                document.getElementById("emp_name").innerHTML = emp_name;
                console.log("emp name is " + emp_name);
            });

        </script>



        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">WAF WEB DEMO</a>
        </div>
        <!-- Top Menu Items -->
        <!--<ul class="nav navbar-right top-nav">
            <li class="dropdown">
                <a id="emp_name" href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> John Smith <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="Pass_reset.jsp"><i class="fa fa-fw fa-user"></i>Password Reset</a>
                    </li>


                    <li class="divider"></li>
                    <li>
                        <a onclick="logout()"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                    </li>
                </ul>
            </li>

          
        </ul>-->




    </body>
</html>
