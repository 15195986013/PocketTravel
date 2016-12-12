<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>系统信息配置</title>
    <jsp:include page="../include/compage.jsp" flush="true"></jsp:include>
    <script type="text/javascript" src="../../js/manager/pt.systemmanager.js"></script>
</head>
<body style="margin: 0px 0px 0px 0px">
<div class="easyui-panel" buttons="#dlg-buttons" style="margin: 0px 0px 0px 0px">
    <div id="dlg" style="text-align: center;">
    <form id="addfm" method="post">
        <br>
        <div>
            <label for="sysVer">系统名称&nbsp;&nbsp;:</label>
            <input type="text" id="sysName" name="sysName" value="" style="width: 230px" class="easyui-validatebox"  validType="length[2,16]" required="required" missingMessage="不能为空">
        </div>
        <br>
        <div>
            <label for="sysVer">系统版本号:</label>
            <input type="text" id="sysVer" name="sysVer" value="" style="width: 230px" class="easyui-validatebox"  validType="length[2,10]" required="required" missingMessage="不能为空">
            <input type="hidden" id="pkId" name="pkId" value="">
        </div>
        <br>
        <div>
            <label for="techSupport">技术支持&nbsp;&nbsp;:</label>
            <input type="text" id="techSupport" name="techSupport" value=""  style="width: 230px;" class="easyui-validatebox"  validType="length[2,16]" required="required" missingMessage="不能为空">
        </div>
        <br>
        <div>
            <label for="contact">运维联系人:</label>
            <input type="text" id="contact" name="contact" value=""  style="width: 230px" class="easyui-validatebox"  validType="length[2,16]" required="required" missingMessage="不能为空">
        </div>
    </form>
</div>

<div id="dlg-buttons" style="margin: 5px 0px 5px 5px; text-align: center;">
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:saveSystemInfo();"
       iconcls="icon-ok">保存</a>
</div>
</div>
</body>
</html>
