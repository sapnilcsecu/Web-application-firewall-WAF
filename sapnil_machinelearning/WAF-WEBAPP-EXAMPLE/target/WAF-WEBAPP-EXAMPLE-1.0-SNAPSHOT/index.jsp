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
                                Form example
                            </h1>

                        </div>

                    </div>
                    <!-- /.row -->

                    <div class="row">
                        <div class="col-lg-6">


                           

                        </div>
                    </div>


                </div>
                <!-- /#page-wrapper -->
                <!-- page content -->

                <div id="wait" style="display:none;width:69px;height:89px;border:1px solid black;position:absolute;top:50%;left:50%;padding:2px;"><img src='loader.gif' width="64" height="64" /><br>Loading..</div>




            </div>

    </body>
</html>
