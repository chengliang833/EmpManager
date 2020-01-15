/*
* JS 全局参数定义
* scripts/const.js
*/
var SUCCESS = 0;
var ERROR = 1;

var ADMIN = 0;
var USER = 1;

var $id = $("#id");
var $name = $("#name");

var pageNow;  //当前页面
var pageNum;  //总页面数

var pageSize;  //页面大小(每页条数)
var itemNum;  //总条数

var allDept; //所有的部门名称 list

//错误提示
function warning(warn){
	var text = $("#warning").text();
	if(text != undefined && text != ""){
		var text1 = text.indexOf("：");
		var text2 = text.indexOf(" ",text1+1);
		text = text.substring(text1+1, text2);
		console.log(text1);
		console.log(text2);
		console.log(text);
		console.log(text.indexOf(warn));
		if(text.indexOf(warn) >= 0)
			warn = "." + text;
	}
	$("#warning").html("---- 错误：" + warn + " ----");
}
//成功提示
function congratulate(abc){
	var text = $("#warning").text();
	if(text != undefined && text != ""){
		var text1 = text.indexOf(" ");
		var text2 = text.indexOf(" ",text1+1);
		text = text.substring(text1+1, text2);
		console.log(text1);
		console.log(text2);
		console.log(text);
		console.log(text.indexOf(abc));
		if(text.indexOf(abc) >= 0)
			abc = "." + text;
	}
	$("#warning").html("---- " + abc + " ----");
}

//重置主显示栏
function resetshow(){
	$("#showview").html('<div id="tableDiv"><table id="tableview"></table></div>');
	$("#warning").html("");
}


//添加提交与重置按钮及警告栏
function showButton(){
	var divButton = "<div class='divButton'>" +
					"    <input type='button' value='提&emsp;交' id='submit'>" +
					"    &emsp;<span id='page'></span>&emsp;" +
					"    <input type='button' value='重&emsp;置' id='reset'>" +
					"</div>";
	$("#showview").append(divButton);
}

//重置按钮
function resetEmp(){
	$("input[type!='button']").val("");
}



//date对象转字符串格式
Date.prototype.format = function (fmt) {
	var o = {
	    "M+": this.getMonth() + 1, //月份 
	    "d+": this.getDate(), //日 
	    "h+": this.getHours(), //小时 
	    "m+": this.getMinutes(), //分 s
	    "s+": this.getSeconds(), //秒 
	    "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	    "S": this.getMilliseconds() //毫秒 
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}




