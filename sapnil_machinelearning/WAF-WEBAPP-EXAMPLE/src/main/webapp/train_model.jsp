<%-- 
    Document   : train_model
    Created on : Apr 26, 2020, 2:28:17 AM
    Author     : Nasir uddin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Bootstrap basic blog -->
        <!-- Bootstrap Core JavaScript -->
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <!-- Bootstrap Core JavaScript -->

        <!--File upload -->
        <script src="js/jquery-1.8.2.js"></script>
        <script src="js/jquery.ajaxfileupload.js"></script>
        <!--File upload -->

        <!-- Bootstrap Core CSS-->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap Core CSS-->
        <!-- Bootstrap basic blog -->

        <!-- Template css-->
        <link href="css/sb-admin.css" rel="stylesheet">
        <link href="css/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <!-- Template css-->

        <script type="text/javascript">
            $(document).ajaxStart(function () {
                $("#wait").css("display", "block");
            });
            $(document).ajaxComplete(function () {
                $("#wait").css("display", "none");
            });


            $(document).ready(function () {

                $('input[type="file"]').ajaxfileupload({
                    'action': 'UploadFile',
                    'onComplete': function (response) {
                        /* $('#upload').hide();
                         $('#message').show();*/
                        file_name = JSON.stringify(response.filename);
                        console.log("file name is333333333 " + file_name);
                        var statusVal = JSON.stringify(response.status);

                        if (statusVal == "false")
                        {
                            $("#message").html("<font color='red'>" + JSON.stringify(response.message) + " </font>");
                        }
                        if (statusVal == "true")
                        {
                            $("#message").html("<font color='green'>" + JSON.stringify(response.message) + " </font>");
                            document.getElementById("pass_num").focus();
                        }
                    }
                    /*  'onStart': function() {
                     $('#upload').show();
                     $('#message').hide();
                     }*/
                });
            });
        </script>

    </head>
    <body>
        <div id="wrapper">

            <!-- Navigation -->
            <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
                <!--------------------------------------------------------header section---------------------------------------------------------->

                <jsp:include page="header.jsp"/>



                <!--------------------------------------------------------header section----------------------------------------------------------->



                <!----------------------------------------------------Side menu bar section--------------------------------------------------------->
                <jsp:include page="SideMenu.jsp"/>



            </nav>


            <!--Side menu bar section  -->






            <!-- page content -->

            <div id="page-wrapper">

                <div class="container-fluid">

                    <!-- Page Heading -->
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">
                                build train model
                            </h1>

                        </div>

                    </div>
                    <!-- /.row -->

                    <div class="row">
                        <div class="form-group">

                            <label>New train dataset</label>

                            <input type="file" name="file" /><br />
                            <div id="upload" style="display: none;">Uploading..</div>
                            <div id="message"></div>


                        </div>


                    </div>
                </div>


            </div>
            <!-- /#page-wrapper -->
            <!-- page content -->

            <div id="wait" style="display:none;width:69px;height:89px;border:1px solid black;position:absolute;top:50%;left:50%;padding:2px;"><img src='loader.gif' width="64" height="64" /><br>Loading..</div>




        </div>

    </body>
</html>
