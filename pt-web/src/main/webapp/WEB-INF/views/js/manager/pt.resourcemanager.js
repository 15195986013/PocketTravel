/**
 * Created by 李建成
 * on 2016/11/30.
 */
var $C = CommonUtils;
$(function () {
    $C.Basic_loadGrid(); //加载数据网格
    $("#searchName").on('keyup', function () {//搜索
        $C.Basic_dataGrid.queryParams = {
            searchType: $("#searchType option:selected").val(),
            searchName: $("#searchName").val()
        }
        $C.Basic_loadGrid();
    });
    LoadAllResource();
    add_editor_Resource(); // 添加编辑权限
});
/**
 * 函数调用
 */
$C.Basic_dataGrid = {
    url: 'getAllResource.json',
    columns: [[
        {field: 'deleteId', checkbox: 'true', align: 'center', width: getwidth(0.1)},
        {field: 'name', title: '权限名称', align: 'center', width: getwidth(0.1)},
        {field: 'url', title: '权限地址', align: 'center', width: getwidth(0.1)},
        {field: 'level', title: '所属等级', align: 'center', width: getwidth(0.1)},
        {field: 'createTime', title: '创建时间', align: 'center', width: getwidth(0.1)},
    ]],
    toolbar: [{
        id: 'add', text: '添加资源权限', iconCls: 'icon-add ', handler: function () {
            $C.Basic_openPopupModel('添加资源', '#resourceId');
        }
    }, '-',
        {
            id: 'update', text: '编辑', iconCls: 'icon-edit', handler: function () {
            var row = $("#devicetab").datagrid('getSelected');
            if (row != null) {
                $C.Basic_openPopupModel('编辑资源', '#resourceId');
                $("#devicetab").datagrid('unselectAll');
                $C.Basic_getInfo(row["resourceId"], 'resourceId', 'selectResourceById.json');
            } else {
                $.messager.alert("提示", "请选择一条数据");
            }
        }
        }
    ]
};


function add_editor_Resource() {
    $("#parentId").change(function () {
        if ($(this).val() == null || $("#parentId").val() == "0") {
            $("#url").attr("readonly", "readonly");
            $("#url").val("none")
        } else {
            $("#url").val("").focus(); // 清空并获得焦点
            $("#url").removeAttr("readonly");
        }
    });
    $("#saveButn").click(function () {
        if ($("#parentId").val() == null) {
            $.messager.alert("警告", "请正确填写文本信息");
            return false;
        } else {
            $C.ajax_add_editor_data = {
                parentId: $('#parentId').val(),
                resourceId: $('#resourceId').val(),
                name: $('#name').val(),
                url: $('#url').val(),
            };
            $C.BasicAdd_Editor('资源', 'addResource.json', 'POST');
        }
    });

}
function LoadAllResource() {
    $.ajax({
        type: 'POST',
        url: 'getSelectAllResourcer.json',
        success: function (data) {
            if (data.code == "0") {
                $("#parentId").empty();//每次不然会重复添加
                var ResList = "<option value='0' >创建一级导航</option>";
                for (var i = 0; i < data.data.length; i++) {
                    ResList = ResList + '<option  value=' + data.data[i].resourceId + ' >' + data.data[i].name + '</option>';
                }
                $("#parentId").append(ResList);
            } else {
                $.messager.alert('警告', data.msg);
            }
        },
        error: function () {
            $.messager.alert('警告', '失败，网络故障!');
        }
    });
}