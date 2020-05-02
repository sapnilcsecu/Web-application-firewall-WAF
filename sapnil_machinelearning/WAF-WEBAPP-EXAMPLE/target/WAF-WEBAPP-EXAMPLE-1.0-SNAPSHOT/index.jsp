<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Bootstrap basic blog -->
        <!-- Bootstrap Core JavaScript -->
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <!-- Bootstrap Core JavaScript -->

        <!-- Bootstrap Core CSS-->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap Core CSS-->
        <!-- Bootstrap basic blog -->

        <!-- Template css-->
        <link href="css/sb-admin.css" rel="stylesheet">
        <link href="css/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <!-- Template css-->

        <script type="text/javascript">

            $(document).ready(function () {

                $("#submit").click(function (event) {
                    var emp_name1 = $("#emp_name1").val();
                    var reg = $("#reg").val();
                    var Date = $("#Date").val();
                    var emp_mobile = $("#emp_mobile").val();
                    $.post('Web_app_servlet', {'emp_name1': emp_name1, 'reg': reg,'Date':Date,'emp_mobile':emp_mobile}, function(responseJson) {

                        if (responseJson.Response != null) {


                            if (responseJson.Response != null) {

                                alert("" + responseJson.Response);
                                location.reload();
                            }
                           
                        }
                    });

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
                                Form example
                            </h1>

                        </div>

                    </div>
                    <!-- /.row -->

                    <div class="row">
                        <div class="col-lg-6">
                            <table id="myTable">
                                <tr class="header">
                                    <td style="width:40%;">

                                        <div class="form-group">

                                            <label>Name</label>
                                            <input id="emp_name1"  class="form-control" >

                                        </div>
                                        <div class="form-group">

                                            <label>Registration no</label>
                                            <input id="reg"  class="form-control" >

                                        </div>
                                        <div class="form-group">

                                            <label>Date</label>
                                            <!-- <input id="Date"  class="form-control datepicker"  placeholder="DD-MM-YYYY">-->
                                            <input id="Date"  class="form-control" >
                                        </div>
                                        <div class="form-group">

                                            <label>Mobile</label>
                                            <input id="emp_mobile"  class="form-control" >

                                        </div>






                                    </td>



                                </tr>

                            </table>
                            <datalist id="place_of_posting_list"></datalist>



                            <button id="submit" type="button" class="btn btn-default">Submit Button</button>



                        </div>
                    </div>


                </div>
                <!-- /#page-wrapper -->
                <!-- page content -->

                <div id="wait" style="display:none;width:69px;height:89px;border:1px solid black;position:absolute;top:50%;left:50%;padding:2px;"><img src='loader.gif' width="64" height="64" /><br>Loading..</div>




            </div>

    </body>
</html>
