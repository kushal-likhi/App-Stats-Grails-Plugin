<div id="page1" class="clearfix" style="display: none">
    <div class="yellowBox clearfix ">
        <div class="nav"><img src="${resource(dir: 'images', file: 'ip.jpeg')}" alt="ip" height="20" width="20"
                              class="imgClass">IP Information
            <a href="#" class="click" id="1">Click To Show/Hide</a>
        </div>

        <div class="noDisplay infoText" id="info1">
            Internal IP Address: ${ipInfo.internalIPAddress}<br>
            External IP Address: <span id='externalIpInfo'></span><br>
            Host Name: ${ipInfo.hostName} <br>
        </div>

        <div class="nav"><img src="${resource(dir: 'images', file: 'cpu.jpeg')}" alt="cpu" height="20" width="20"
                              class="imgClass">CPU Information
            <a href="#" class="click" id="2">Click To Show/Hide</a>
        </div>

        <div class="noDisplay infoText" id="info2">
            <g:each in="${cpuInfo}" var="processor">
                <g:each in="${processor}" var="info">
                    ${info} <br/>
                </g:each>
                <br/>
            </g:each>
        </div>

        <div class="nav"><img src="${resource(dir: 'images', file: 'ram.jpeg')}" alt="ram" height="20" width="20"
                              class="imgClass">Memory Information
            <a href="#" class="click" id="3">Click To Show/Hide</a>
        </div>

        <div class="noDisplay infoText" id="info3">
            <g:each in="${memInfo}" var="info">
                ${info}<br>
            </g:each>
        </div>

        <div class="nav"><img src="${resource(dir: 'images', file: 'other.jpeg')}" alt="other" height="20" width="20"
                              class="imgClass">Other Information
            <a href="#" class="click" id="4">Click To Show/Hide</a>
        </div>

        <div class="noDisplay infoText" id="info4">
            Number of Hyper Threaded CPU : ${serverInformation.virtualCPUCount}<br/>
            Number of Physical CPU : ${serverInformation.physicalCPUCount}<br/>
            Operating System Name : ${serverInformation.osName}<br/>
            OS Version : ${serverInformation.osVersion}<br/>
            Local Domain Name : ${serverInformation.localDomainName}<br/>
            kernel Version Number : ${serverInformation.kernelVersionNumber}<br/>
            Machine Number : ${serverInformation.machineNumber}<br/>
            Server Date : ${serverInformation.serverDate}<br/>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(".click").click(function () {
        var id = $(this).attr("id");
        $("#info" + id).toggle()
        return false;
    });
    jQuery.get("${createLink(controller: 'appInfo', action: 'ipOfUrl')}", {url:window.location.href}, function (data) {
        jQuery("#externalIpInfo").html(data);
    });
</script>