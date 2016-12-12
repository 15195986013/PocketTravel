/**
 * Created by 李建成
 * on 2016/11/29.
 */

$(function () {
    var $inp = $('input');
    $inp.keypress(function (e) { //这里给function一个事件参数命名为e，叫event也行，随意的，e就是IE窗口发生的事件。
        $('#ermsg').css("display", "none");
        $('#info').css("display", "none");
        var key = e.which; //e.which是按键的值
        if (key == 13) {
            isRightCode();
        }
    });
})



function login() {
    var username = $('#username').val();
    var password = $('#password').val();
    $.ajax({
        type: 'POST',
        url: 'logincheckd.json',
        async: false,//同步
        data: {
            account: username,
            password: password
        },
        dataType: 'json',
        success: function (data) {
            if (data.code == 0) {
                location.href = "./index.html";
            } else {
                $('#ermsg').css("display", "block");
            }
        },
        error: function () {
            $('#ermsg').css("display", "block");
            changeImg();
        }
    });
}

/*验证码*/

function changeImg() {
    var imgSrc = $("#imgObj");
    var src = imgSrc.attr("src");
    imgSrc.attr("src", chgUrl(src));
}
//时间戳
//为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
function chgUrl(url) {
    var timestamp = (new Date()).valueOf();
    url = url.substring(0, 17);
    if ((url.indexOf("&") >= 0)) {
        url = url + "×tamp=" + timestamp;
    } else {
        url = url + "?timestamp=" + timestamp;
    }
    return url;
}
function isRightCode() {
    var code = $("#veryCode").val();
    console.log(code);
    code = "c=" + code;
    $.ajax({
        type: "POST",
        url: "resultServlet/validateCode",
        data: code,
        success: function (data) {
            callback(data);
        }
    });
}
function callback(data) {
    if (data == 1) {
        login();
    } else {
        $('#info').css("display", "block");
        changeImg();
    }
}