/**
 * Created by 李建成
 * on 2016/11/30.
 */


window.onload = function () {
    $('#loading-mask').fadeOut();
    InitLeftMenu();
    tabCloseEven();
    //获取系统信息
    $.ajax("indexSystemInfo.json",
        function (data, status) {
            if (status == "success") {
                $(".footer").append("技术支持：" + data.data.techSupport + " 版本号：" + data.data.sysVer + " 运维联系人：" + data.data.contact);
            }
        });
}


function popupmodel(title) {
    $("#mdlg").dialog("open").dialog('setTitle', title);
    $("#passwordfm").form("clear");
}
function changepassword() {
    var oldpassword = $("#oldpassword").val();
    var password = $("#password").val();
    var confirmpassword = $("#confirmpassword").val();
    if (password == "" || confirmpassword == "") {
        alert("请填写内容再提交");
        return;
    } else {
        if (password == confirmpassword) {
            $.ajax({
                type: 'POST',
                url: 'changepassword.json',
                data: {
                    newpassword: confirmpassword,
                    oldpassword: oldpassword
                },
                success: function (data) {
                    if (data.code == "0") {
                        $.messager.alert('提示', "密码修改成功，请重新登录！");
                        $("#mdlg").dialog("close");
                        setTimeout("window.location.href='./logout.html'", 2000);
                    } else {
                        $.messager.alert('警告', data.msg);
                    }
                }
            });
            return;
        }
        $.messager.alert('提醒', '两次密码不同，请重新输入');
        return;
    }
}

var _menus = {};
//同步获取
$.ajax({
    type: 'GET',
    url: 'menu.json',
    async: false,
    dataType: 'json',
    success: function (data) {
        if (data.code == 0) {
            _menus = data;
        }

    },
    error: function () {
        $.messager.alert("提示", "系统异常稍后再试！！！")
    }
});

var onlyOpenTitle = "欢迎使用";//不允许关闭的标签的标题


//初始化左侧导航
function InitLeftMenu() {
    $("#nav").accordion({animate: false, fit: true, border: false});
    var selectedPanelname = '';
    $.each(_menus.menus, function (i, n) {
        var menulist = '';
        menulist += '<ul class="navlist">';
        $.each(n.menus, function (j, o) {
            menulist += '<li><div ><a ref="' + o.id + '" href="#" rel="' + o.url + '" ><span class="icon ' + o.icon + '" >&nbsp;</span><span class="nav">' + o.name + '</span></a></div> ';
            if (o.child && o.child.length > 0) {
                menulist += '<ul class="third_ul">';
                $.each(o.child, function (k, p) {
                    menulist += '<li><div><a ref="' + p.id + '" href="#" rel="' + p.url + '" ><span class="icon ' + p.icon + '" >&nbsp;</span><span class="nav">' + p.name + '</span></a></div> </li>'
                });
                menulist += '</ul>';
            }
            menulist += '</li>';
        })
        menulist += '</ul>';
        $('#nav').accordion('add', {
            title: n.name,
            content: menulist,
            border: false,
            iconCls: 'icon ' + n.icon
        });
        if (i == 0)
            selectedPanelname = n.name;
    });
    $('#nav').accordion('select', selectedPanelname);

    $('.navlist li a').click(function () {
        var tabTitle = $(this).children('.nav').text();
        var url = $(this).attr("rel");
        var id = $(this).attr("ref");
        var icon = $(this).find('.icon').attr('class');
        var third = $(this).find(id);
        if (third && third.child && third.child.length > 0) {
            $('.third_ul').slideUp();
            var ul = $(this).parent().next();
            if (ul.is(":hidden"))
                ul.slideDown();
            else
                ul.slideUp();
        }
        else {
            addTab(tabTitle, url, icon);
            $('.navlist li div').removeClass("selected");
            $(this).parent().addClass("selected");
        }
    }).hover(function () {
        $(this).parent().addClass("hover");
    }, function () {
        $(this).parent().removeClass("hover");
    });


}


function addTab(subtitle, url, icon) {
    if (!$('#tabs').tabs('exists', subtitle)) {
        $('#tabs').tabs('add', {
            title: subtitle,
            content: createFrame(url),
            closable: true,
            icon: icon
        });
    } else {
        $('#tabs').tabs('select', subtitle);
        $('#mm-tabupdate').click();
    }
    tabClose();
}

function createFrame(url) {
    var s = '<iframe scrolling="yes" frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
    return s;
}

function tabClose() {
    $(".tabs-inner").dblclick(function () {
        var subtitle = $(this).children(".tabs-closable").text();
        $('#tabs').tabs('close', subtitle);
    })
    $(".tabs-inner").bind('contextmenu', function (e) {
        $('#mm').menu('show', {
            left: e.pageX,
            top: e.pageY
        });

        var subtitle = $(this).children(".tabs-closable").text();
        $('#mm').data("currtab", subtitle);
        $('#tabs').tabs('select', subtitle);
        return false;
    });
}


//绑定右键菜单事件
function tabCloseEven() {

    $('#mm').menu({
        onClick: function (item) {
            closeTab(item.id);
        }
    });

    return false;
}

function closeTab(action) {
    var alltabs = $('#tabs').tabs('tabs');
    var currentTab = $('#tabs').tabs('getSelected');
    var allTabtitle = [];
    $.each(alltabs, function (i, n) {
        allTabtitle.push($(n).panel('options').title);
    })


    switch (action) {
        case "refresh":
            var iframe = $(currentTab.panel('options').content);
            var src = iframe.attr('src');
            $('#tabs').tabs('update', {
                tab: currentTab,
                options: {
                    content: createFrame(src)
                }
            })
            break;
        case "close":
            var currtab_title = currentTab.panel('options').title;
            $('#tabs').tabs('close', currtab_title);
            break;
        case "closeall":
            $.each(allTabtitle, function (i, n) {
                if (n != onlyOpenTitle) {
                    $('#tabs').tabs('close', n);
                }
            });
            break;
        case "closeother":
            var currtab_title = currentTab.panel('options').title;
            $.each(allTabtitle, function (i, n) {
                if (n != currtab_title && n != onlyOpenTitle) {
                    $('#tabs').tabs('close', n);
                }
            });
            break;
        case "closeright":
            var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);

            if (tabIndex == alltabs.length - 1) {
                alert('亲，后边没有啦 ^@^!!');
                return false;
            }
            $.each(allTabtitle, function (i, n) {
                if (i > tabIndex) {
                    if (n != onlyOpenTitle) {
                        $('#tabs').tabs('close', n);
                    }
                }
            });

            break;
        case "closeleft":
            var tabIndex = $('#tabs').tabs('getTabIndex', currentTab);
            if (tabIndex == 1) {
                alert('亲，前边那个上头有人，咱惹不起哦。 ^@^!!');
                return false;
            }
            $.each(allTabtitle, function (i, n) {
                if (i < tabIndex) {
                    if (n != onlyOpenTitle) {
                        $('#tabs').tabs('close', n);
                    }
                }
            });

            break;
        case "exit":
            $('#closeMenu').menu('hide');
            break;
    }
}


function refresh() {
    self.reload();
}