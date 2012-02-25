<div id="page2" class="clearfix" style="display: none">
    <div class="yellowBox clearfix ">
        <script type="text/javascript">
            String.prototype.startsWith = function (str) {
                return (this.match("^" + str) == str);
            };
            var hostName = "";
            var uiPath = "";
            var pwd = "";
            $(document).ready(function () {
                pwd = $("#pwd").text();
                hostName = window.location.host;
                uiPath = hostName + ":" + pwd
            });
            function executeCommand(command) {
                var modifiedCommand;
                if (command.startsWith("cd")) {
                    cdCommandHandling(command)
                }
                else if (command == "clear") {
                    $("#result").html("")
                }
                else {
                    modifiedCommand = "cd " + pwd + " ; " + command;
                    $.post("${createLink(controller:'appInfo',action:'commandResult')}", {command:modifiedCommand}, function (response) {
                        if (response) {
                            $("#result").append("<span style='color:#97D0E8;'>" + uiPath + "$</span> " + command + "<br/>");
                            $("#result").append(response + "<br/>");
                            var result = document.getElementById("result");
                            result.scrollTop = result.scrollHeight;
                        }
                    });
                }
                $("#command").val("");
            }

            function cdCommandHandling(command) {
                var error;
                var originalCommand = command;
                command = "cd " + pwd + " ; " + command + " ; " + "pwd";
                $.post("${createLink(controller:'appStats',action:'commandResult')}", {command:command}, function (response) {
                    if (response.replace("<br/>", "") == pwd) {
                        error = "bash: " + originalCommand + ": No such file or directory <br/>";
                    }
                    pwd = response.replace("<br/>", "");
                    uiPath = hostName + ":" + pwd;
                    $("#result").append("<span style='color:#97D0E8;'>" + uiPath + "$</span> <br/>");
                    if (error != null) {
                        $("#result").append(error);
                    }
                });
            }
        </script>

        <div style="font-size: 12px; font-weight: bold; color: white; background-color: #008888; padding: 0; margin: 0; width: 100%; left: 0; height: 6%; top: 0%;">
            <div style="padding-left: 20px;padding-top: 6px; margin: 0;font-size: 18px;letter-spacing: 2px; font-weight: bold;">Server Console</div>
        </div>

        <div id="result"
             style="font-size: 12px; font-weight: bold; color: white; background-color:#777777; padding: 0; margin: 0; width: 100%; height: 70%; left: 0; top: 6%;overflow: scroll;">

        </div>

        <input name="command" id="command" onchange="executeCommand(this.value)"
               style="font-size: 14px; font-weight: bold; color: white; background-color:#777;float: right; padding: 0; margin: 0; width: 90%; left: 100px; height: 7%; top: 94%;border-bottom: 4px ridge gray">
        <input style="font-size: 16px; font-weight: bold; color: white; background-color: #777; padding-left:20px; width: 10%; left: 0; height: 7%; top: 94%;border-bottom: 4px ridge gray"
               value="Command" readonly="">

        <div id="pwd" style="display: none">${workingDir}</div>

    </div>
</div>