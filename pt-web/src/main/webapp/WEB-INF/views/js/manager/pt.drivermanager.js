/**
 *             Created by 李建成
 *         @date 2016/12/5-11:08.on NJBD
 */
var $C = CommonUtils;
$(function () {
    $C.Basic_loadGrid();//始终保持数据加载
    $("#searchName").on('keyup', function () {//搜索
        $C.Basic_dataGrid.queryParams = {
            searchType: $("#searchType option:selected").val(),
            searchName: $("#searchName").val()
        }
        $C.Basic_loadGrid();
    });
});
$C.Basic_dataGrid = {
    url: 'getAllDriver.json',
    columns: [[
        {field: 'deleteId', title: '多选', align: 'center', width: getwidth(0.1), checkbox: 'true'},
        {field: 'name', title: '名称', align: 'center', width: getwidth(0.1)},
        {field: 'vehiclenumber', title: '行使证号', align: 'center', width: getwidth(0.2)},
        {field: 'drivingNumber', title: '驾驶证号', align: 'center', width: getwidth(0.2)},
        {field: 'platenumber', title: '车牌号', align: 'center', width: getwidth(0.2)},
        {field: 'identitynumber', title: '身份证号', align: 'center', width: getwidth(0.2)},
        {field: 'units', title: '司机信息', align: 'center', width: getwidth(0.2)},
        {
            field: 'state', title: '审核通过', align: 'center', width: getwidth(0.1), formatter: function (value, row) {
            if (value == 1) {
                return "<a href='javascript:void(0)' onclick=\" $C.Basic_changeEnabled('" + row.id + "','DriverId','changeStatus.json','POST');\">点击取消</a>";
            } else {
                return "<a href='javascript:void(0)' onclick=\"$C.Basic_changeEnabled('" + row.id + "','DriverId','changeStatus.json','POST');\">审核</a>";
            }
        }
        },
        {field: 'updateTime', title: '修改时间', align: 'center', width: getwidth(0.1)}
    ]]
};
