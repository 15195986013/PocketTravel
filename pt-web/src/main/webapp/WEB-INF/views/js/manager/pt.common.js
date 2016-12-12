/**
 *         Created by 李建成 on 2016/11/30.
 *  ********* 封装 公有  增 删 改 查询*********
 */



var CommonUtils = {
    /**
     *      ******* 1.加载核心工厂******
     *      ******* 2.初始化数据表格 ****
     */
    inint_devicetab: '#devicetab',  //公用参数 table  初始值 #devicetab   可赋值

    inint_form: '#addfm',//公共参数form  初始值 #addfm   可赋值

    inint_dlg: '#mdlg', //公共参数mdlg  初始值 #mdlg   可赋值

    inint_treedlg: '#treedlg',   //公共参数treedlg  初始值 #treedlg   可赋值

    inint_treefunction: "#treefunction",

    Basic_dataGrid: {},//公共数据对象 ,初始值  { }   可赋值


    /**
     *    ***************  公共加载数据表格  （Basic_loadGrid）****************
     *   @constructor
     */
    Basic_loadGrid: function () {
        var LD = this.inint_devicetab;
        var BD = this.Basic_dataGrid;
        /**
         *   这里封装数据基本类型可更改
         *   @type {string}
         */
        BD.width = 'auto';
        BD.height = 'auto';
        BD.striped = true;
        BD.singleSelect = true;
        BD.loadMsg = "数据加载中请稍后……";
        BD.pagination = true;
        BD.rownumbers = true;
        BD.fitColumns = true;
        BD.singleSelect = false;
        BD.rownumbers = true;
        BD.rownumbers = true;

        $(LD).datagrid(BD);
    },
    /**
     *    ****  封装公共参数 *****
     *    添加或编辑 ajax 传参———>对象，
     */
    ajax_add_editor_data: {},
    /**
     *       ************* 公共删除 (Basic_Delete) *************
     * @param id
     * @param typeId
     * @param requestUrl
     * @constructor
     */
    Basic_Delete: function (id, typeId, requestUrl, requestTypes) {
        if (id == "0") {
            $.messager.alert()
        }
        var delete_devicetab = this.inint_devicetab; //接收全局变量 this 重定向为 Basic_Delete
        $.messager.confirm('提示框', '你确定要删除吗?', function (f) {
            if (f) {
                var row = $(delete_devicetab).datagrid('getSelections');
                var id = "";
                for (var i = 0; i < row.length; i++) {
                    id += row[i][typeId];
                    if (i < row.length - 1) {
                        id += ',';
                    }
                }
                var ajax_delete_devicetab = delete_devicetab; //  接收  Basic_Delete this 重定向 ajax_delete_devicetab
                $.ajax({
                    type: requestTypes,
                    url: requestUrl,
                    data: {"ids": id},
                    loadMsg: '数据提交中请稍后……',
                    success: function (data) {
                        if (data.code == "0") {
                            $.messager.alert('提示', ' 删除成功!');
                            $(ajax_delete_devicetab).datagrid("reload");
                        } else {
                            $.messager.alert('警告', data.msg);
                            $(ajax_delete_devicetab).datagrid('unselectAll');
                        }
                    },
                    error: function () {
                        $.messager.alert('警告', ' 删除失败，网络故障!');
                        $(ajax_delete_devicetab).datagrid('unselectAll');
                    }
                });
            }
        });
    },


    /**
     *       *********** 公共添加编辑方法  (BasicAdd_Editor) *************
     * @param Title
     * @param requestUrl
     * @param requestTypes
     * @constructor
     */
    BasicAdd_Editor: function (Title, requestUrl, requestTypes) {
        var isValidate = $(this.inint_form).form('validate');
        if (!isValidate) {
            $.messager.alert('警告', '请正确填写');
        } else {
            var add_editor_devicetab = this.inint_devicetab;
            var add_editor_dlg = this.inint_dlg;
            $.ajax({
                type: requestTypes,
                url: requestUrl,
                data: this.ajax_add_editor_data,
                success: function (data) {
                    if (data.code == "0" || data.code == 0) {
                        if (Title != null || Title != "") {
                            $.messager.alert('提示', '添加/修改' + Title + '成功!');
                            $(add_editor_dlg).dialog('close');
                            $(add_editor_devicetab).datagrid("reload");
                        } else {
                            $.messager.alert('提示', '添加/修改信息成功!');
                        }
                    } else {
                        $.messager.alert('警告', data.msg);
                    }
                },
                error: function () {
                    if (Title != null || Title != "") {
                        $.messager.alert('警告', '添加/修改' + Title + '失败，网络故障!');
                    } else {
                        $.messager.alert('警告', '添加/修改信息失败，网络故障!');
                    }
                }
            });

        }
    },

    /**
     *       ************ 公共获取对象信息方法  （Basic_getInfo）********
     *
     * @param id
     * @param typeId
     * @param requestUrl
     * @constructor
     */
    Basic_getInfo: function (id, typeId, requestUrl) {
        var getInfo_form = this.inint_form;
        $(getInfo_form).form('load',
            requestUrl + "?" + typeId + "=" + id
        );
    },

    /**
     *        *******公共启用关闭方法  （Basic_changeEnabled）********
     * @param id
     * @param typeId
     * @param requestUrl
     * @param requestTypes
     * @constructor
     */
    Basic_changeEnabled: function (id, typeId, requestUrl, requestTypes) {
        var add_editor_devicetab = this.inint_devicetab;
        $.ajax({
            type: requestTypes,
            url: requestUrl + '?' + typeId + '=' + id,
            data: {"ids": id},
            loadMsg: '数据提交中请稍后……',
            success: function (data) {
                if (data.code == 0 || data.code == '0') {
                    $.messager.alert("提示", data.msg);
                    $(add_editor_devicetab).datagrid("reload");
                } else {
                    $.messager.alert("提示", data.msg);
                    $(add_editor_devicetab).datagrid("reload");
                }
            },
            error: function () {
                $.messager.alert("警告", "网络异常,启用状态修改失败");
            }
        });
        $(this.inint_devicetab).datagrid('reload');
    },
    /**
     *      ******** 添加编辑公共打开窗口 （Basic_openPopupModel） *********
     * @param title      弹出标题
     * @param typeId     参数id
     * @constructor
     */
    Basic_openPopupModel: function (title, typeId) {
        $(this.inint_dlg).dialog("open").dialog('setTitle', title);
        // $(this.inint_dlg).panel("move",{top:$(document).scrollTop() + ($(window).height()-200) * 3.5});
        $(this.inint_form).form("clear");
        $(typeId).val("-1");
    },
    /**
     *   *************  公共加载返回树(json)数据  *********
     * @param Title             弹出模型 标题
     * @param selectTypeId      弹出模型选择器Id
     * @param selectTreeId     弹出模型Json 树 选择器Id
     * @param typeId            类型id
     * @param id                key
     * @param requestUrl        请求地址
     * @constructor            构造器
     */
    Basic_loadTreePopupModel: function (Title, selectTypeId, typeId, id, requestUrl) {
        $(this.inint_treedlg).dialog("open").dialog('setTitle', Title);
        /**
         *    1. 清空树
         *    2. 返回  json 树绑定到树选择器上
         */
        $(selectTypeId).val(id);
        $(this.inint_treefunction).form("clear");
        $(this.inint_treefunction).tree({
            url: requestUrl + '?' + typeId + '=' + id,
            loadFilter: function (data) {
                return data.data;
            }
        });
    },
    /**
     *   *********  JSON TREE（Basic_loadTreePopupSave）  保存数据  ***********
     * @param typeId
     * @param requestUrl
     * @constructor
     */
    Basic_loadTreePopupSave: function (typeId, requestUrl) {
        var basic_save_treefunction = this.inint_treefunction;
        var basic_save_treedlg = this.inint_treedlg;
        var basic_save_devicetab = this.inint_devicetab;
        $.messager.confirm('提示框', '你确定要提交吗?', function () {
            var nodes = $(basic_save_treefunction).tree('getChecked');
            var postjson = {typeId: $(typeId).val(), data: nodes};
            var ajax_save_treedlg = basic_save_treedlg; //this 从定向
            var ajax_save_devicetab = basic_save_devicetab; //this 从定向a
            $.ajax({
                type: 'POST',
                contentType: "application/json;charset=utf-8",
                url: requestUrl,
                data: JSON.stringify(postjson),
                async: false,
                dataType: 'json',
                loadMsg: '数据提交中请稍后……',
                success: function (data) {
                    if (data.code == "0") {
                        $.messager.alert('提示', data.msg);
                        $(ajax_save_treedlg).dialog('close');
                        $(ajax_save_devicetab).datagrid('reload');
                    } else {
                        $.messager.alert('警告', data.msg);
                    }
                },
                error: function () {
                    $.messager.alert('警告', '数据修改失败，网络故障!');
                }
            });
        });
    },

    /**
     *         动态级联模块  加载   省  市 区  三级，模块
     * @constructor
     */

    Basic_onloadChange: function () {
        $.ajax({
            type: 'POST',
            url: './getProvince',
            success: function (data) {
                if (data.code == "0") {
                    $("#Province").empty();//每次不然会重复添加
                    var provinceList = "<option value='0'>请选择</option>";
                    for (var i = 0; i < data.data.length; i++) {
                        provinceList = provinceList + '<option value=' + data.data[i].cityCode + '>' + data.data[i].cityName + '</option>';
                    }
                    $("#Province").append(provinceList);
                    $("#City").empty();
                    $("#District").empty();
                } else {
                    $.messager.alert('警告', data.msg);
                }
            },
            error: function () {
                $.messager.alert('警告', '失败，网络故障!');
            }
        });
    },
    Basic_selProvinceChange: function () {
        $.ajax({
            type: 'POST',
            url: './getCitys',
            data: {
                provinceCode: $('#Province').val()
            },
            success: function (data) {
                if (data.code == "0") {
                    $("#City").empty();//每次不然会重复添加
                    var cityList = "<option value='0'>请选择</option>";
                    for (var i = 0; i < data.data.length; i++) {
                        cityList = cityList + '<option value=' + data.data[i].cityCode + '>' + data.data[i].cityName + '</option>';
                    }
                    $("#City").append(cityList);
                    $("#District").empty();
                } else {
                    $.messager.alert('警告', data.msg);
                }
            },
            error: function () {
                $.messager.alert('警告', '失败，网络故障!');
            }
        });
    },
    Baisc_selCityChange: function () {
        $.ajax({
            type: 'POST',
            url: './getCounty',
            data: {
                cityCode: $('#City').val()
            },
            success: function (data) {
                if (data.code == "0") {
                    $("#District").empty();//每次不然会重复添加
                    var countyList = "<option value='0'>请选择</option>";
                    for (var i = 0; i < data.data.length; i++) {
                        countyList = countyList + '<option value=' + data.data[i].cityCode + '>' + data.data[i].cityName + '</option>';
                    }
                    $("#District").append(countyList);
                } else {
                    $.messager.alert('警告', data.msg);
                }
            },
            error: function () {
                $.messager.alert('警告', '失败，网络故障!');
            }
        });
    },


    //清空缓存
    Basic_xialachange: function () {
        var province = $('#Province').val();
        var city = $('#City').val();
        var county = $('#District').val();
        this.Basic_loadGrid();
        province = null;
        city = null;
        county = null;
    },


    /**
     *  闭包函数
     */



    /**
     * 不定参盒子 打印传参信息
     */
    getValue: function (args) {
        if (args != null) {
            console.log(args);
        } else {
            console.log("正确传参...")
        }
        return args;
    },
    ValueBox: function () {
        var cont = arguments.length;
        try {
            for (var i = 0; i < cont; i++) {
                this.getValue(arguments[i])
                return cont;
            }
        } catch (e) {
            this.getValue(e)
        }
    }

};

/**
 * @type {{inint_devicetab: string, inint_form: string, inint_dlg: string, inint_treedlg: string, inint_treefunction: string, Basic_dataGrid: {}, Basic_loadGrid: CommonUtils.Basic_loadGrid, ajax_add_editor_data: {}, Basic_Delete: CommonUtils.Basic_Delete, BasicAdd_Editor: CommonUtils.BasicAdd_Editor, Basic_getInfo: CommonUtils.Basic_getInfo, Basic_changeEnabled: CommonUtils.Basic_changeEnabled, Basic_openPopupModel: CommonUtils.Basic_openPopupModel, Basic_loadTreePopupModel: CommonUtils.Basic_loadTreePopupModel, Basic_loadTreePopupSave: CommonUtils.Basic_loadTreePopupSave, Basic_onloadChange: CommonUtils.Basic_onloadChange, Basic_selProvinceChange: CommonUtils.Basic_selProvinceChange, Baisc_selCityChange: CommonUtils.Baisc_selCityChange, Basic_xialachange: CommonUtils.Basic_xialachange}}
 */
