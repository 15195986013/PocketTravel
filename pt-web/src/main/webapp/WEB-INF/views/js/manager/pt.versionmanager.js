/**
 * Created by 李建成
 * ON 2016/10/19 17:05.
 */
var $C=CommonUtils;
$(function () {
    $C.Basic_loadGrid(); //加载数据网格
    $("#searchName").on('keyup',function () {//搜索
        $C.Basic_dataGrid.queryParams={
            searchType:  $("#searchType option:selected").val(),
            searchName: $("#searchName").val()
        };
        $C.Basic_loadGrid();

    });
    add_edrtor_version();
});
/**
 * 函数调用
 */
$C.Basic_dataGrid = {
    url: 'getAllVersion.json',
    queryParams: {},
    columns: [[
        {field: 'deleteId', checkbox: 'true', align: 'center', width: getwidth(0.1)},
        {field: 'versionName', title: '版本名称', align: 'center', width: getwidth(0.1)},
        {field: 'version', title: '最大版本', align: 'center', width: getwidth(0.1)},
        {field: 'minimumVersion', title: '最小版本', align: 'center', width: getwidth(0.1)},
        {field: 'url', title: '下载地址', align: 'center', width: getwidth(0.2)},
        {
            field: 'clientType', title: '版本类型', align: 'center', width: getwidth(0.2),
            formatter: function (value, row) {
                if (value == 0) {
                    return "<h4 style='color:#00f947 '>Android 版本 </h4> "
                } else if (value == 1) {
                    return "<h4 style='color:#00e1ff '> IOS 版本   </h4>"
                } else {
                    return "<h4 style='color:#f91800 '>Android  IOS  兼容版本   </h4> "
                }
            }
        },
        {field: 'content', title: '版本描述', align: 'center', width: getwidth(0.2)},
        {field: 'createTime', title: '创建时间', align: 'center', width: getwidth(0.2)},
    ]], toolbar: [{
        id: 'add', text: '新增版本', iconCls: 'icon-add', handler: function () {
            $C.Basic_openPopupModel('新增版本', '#versionId');
        }
    }, '-',
        {
            id: 'update', text: '编辑', iconCls: 'icon-edit', handler: function () {
            var row = $("#devicetab").datagrid('getSelected');
            if (row != null) {
                $C.Basic_openPopupModel('编辑版本', '#versionId');
                $("#devicetab").datagrid('unselectAll');
                $C.Basic_getInfo(row["versionId"], 'versionId', 'selectVersionById.json');
            } else {
                $.messager.alert("提示", "请选择一条数据");
            }
        }
        }, '-',
        {
            id: 'delete', text: '删除', iconCls: 'icon-remove', handler: function () {
            var row = $("#devicetab").datagrid('getSelected');
            if (row != null) {
                $C.Basic_Delete(row["versionId"], 'versionId', 'deleteVersion.json', 'POST');
            } else {
                $.messager.alert("提示", "请选择一条数据");
            }
        }
        }
    ]
};
function add_edrtor_version() {
    $("#saveButn").click(function () {
        $C.ajax_add_editor_data = {
            versionId: $('#versionId').val(),
            version: $('#version').val(),
            url: $('#url').val(),
            shar: $('#url').val(),
            versionName: $('#versionName').val(),
            content: $('#content').val(),
            minimumVersion: $('#minimumVersion').val(),
            clientType: $('#clientType').val()
        };
        $C.BasicAdd_Editor('版本', 'addVersion.json', 'POST');
    })
}