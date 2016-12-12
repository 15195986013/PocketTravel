/**
 *             Created by 李建成
 *         @date 2016/12/1-16:31.on NJBD
 */
var $C = CommonUtils;
$(function () {
    $C.Basic_loadGrid(); //加载数据网格
    $("#searchName").on('keyup',function () {//搜索
        $C.Basic_dataGrid.queryParams={
            searchType:  $("#searchType option:selected").val(),
            searchName: $("#searchName").val()
        }
        $C.Basic_loadGrid();
    });
});
$C.Basic_dataGrid = {
    url: 'getAppFeedBack.json',
    queryParams: {},
    columns: [[
        {field: 'deleteId', checkbox: 'true', align: 'center', width: getwidth(0.1)},
        {field: 'nickName', title: '反馈人', align: 'center', width: getwidth(0.2)},
        {field: 'content', title: '反馈信息', align: 'center', width: getwidth(0.2)},
        {
            field: 'clientType', title: '客户端类型', align: 'center', width: getwidth(0.2),
            formatter: function (value) {
                switch (value) {
                    case "0" :
                        return "<h4>安卓用户</h4>";
                    case "1":
                        return "<h4>IOS用户</h4>";
                }

            }
        },
        {field: 'createTime', title: '反馈时间', align: 'center', width: getwidth(0.1)}
    ]]
};

