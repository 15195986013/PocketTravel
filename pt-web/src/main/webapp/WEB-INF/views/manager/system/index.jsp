<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>后台管理系统</title>
    <jsp:include page="../include/compage.jsp" flush="true"/>
    <link href="../../css/manager/default.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="../../js/manager/pt.index.js"></script>
    <style>
        #mdlg {
            width: 350px;
            height: 190px;
            padding: 10px 20px;
        }

        #pageloading {
            position: absolute;
            top: 50%;
            left: 50%;
            margin: -120px 0px 0px -120px;
            text-align: center;
            border: 2px solid #8DB2E3;
            width: 200px;
            height: 40px;
            font-size: 14px;
            padding: 10px;
            font-weight: bold;
            background: #fff;
            color: #15428B;
        }

        #loading-mask {
            position: absolute;
            top: 0px;
            left: 0px;
            width: 100%;
            height: 100%;
            background: #D2E0F2;
            z-index: 20000
        }

        #ErrorInfo {
            position: absolute;
            z-index: 100000;
            height: 2046px;
            top: 0px;
            left: 0px;
            width: 100%;
            background: white;
            text-align: center;
        }

        #Banner_Div {
            overflow: hidden;
            height: 30px;
            background: #7f99be repeat-x center 50%;
            line-height: 20px;
            color: #fff;
            font-family: Verdana, 微软雅黑, 黑体
        }

        #banner_span {
            float: right;
            padding-right: 20px;
        }
        #banner_span01{
            padding-left:10px; font-size: 16px;
        }

    </style>
</head>
<body class="easyui-layout" style="overflow-y:hidden" fit="true" scroll="no">
<noscript>
    <div id="ErrorInfo">
        <img src="../../images/noscript.gif" alt='抱歉，请开启脚本支持！'/>
    </div>
</noscript>
<div id="loading-mask">
    <div id="pageloading">
        <img src="../../images/loading.gif" align="absmiddle"/> 正在加载中,请稍候...
    </div>
</div>
<div region="north" split="true" border="false" id="Banner_Div">
        <span class="head" id="banner_span">欢迎 系统管理员
            <a href="javascript:void(0);" onclick="javascript:popupmodel('用户密码修改');" id="editpass">修改密码</a>
            <a href="./logout.html" id="loginOut">安全退出</a>
        </span>
    <span id="banner_span01">
        <img src="../../images/blocks.gif" width="20" height="20" align="absmiddle"/>掌上出行服务平台</span>
</div>
<div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
    <div class="footer"></div>
</div>
<div region="west" split="true" title="导航菜单" style="width:180px;" id="west">
    <div id="nav">

    </div>
</div>

<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
    <div id="tabs" class="easyui-tabs" fit="true" border="false">
        <div title="欢迎使用" style="padding:20px;overflow:hidden; color:red; ">
            <div id="maincontext">
                <H1>欢迎使用</H1>
            </div>
        </div>
    </div>
</div>


<div id="mdlg" class="easyui-dialog" style="" closed="true"
     buttons="#mdlg-buttons">
    <form id="passwordfm" method="post">
        <table>
            <tr>
                <td>当前密码：</td>
                <td>
                    <input type="password" name="oldpassword" id="oldpassword" value="">
                </td>
            </tr>
            <tr>
                <td>新密码：</td>
                <td>
                    <input type="password" name="password" id="password" value="">
                </td>
            </tr>
            <tr>
                <td>确认密码：</td>
                <td>
                    <input type="password" name="confirmpassword" id="confirmpassword" value="">
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="mdlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:changepassword();"
       iconcls="icon-save">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#mdlg').dialog('close');"
       iconcls="icon-cancel">取消</a>
</div>
</body>
</html>
