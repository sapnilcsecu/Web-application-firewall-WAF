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
        <!--<script src="js/jquery-1.8.2.js"></script>
        <script src="js/jquery.ajaxfileupload.js"></script>-->
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
            $(document).ready(function () {
                function bs_input_file() {
                    $(".input-file").before(
                            function () {
                                if (!$(this).prev().hasClass('input-ghost')) {
                                    var element = $("<input type='file' class='input-ghost' style='visibility:hidden; height:0'>");
                                    element.attr("name", $(this).attr("name"));
                                    element.change(function () {
                                        element.next(element).find('input').val((element.val()).split('\\').pop());
                                    });
                                    $(this).find("button.btn-choose").click(function () {
                                        element.click();
                                    });
                                    $(this).find("button.btn-reset").click(function () {
                                        element.val(null);
                                        $(this).parents(".input-file").find('input').val('');
                                    });
                                    $(this).find('input').css("cursor", "pointer");
                                    $(this).find('input').mousedown(function () {
                                        $(this).parents('.input-file').prev().click();
                                        return false;
                                    });
                                    return element;
                                }
                            }
                    );
                }

                bs_input_file();

                $("#uploadBtn").on("click", function () {
                    var url = "UploadFile";
                    var form = $("#sampleUploadFrm")[0];
                    //var data = new FormData(form);
                    var data = new FormData(form);
                   
                    var payload_name = $("#payload_name").val();
                    console.log("payload_name is "+payload_name);
                    data.append("payload_name", payload_name);
                    /*var data_list = {};
                     data_list['payload_name'] = 'payload_name';
                     data_list['payload_label'] = 'payload_name';
                     data_list['file_data']=file_data;*/
                    $.ajax({
                        type: "POST",
                        encType: "multipart/form-data",
                        url: url,
                        cache: false,
                        processData: false,
                        contentType: false,
                        data: data,
                        success: function (msg) {
                            var response = JSON.parse(msg);
                            var status = response.status;
                            if (status == 1) {
                                alert("File has been uploaded successfully");
                            } else {
                                alert("Couldn't upload file");
                            }
                        },
                        error: function (msg) {
                            alert("Couldn't upload file");
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
                                build train model
                            </h1>

                        </div>

                    </div>
                    <!-- /.row -->

                    <div class="row">
                        <div class="form-group">

                            <label>New train dataset</label>
                            <!--
                            <input type="file" name="file" accept=".csv" /><br />
                            <div id="upload" style="display: none;">Uploading..</div>
                            <div id="message"></div>

                            -->
                            <form id="sampleUploadFrm" method="POST" action="#" enctype="multipart/form-data">
                                <div class="form-group">

                                    <label>Payload name</label>
                                    <input id="payload_name"  class="form-control"  data-multiple data-minchars="1" placeholder="Payload name">


                                </div>
                                <!-- COMPONENT START -->
                                <div class="form-group">
                                    <div class="input-group input-file" name="file">
                                        <span class="input-group-btn"><button class="btn btn-default btn-choose" type="button">Choose</button></span> <input type="text" class="form-control" placeholder='Choose a file...' />
                                    </div>
                                    <button type="button" class="btn btn-primary pull-right" id="uploadBtn">Submit</button>
                                </div>

                            </form>
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
