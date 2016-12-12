<%--
  Created by IntelliJ IDEA.
  User: 李建成
  Date: 2016/10/19
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>版本管理</title>
    <jsp:include page="../include/compage.jsp" flush="true"></jsp:include>
    <script type="text/javascript" src="../../js/manager/pt.versionmanager.js"></script>

</head>
<body>
<div id="toolbar">
    查询类型：
    <select id="searchType">
        <option value="versionName">版本名称</option>
        <option value="content">版本描述</option>
    </select>
    <input id="searchName" type="text" name="searchName">
    </br>
</div>

<table id="devicetab" title="版本管理列表" style="margin:0px 0px 0px 0px;">
</table>

<div id="mdlg" class="easyui-dialog" style="width: 700px; height: 350px; top: 110px; padding: 10px 20px;" closed="true"
     buttons="#mdlg-buttons">
    <form id="addfm" method="post">
        <table>
            <tr>
                <td class="rightTd">版本名称：</td>
                <td>
                    <input type="text" name="versionName" id="versionName" class="easyui-validatebox"
                           validType="length[2,16]" required="required" missingMessage="不能为空">

                </td>

                <td class="rightTd">版本类型：</td>
                <td>
                    <select id="clientType" name="clientType">
                        <option value="0" selected>Android 版本</option>
                        <option value="1">IOS 版本</option>
                        <option value="2">Android IOS 版本</option>
                    </select>
                </td>


            </tr>

            <br>
            <tr>
                <td class="rightTd">版本号：</td>
                <td>
                    <input type="text" name="version" id="version" class="easyui-validatebox" validType="length[1,16]"
                           required="required" missingMessage="不能为空">
                    <input type="hidden" name="versionId" id="versionId" value="">
                </td>
                <td class="rightTd">最小版本号：</td>
                <td>
                    <input type="text" name="minimumVersion" id="minimumVersion" class="easyui-validatebox"
                           validType="length[1,16]"
                           required="required" missingMessage="不能为空">
                </td>
            </tr>
            <br>
            <tr>

                <td class="rightTd">下载地址：</td>
                <td>
                    <input type="text" name="url" id="url" class="easyui-validatebox"
                           validType="length[1,16]"
                           required="required" missingMessage="不能为空">
                </td>
                <td class="rightTd">分享地址：</td>
                <td>
                    <input type="text" name="sharurl" id="sharurl" class="easyui-validatebox"
                           validType="length[1,16]"
                           required="required" missingMessage="不能为空">
                </td>
            </tr>
            <tr>
                <td class="rightTd">版本描述：</td>
                <td>
                    <textarea name="content" id="content"
                              class="easyui-validatebox"
                              validType="length[2,100]"
                              required="required"
                              missingMessage="不能为空"
                              style="overflow:auto;width:100%;height:50px;"></textarea>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="mdlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" id="saveButn" iconcls="icon-save">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#mdlg').dialog('close');"
       iconcls="icon-cancel">取消</a>
</div>
</body>
</html>
