/**
 *             Created by 李建成
 *         @date 2016/12/1-13:38.on NJBD
 */
var $C = CommonUtils;
$(function () {
    $C.Basic_loadGrid();//始终保持数据加载
    add_editor_Admin(); //管理员添加
    saveAdminfunction();//保存管理员
    $("#searchName").on('keyup', function () {//搜索
        $C.Basic_dataGrid.queryParams = {
            searchType: $("#searchType option:selected").val(),
            searchName: $("#searchName").val()
        }
        $C.Basic_loadGrid();
    });
});
$C.Basic_dataGrid = {
    url: 'getAllUser.json',
    columns: [[
        {field: 'deleteId', title: '多选', align: 'center', width: getwidth(0.1), checkbox: 'true'},
        {field: 'account', title: '用户名', align: 'center', width: getwidth(0.1)},
        {field: 'name', title: '当前角色', align: 'center', width: getwidth(0.1)},
        {
            field: 'adminId',
            title: '角色分配',
            align: 'center',
            width: getwidth(0.1),
            formatter: function (value) {
                return "<a href='javascript:void(0)' onclick=\" +  $C.Basic_loadTreePopupModel('用户角色分配','#adminIds','adminId','" + value + "','getAdminRoles.json');\">角色分配</a>";
            }
        },
        {
            field: 'isEnabled', title: '是否启用', align: 'center', width: getwidth(0.1), formatter: function (value, row) {
            if (value == 1) {
                return "<a href='javascript:void(0)' onclick=\" $C.Basic_changeEnabled('" + row.adminId + "','adminId','changeEnabled.json','POST');\">否</a>";
            } else {
                return "<a href='javascript:void(0)' onclick=\"$C.Basic_changeEnabled('" + row.adminId + "','adminId','changeEnabled.json','POST');\">是</a>";
            }
        }
        },
        {field: 'updateTime', title: '修改时间', align: 'center', width: getwidth(0.1)}
    ]],
    toolbar: [{
        id: 'add', text: '添加用户', iconCls: 'icon-add', handler: function () {
            $C.Basic_openPopupModel('添加用户', '#adminId'); // title id选择器
        }
    }, '-',
        {
            id: 'update', text: '编辑', iconCls: 'icon-edit', handler: function () {
            var row = $("#devicetab").datagrid('getSelected');
            if (row != null) {
                $C.Basic_openPopupModel('编辑用户', '#adminId');
                $("#devicetab").datagrid('unselectAll');
                $C.Basic_getInfo(row["adminId"], 'adminId', 'selectByAdminId.json');
            } else {
                $.messager.alert('提示', "请选择一条数据");
            }
        }
        }, '-',
        {
            id: 'delete', text: '删除', iconCls: 'icon-remove', handler: function () {
            var row = $("#devicetab").datagrid('getSelected');
            if (row != null) {
                $C.Basic_Delete(row["adminId"], 'adminId', 'deleteAdmin.json');
            } else {
                $.messager.alert('提示', "请选择一条数据");
            }
        }
        }
    ]
};
/**
 * 保存权限
 */
function saveAdminfunction() {
    $("#savefunButn").click(function () {
        $C.Basic_loadTreePopupSave('#adminIds', 'saveAdminRole.json');
    })
}
/**
 * 添加|编辑 管理员操作
 */
function add_editor_Admin() {
    $("#saveButn").click(function () {
        $C.ajax_add_editor_data = {
            adminId: $("#adminId").val(),
            account: $('#account').val(),
            password: $('#password').val(),
            isEnabled: $('#isEnabled').val()
        };
        $C.BasicAdd_Editor('用户', 'addAdmin.json', 'POST');
    })
}


