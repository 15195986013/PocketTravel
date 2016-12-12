/**
 *             Created by 李建成
 *         @date 2016/12/2-15:42.on NJBD
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
});
$C.Basic_dataGrid = {
    url: 'getPostList.json',
    queryParams: {},
    columns: [[
        {field: 'deleteId', checkbox: 'true', align: 'center', width: getwidth(0.1)},
        {field: 'name', title: '姓名', align: 'center', width: getwidth(0.1)},
        {field: 'appraisal', title: '预估价格', align: 'center', width: getwidth(0.1)},
        {field: 'startPostion', title: '起点', align: 'center', width: getwidth(0.2)},
        {field: 'endPostion', title: '终点', align: 'center', width: getwidth(0.2)},
        {
            field: 'state', title: '状态', align: 'center', width: getwidth(0.2),
            formatter: function (value) {
                switch (value) {
                    case 0 :
                        return "<h4>订单未完成</h4>";
                    case 1:
                        return "<h4>订单已经完成</h4>";
                }

            }
        },
        {field: 'pubDate', title: '发布时间', align: 'center', width: getwidth(0.2)}
    ]],
};