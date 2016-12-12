

var $C=CommonUtils;
$(function () {

    $("#searchName").on('keyup',function () {//搜索
        $C.Basic_dataGrid.queryParams={
            searchType:  $("#searchType option:selected").val(),
            searchName: $("#searchName").val()
        }
        $C.Basic_loadGrid();
    });



    $C.Basic_loadGrid(); //加载数据网格
    add_editor_role(); // 添加编辑权限
    savefunction();// 保存权限
});
/**
 * 函数调用
 */
$C.Basic_dataGrid = {
    url: 'getAllRole.json',
    queryParams: {},
    columns: [[
        {field: 'deleteId', checkbox: 'true', align: 'center', width: getwidth(0.1)},
        {field: 'name', title: '角色名称', align: 'center', width: getwidth(0.1)},
        {field: 'description', title: '角色描述', align: 'center', width: getwidth(0.1)},
        {
            field: 'isEnabled',
            title: '是否启用',
            align: 'center',
            width: getwidth(0.1),
            formatter: function (value, row) {
                if (value == 1) {
                    return "<a href='javascript:void(0)' onclick=\"$C.Basic_changeEnabled('" + row.roleId + "','roleId','changeRoleEnabled.json','POST');\">否</a>";
                } else {
                    return "<a href='javascript:void(0)' onclick=\"$C.Basic_changeEnabled('" + row.roleId + "','roleId','changeRoleEnabled.json','POST');\">是</a>";
                }
            }
        },
        {field: 'createTime', title: '创建时间', align: 'center', width: getwidth(0.1)},
        {
            field: 'roleId',
            title: '权限分配',
            align: 'center',
            width: getwidth(0.1),
            formatter: function (value) {
                return "<a href='javascript:void(0)' onclick=\" +  $C.Basic_loadTreePopupModel('用户权限修改','#roleIds','pkId','" + value + "','getmanagertbfunction.json');\">权限分配</a>";
            }
        },

    ]],
    /**
     *  加载按钮组件
     */
    toolbar: [{
        id: 'add', text: '添加角色', iconCls: 'icon-add', handler: function () {
            $C.Basic_openPopupModel('添加角色', '#roleId');
        }
    }, '-',
        {
            id: 'update', text: '编辑', iconCls: 'icon-edit', handler: function () {
            var row = $("#devicetab").datagrid('getSelected');
            if (row != null) {
                $C.Basic_openPopupModel('编辑角色', '#roleId');
                $("#devicetab").datagrid('unselectAll');
                $C.Basic_getInfo(row["roleId"], 'roleId', 'selectRoleById.json');
            } else {
                $.messager.alert("提示", "请选择一条数据");
            }
        }
        }, '-',
        {
            id: 'delete', text: '删除', iconCls: 'icon-remove', handler: function () {
            var row = $("#devicetab").datagrid('getSelected');
            if (row != null) {
                $C.Basic_Delete(row["roleId"], 'roleId', 'deleteRole.json', 'POST');
            } else {
                $.messager.alert("提示", "请选择一条数据");
            }
        }
        }
    ]
};



/**
 * 保存权限
 */
function savefunction() {
    $("#savefunButn").click(function () {
        $C.Basic_loadTreePopupSave('#roleIds', 'savemanagerfunction.json');
    })
}
/**
 *  添加编辑权限
 */
function add_editor_role() {
    $("#saveButn").click(function () {
        $C.ajax_add_editor_data = {
            roleId: $('#roleId').val(),
            name: $('#name').val(),
            description: $('#description').val(),
            isEnabled: $('#isEnabled').val()
        };
        $C.BasicAdd_Editor('角色', 'addRole.json', 'POST');
    })
}
