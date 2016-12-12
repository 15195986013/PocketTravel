<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员后台登录</title>
    <link href="../../css/manager/main.css" rel="stylesheet" type="text/css"/>
    <link href="../../css/manager/pt.login.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="../../js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="../../js/common/fun.base.js"></script>
    <script type="text/javascript" src="../../js/manager/pt.login.js"></script>
    <script src="../../js/common/DD_belatedPNG.js" type="text/javascript"></script>
    <script>DD_belatedPNG.fix('.png')</script>
</head>
<body>


<div class="login">
    <h1 style="margin: 0  auto; width:40%;">${sysInfo.sysName}</h1>
    <div class="box png">
        <%--      <div class="logo png"></div>--%>
        <div style="text-align: center;padding-top: 30px">

        </div>
        <div class="input" style="margin-top: -60px;">
            <div class="log">
                <div class="name">
                    <label>用户名</label>
                    <input type="text" class="text" id="username" placeholder="用户名" name="username" tabindex="1">
                </div>
                <div class="pwd">
                    <label>密&nbsp;&nbsp;&nbsp;码</label><input type="password" class="text" id="password"
                                                              placeholder="密码" name="password" tabindex="2"
                                                              style="margin-left:4px;">
                </div>
                <div class="codebox">
                    <label>验证码</label>
                    <input id="veryCode" name="veryCode" type="text" class="text" style="  width: 60px;"/>
                    <img id="imgObj" alt="" src="xuan/verifyCode" style="position:relative; top: 10px ;width: 80px"
                         onclick="changeImg()"/>
                    <input type="button" class="submit" onclick="isRightCode()" tabindex="3" value="登录">
                    <div id="ermsg" style="color: red;text-align: left; display: none; font-size: 10px;">用户名或密码错误！</div>
                    <div id="info" style="color: red;text-align: left; display: none; font-size: 10px;">验证码错误！</div>
                </div>
                <div class="tip">
                </div>
            </div>
        </div>
    </div>
    <div class="footer"></div>
</div>
<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
    <p>适用浏览器：FireFox、Chrome、Safari. 不支持IE8及以下浏览器。</p>
    <p>技术支持:${sysInfo.techSupport}</p>
    <p>运维联系人:${sysInfo.contact}</p>
    <p>系统版本:${sysInfo.sysVer}</p>

</div>


</body>
</html>
