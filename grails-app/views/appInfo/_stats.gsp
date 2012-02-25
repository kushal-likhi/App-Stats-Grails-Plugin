<div id="page0" class="clearfix">
    <div class="yellowBox clearfix ">
        <div>
            <div style="width: 20%;display: inline;float:left">
                <table>
                    <tr><td></td></tr>
                </table>
            </div>

            <div style="width: 79%;display: inline;float:left">
                <div style="margin-bottom: 10px">Reported Period :<g:form controller="appInfo" action="index" style="display: inline;">
                    <g:datePicker name="date" precision="month"/>
                    <g:submitButton name="date" value="Ok"/></g:form></div>
                <table class="appStatsBorder" width="100%" cellspacing="0" cellpadding="2" border="0">
                    <tbody><tr>
                        <td class="appStatsTitle" width="70%">Summary</td>
                        <td class="appStatsBlank">&nbsp;</td>
                    </tr>
                    <tr><td colspan="2">
                        <table class="appStatsData" width="100%" cellspacing="0" cellpadding="2" border="1">
                            <tbody>
                            <tr>
                                <td bgcolor="#ccccdd">&nbsp;</td>
                                <td width="25%" bgcolor="#ffb055">Unique visitors</td>
                                <td width="25%" bgcolor="#f8e880">Number of visits</td>
                                <td width="25%" bgcolor="#66f0ff">Hits</td>
                            </tr>
                            <tr>
                                <td class="appStats">Traffic &nbsp;</td>
                                <td><b>${stats.uniqueVisitorsCount}</b></td>
                                <td><b>${stats.noOfVisits}</b></td>
                                <td><b>${stats.totalHits}</b></td>
                            </tr>
                            </tbody>
                        </table>
                    </td></tr>
                    </tbody>
                </table>
                <br/><br/><br/>
                <table class="appStatsBorder" width="100%" cellspacing="0" cellpadding="2" border="0">
                    <tbody><tr>
                        <td class="appStatsTitle" width="70%">Pages</td>
                        <td class="appStatsBlank">&nbsp;</td>
                    </tr>
                    <tr><td colspan="2">
                        <table class="appStatsData" width="100%" cellspacing="0" cellpadding="2" border="1">
                            <tbody><tr>
                                <td width="80" bgcolor="#4477dd">Pages</td>
                                <td width="25%" bgcolor="#ffb055">Unique visitors</td>
                                <td width="25%" bgcolor="#f8e880">Number of visits</td>
                                <td width="25%" bgcolor="#66f0ff">Hits</td>
                            </tr>
                            <g:each in="${pageInfoList}" var="pageInfo">
                                <tr>
                                    <td>${pageInfo.pageURL}</td>
                                    <td>${pageInfo.uniqueVisitor}</td>
                                    <td>${pageInfo.noOfVisits}</td>
                                    <td>${pageInfo.totalHits}</td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </td></tr>
                    </tbody>
                </table>
                <br/><br/><br/>
                <table class="appStatsBorder" width="100%" cellspacing="0" cellpadding="2" border="0">
                    <tbody><tr>
                        <td class="appStatsTitle" width="70%">Controllers</td>
                        <td class="appStatsBlank">&nbsp;</td>
                    </tr>
                    <tr><td colspan="2">
                        <table class="appStatsData" width="100%" cellspacing="0" cellpadding="2" border="1">
                            <tbody><tr>
                                <td width="80" bgcolor="#4477dd">Controllers</td>
                                <td width="25%" bgcolor="#ffb055">Unique visitors</td>
                                <td width="25%" bgcolor="#f8e880">Number of visits</td>
                                <td width="25%" bgcolor="#66f0ff">Hits</td>
                            </tr>
                            <g:each in="${controllerInfoList}" var="pageInfo">
                                <tr>
                                    <td>${pageInfo.pageURL}</td>
                                    <td>${pageInfo.uniqueVisitor}</td>
                                    <td>${pageInfo.noOfVisits}</td>
                                    <td>${pageInfo.totalHits}</td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </td></tr>
                    </tbody>
                </table>
                <br/><br/><br/>
                <table class="appStatsBorder" width="100%" cellspacing="0" cellpadding="2" border="0">
                    <tbody><tr>
                        <td class="appStatsTitle" width="70%">Controllers With Their Action</td>
                        <td class="appStatsBlank">&nbsp;</td>
                    </tr>
                    <tr><td colspan="2">
                        <table class="appStatsData" width="100%" cellspacing="0" cellpadding="2" border="1">
                            <tbody><tr>
                                <td width="80" bgcolor="#4477dd">Controllers And Actions</td>
                                <td width="25%" bgcolor="#ffb055">Unique visitors</td>
                                <td width="25%" bgcolor="#f8e880">Number of visits</td>
                                <td width="25%" bgcolor="#66f0ff">Hits</td>
                            </tr>
                            <g:each in="${controllerAndActionList}" var="pageInfo">
                                <tr>
                                    <td>${pageInfo.pageURL}</td>
                                    <td>${pageInfo.uniqueVisitor}</td>
                                    <td>${pageInfo.noOfVisits}</td>
                                    <td>${pageInfo.totalHits}</td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </td></tr>
                    </tbody>
                </table>
                %{--<br/><br/><br/>
                <table class="appStatsBorder" width="100%" cellspacing="0" cellpadding="2" border="0">
                    <tbody><tr>
                        <td class="appStatsTitle" width="70%">Monthly History</td>
                        <td class="appStatsBlank">&nbsp;</td>
                    </tr>
                    <tr><td colspan="2">
                        <table class="appStatsData" width="100%" cellspacing="0" cellpadding="2" border="1">
                            <tbody><tr>
                                <td align="center">
                                    <center>
                                        <table>
                                            <tbody>
                                            <tr>
                                                <td width="80" bgcolor="#ececec">Month</td>
                                                <td width="80" bgcolor="#ffb055">Unique visitors</td>
                                                <td width="80" bgcolor="#f8e880">Number of visits</td>
                                                <td width="80" bgcolor="#4477dd">Pages</td>
                                                <td width="80" bgcolor="#66f0ff">Hits</td>
                                                --}%%{--<td width="80" bgcolor="#2ea495">Bandwidth</td>--}%%{--
                                            </tr>
                                            </tbody>
                                        </table>
                                    </center>
                                </td>
                            </tr></tbody>
                        </table>
                    </td></tr>
                    </tbody>
                </table>--}%
            </div>
        </div>
    </div>
</div>