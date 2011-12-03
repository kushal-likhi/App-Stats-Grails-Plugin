<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Server Console</title>
    <g:javascript library="jquery" plugin="jquery"/>
    <script type="text/javascript">
        var hostName = "";
        $(document).ready(function() {
            hostName = window.location.host;
        });
        function executeCommand(command) {
            if (command == "") {
                alert("Please Enter Command");
            } else {
                $("#command").val("");
                if (command == "clear") {
                    $("#result").html("")
                } else {
                    $.post("${createLink(controller:'serverInformation',action:'commandResult')}", {command:command}, function(response) {
                        if (response) {
                            $("#result").append("<span style='color:#97D0E8;'>" + hostName + ":$</span> " + command + "<br/>");
                            var ls = response.split("\n");
                            for (var i = 0; i <= ls.length - 1; i++) {
                                $("#result").append(ls[i] + "<br/>");
                            }
                        }
                    });
                }
            }
        }
    </script>
</head>

<body>
<div style="font-size: 12px; font-weight: bold; color: white; background-color: #008888; padding: 0; margin: 0; width: 100%; left: 0; height: 6%; top: 0%;">
    <div style="padding-left: 20px;padding-top: 6px; margin: 0;font-size: 18px;letter-spacing: 2px; font-weight: bold;">Server Console</div>
</div>

<div id="result"
     style="font-size: 12px; font-weight: bold; color: white; background-color:#777777; padding: 0; margin: 0; width: 100%; height: 85%; left: 0; top: 6%;overflow: scroll;">

</div>

<input name="command" id="command" onchange="executeCommand(this.value)"
       style="font-size: 14px; font-weight: bold; color: white; background-color:#777;float: right; padding: 0; margin: 0; width: 90%; left: 100px; height: 7%; top: 94%;border-bottom: 4px ridge gray">
<input style="font-size: 16px; font-weight: bold; color: white; background-color: #777; padding-left:20px; width: 10%; left: 0; height: 7%; top: 94%;border-bottom: 4px ridge gray"
       value="Command" readonly="">

</body>
</html>