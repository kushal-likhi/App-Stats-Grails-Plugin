<html>
<head>
    <title>
        Application Statistics
    </title>
    <g:javascript library="jquery" plugin="jquery"/>
    <script type="text/javascript">
        var selectedTab = 0;
        function tabClick(n) {
            if (n === selectedTab) return; // nothing to do.
            $("span.active").removeClass('active');
            $("#tab" + n).addClass('active');
            $("#page" + selectedTab).css({'display':'none' , 'position':'absolute'});
            $("#page" + n).css({'display':'block'  , 'position':'static'});
            selectedTab = n;
        }
    </script>
    <style type="text/css">
    .roundedCorner {
        border-radius: 5px;
        -moz-border-radius: 5px;
        -webkit-border-radius: 5px;
        -khtml-border-radius: 5px;
    }

    .whitebox {
        border: 2px solid #e3dedd;
        padding: 12px;
        clear: both;
    }

    .whitebox h2 {
        font-size: 26px;
        line-height: 26px;
        padding-bottom: 10px;
    }

    .whitebox h4 {
        font-size: 18px;
        line-height: 20px;
        padding-bottom: 5px;
    }

    .whitediv {
        background: #fff;
        border: 1px solid #e3dedd;
        padding: 5px 7px;
        position: relative;
    }

    .whitediv h2 {
        font-size: 21px;
        line-height: 21px;
        padding-bottom: 7px;
        letter-spacing: -1px
    }

    .whitediv h4 {
        font-size: 16px;
        line-height: 16px;
        padding-bottom: 10px;
    }

    .clear {
        clear: both;
    }

    .clearfix:after {
        visibility: hidden;
        display: block;
        font-size: 0;
        content: " ";
        clear: both;
    }

    .clearfix {
        display: block;
    }

    .marginTob {
        margin-top: 10px;
    }

    ul#tabMenu {
        background: url(../images/yellow-dot.png) repeat-x 0 bottom;
        padding: 0;
        margin: 0;
        list-style-type: none;
    }

    ul#tabMenu li {
        float: left;
        margin-top: 15px;
    }

    ul#tabMenu li.last {
        float: right;
    }

    ul#tabMenu li span {
        color: #bababa;
        cursor: pointer;
        font-size: 18px;
        display: block;
        background: url(../images/tabMenu-bg.png) repeat-x 0 0;
        text-decoration: none;
        font-weight: bold;
        border-top: 1px solid #dbd5d5;
        border-left: 1px solid #dbd5d5;
        margin-top: 4px;
        line-height: 34px;
        padding: 0 24px;
        margin-right: -1px;
        border-top-left-radius: 8px;
        border-top-right-radius: 8px;
        border-right: 1px solid #dbd5d5;
        -moz-border-radius-topright: 8px;
        -moz-border-radius-topleft: 8px;
        -webkit-border-top-left-radius: 8px;
        -webkit-border-top-right-radius: 8px;
    }

    ul#tabMenu li span.last {
        padding-right: 28px;
        margin-right: 0;
    }

    ul#tabMenu li span.active {
        line-height: 39px;
        border-color: #fbe04d;
        color: #8aa412;
        background: #fff;
        margin-right: 0;
        margin-top: 0;
    }

    .yellowBox {
        color: #615959;
        border-bottom: 1px solid #fbe04d;
        border-left: 1px solid #fbe04d;
        font-size: 12px;
        font-family: Arial, Helvetica, sans-serif;
        border-right: 1px solid #fbe04d;
        padding: 28px;
        clear: both;
    }

    .nav {
        background: none repeat scroll 0 0 #DEDEDE;
        border-bottom: 2px solid #CBCBCB;
        clear: both;
        color: graytext;
        font-size: 25px;
        font-weight: bolder;
        height: 35px;
        margin-top: 5px;
        padding-left: 23px;
        padding-top: 5px;
        width: 1250px;
    }

    .imgClass {
        padding-right: 15px;
    }

    .click {
        float: right;
        margin-right: 45px;
        margin-top: 5px;
        cursor: pointer;
        color: #229999;
        font-size: 14px;
        font-weight: bold;
    }

    .click:hover {
        color: #97D0E8;
    }

    .noDisplay {
        display: none;
    }

    .infoText {
        background: #777;
        color: white;
        font-weight: bold;
        font-size: 16px;
        border: 2px solid #e3dedd;
        padding: 20px;
        margin-top: 10px;
        margin-bottom: 10px;
    }
    </style>
</head>

<body>

<div class="roundedCorner whitebox marginTob clearfix" id="tabs">

    <ul id="tabMenu" class="clearfix clear">
        <li><span id="tab0" class="active" onclick="tabClick(0);">Application Statistics</span></li>
        <li><span id="tab1" onclick="tabClick(1);">Server Information</span></li>
        <li><span id="tab2" onclick="tabClick(2);">Server Console</span></li>
        %{--<li><span onclick="showReviews(this)">Reviews</span></li>
       <li><span onclick="showMerchantDetails(this)">Merchant</span></li>--}%
    </ul>

    <g:render template="userInfo"/>
    <g:render template="serverInfo" model="[cpuInfo:cpuInfo,memInfo:memInfo,serverInformation:serverInformation]"/>
    <g:render template="console" model="[workingDir:workingDir]"/>

</div>
</body>
</html>