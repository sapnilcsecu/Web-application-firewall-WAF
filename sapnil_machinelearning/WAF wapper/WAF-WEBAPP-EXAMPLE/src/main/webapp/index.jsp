<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <!-- Bootstrap Core JavaScript -->
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <!-- Bootstrap Core JavaScript -->

        <!-- Bootstrap Core CSS-->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/loginstyle.css">
        <!-- Bootstrap Core CSS-->
        <!-- Bootstrap basic blog -->

       
    </head>
    <body onload="setinfo()">
        <script type="text/javascript">
            
            $(document).ready(function() {

                $("#submit").click(function(event) {
                  
                    var user_name = $("#user_name").val();
                    var password = $("#password").val();
                   

                    $.post('Web_app_servlet', {'user_name': user_name, 'password': password}, function(responseJson) {

                        if (responseJson.Response != null) {


                            if (responseJson.Response != null) {

                                alert("" + responseJson.Response);
                                location.reload();
                            }
                            //reset();
                            // window.location.href = "Buy_boucher_report.jsp?voucher_no=" + responseJson.voucher_no + "&total_in_tk=" + responseJson.total_in_tk + "&tra_Date=" + responseJson.tra_Date;
                            //window.location.href = "Buy_boucher_report.jsp?voucher_no=15";

                        }
                    });

                });

            });
            
        </script>



        <div id="wrapper">

            <!-- Navigation -->
            <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
                <!--------------------------------------------------------header section---------------------------------------------------------->

                <jsp:include page="login_header.jsp"/>



                <!--------------------------------------------------------header section----------------------------------------------------------->

            </nav>
            <!-- Navigation -->


            <section class="container"> 
                <div class="login">
                    <h1>Login to Rupali chata</h1>
                    <!-- <form method="post" action="index.html"> -->
                    <p id="login_satus"></p>
                    <p><input type="text" name="user_name" id="user_name" value="" placeholder="Username"></p>
                    <p><input type="password" name="password" id="password" value="" placeholder="Password"></p>

                    <p class="submit"><input type="button" name="login" value="Login" onclick="submitform()"></p>

                </div>


            </section>




        </div>
        <!-- /#wrapper -->

    </body>
</html>
