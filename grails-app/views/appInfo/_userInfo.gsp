<div id="page1" class="clearfix" style="display: none">
    <div class="yellowBox clearfix ">
        <div>
            <div style="width: 20%;display: inline;float:left">
                <table>
                    <tr><td></td></tr>
                </table>
            </div>

            <div style="width: 79%;display: inline;float:left">
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
                                <td width="20%" bgcolor="#ffb055">Unique visitors</td>
                                <td width="20%" bgcolor="#f8e880">Number of visits</td>
                                <td width="20%" bgcolor="#4477dd">Pages</td>
                                <td width="20%" bgcolor="#66f0ff">Hits</td>
                                %{--<td width="17%" bgcolor="#2ea495">Bandwidth</td>--}%
                            </tr>
                            <tr>
                                <td class="appStats">Traffic &nbsp;</td>
                                <td><b>${totalVisitorStats.uniqueVisitorsCount}</b></td>
                                <td><b>${totalVisitorStats.noOfVisits}</b></td>
                                <td><b></b></td>
                                <td><b>${totalVisitorStats.totalHits}</b></td>
                                %{--<td><b></b></td>--}%
                            </tr>
                            </tbody>
                        </table>
                    </td></tr>
                    </tbody>
                </table>
                <br/><br/><br/>
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
                                                %{--<td width="80" bgcolor="#2ea495">Bandwidth</td>--}%
                                            </tr>
                                            </tbody>
                                        </table>
                                    </center>
                                </td>
                            </tr></tbody>
                        </table>
                    </td></tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>