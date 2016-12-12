/**
 *             Created by 李建成
 *         @date 2016/12/1-13:10.on NJBD
 */


$(function () {
    loadSystemInfo();
});

function loadSystemInfo() {
    $.ajax({
        type: 'post',
        url: 'selectSystemInfo.json',
        contentType: 'application/json;charset=utf-8',
        async: false,
        dataType: 'json',
        loadMsg: '数据提交中请稍后……',
        success: function (data) {
            if (data.code == 0) {
                $("#addfm").form("load", data.sysInfo);
            } else {
                $.messager.alert("警告", data.msg);
            }
        },
        error: function () {
            $.messager.alert('警告', '系统信息获取失败，网络故障!');
        }
    });
}
/**
 * 保存公司信息
 */
function saveSystemInfo() {
    $('#addfm').form('submit', {
        url: 'saveSystemInfo.json',
        success: function (result) {
            var data = JSON.parse(result);
            if (data.code == "0") {
                $.messager.alert('提示', "版本设置成功！");
            } else {
                $.messager.alert('警告', data.msg);
            }
        }
    });
}