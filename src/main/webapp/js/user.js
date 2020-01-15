
$(function(){
	ensure();

	$("#user_select").click(user_select);
	$("#user_update").click(user_update);
	$("#userwage_select").click(userwage_select);
	$("#user_pwd").click(user_pwd);
	$("#exit").click(exit);
	
	$(".div2").click(function(){ 
		$(this).next("div:.div3").slideToggle("slow").siblings(".div3:visible").slideUp("slow");
	});
});

//用一个请求保证登录检查
function ensure(){
	$.post("user.do", null, function(result){
		if(result.state == SUCCESS){
		}else if(result.state == ERROR){
		}else
			window.location = "login.html";
	});
}

//--------------退出--------------
function exit(){
	$.post("login/exit.do", null, function(result){
		window.location = "login.html";
	});
}

//--------------查询--------------
function user_select(){
	var url = "user/select.do";
	$.post(url, null, function(result){
//		console.log(result);
		if(result.state == SUCCESS){
			listHead();
			listEmp(result);
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}

//显示表头
function listHead(){
	resetshow();
	var ths = "<thead>" +
			  "    <tr>" +
			  "        <th>编号</th>" +
			  "        <th>姓名</th>" +
			  "        <th>性别</th>" +
			  "        <th>年龄</th>" +
			  "        <th>职位</th>" +
			  "        <th>部门</th>" +
			  "        <th>薪资</th>" +
			  "    </tr>" +
			  "</thead>" +
			  "<tbody id='tbodyview'><tbody>";
	$("#tableview").append(ths);
}

//显示表体
function listEmp(result){
	var list = result.data;
//	console.log(list);
	for(var i=0; i<list.length; i++){
		var tds = "<tr class='tableTr'>" +
				  "    <td>" + list[i].id + "</td>" +
				  "    <td>" + list[i].name + "</td>" +
				  "    <td>" + list[i].sex + "</td>" +
				  "    <td>" + list[i].age + "</td>" +
				  "    <td>" + list[i].post + "</td>" +
				  "    <td>" + list[i].department + "</td>" +
				  "    <td>" + list[i].wage + "</td>" +
				  "</tr>";
		$("#tbodyview").append(tds);
	}
}

//--------------修改--------------
function user_update(){
	var url = "user/select.do";
	$.post(url, null, function(result){
		if(result.state == SUCCESS){
			listHead();
			updateEmpTable(result);
			showButton();
			$("#submit").click(updateEmp);
			$("#reset").click(resetEmp);
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}

//修改表体
function updateEmpTable(result){
	var list = result.data;
//	console.log(list);
	for(var i=0; i<list.length; i++){
		var tds = "<tr>" +
				  "    <td id='id'>" + list[i].id + "</td>" +
				  "    <td><input placeholder='" + list[i].name + "' id='name'/></td>";
//				  "    <td><input placeholder='" + list[i].sex + "' id='sex'/></td>" +
		switch(list[i].sex){
			case '女':
				tds+=" <td><input type='radio' name='sex' value='男'/>男 "+
					 "     <input type='radio' name='sex' id='sex' value='女' checked='checked'/>女</td> ";
				break;
			default:
				tds+=" <td><input type='radio' name='sex' value='男' checked='checked'/>男 "+
					 "     <input type='radio' name='sex' id='sex' value='女'/>女</td> ";
		}
		tds +=    "    <td><input placeholder='" + list[i].age + "' id='age'/></td>" +
				  "    <td id='post'>" + list[i].post + "</td>" +
				  "    <td id='department'>" + list[i].department + "</td>" +
				  "    <td id='wage'>" + list[i].wage + "</td>" +
				  "</tr>";
		$("#tbodyview").append(tds);
	}
}

//提交按钮
function updateEmp(){
	var data = getData();
	if(!data)
		return;
//	console.log(data);
	var url = "user/update.do";
	$.post(url, data, function(result){
//		console.log(result);
		if(result.state == SUCCESS){
			listHead();
			listEmp(result);
			congratulate("修改成功");
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}

//提交前获得数据
function getData(){
	var abc;
	var data;
	var age = (abc=$("#age").val()) ? abc : $("#age").attr("placeholder");
	if(!age.match("^\\d+$")){
		warning("年龄为数字");
		return data;
	}
	data = {"name": (abc=$("#name").val()) ? abc : $("#name").attr("placeholder"),
			"sex": $("input[name='sex']:checked").val(),
			"age": age,
			"post": $("#post").text(),
			"department": $("#department").text(),
			"wage": $("#wage").text()
			};
	return data;
}

//--------------查询--------------------
function userwage_select(){
	var url = "user/selectWage.do";
	$.post(url, null, function(result){
//		console.log(result);
		if(result.state == SUCCESS){
			listwageHead();
			listWage(result);
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}

//显示薪资表头
function listwageHead(){
	resetshow();
	var ths = "<thead>" +
			  "    <tr>" +
			  "        <th>编号</th>" +
			  "        <th>姓名</th>" +
			  "        <th>实际薪资</th>" +
			  "        <th>薪资等级</th>" +
			  "        <th>基础薪资</th>" +
			  "        <th>奖金</th>" +
			  "        <th>罚金</th>" +
			  "        <th>修改日期</th>" +
			  "    </tr>" +
			  "</thead>" +
			  "<tbody id='tbodyview'><tbody>";
	$("#tableview").append(ths);
}

//显示薪资表体
function listWage(result){
	var list = result.data;
//	console.log(list);
	for(var i=0; i<list.length; i++){
		var tds = "<tr class='tableTr'>" +
				  "    <td>" + list[i].id + "</td>" +
				  "    <td>" + list[i].name + "</td>" +
				  "    <td>" + list[i].wage + "</td>" +
				  "    <td>" + list[i].wagetype + "</td>" +
				  "    <td>" + list[i].basewage + "</td>" +
				  "    <td>" + list[i].bonus + "</td>" +
				  "    <td>" + list[i].deductwage + "</td>" +
				  "    <td>" + new Date(list[i].grantdate).format("yyyy-MM-dd") + "</td>" +
				  "</tr>";
		$("#tbodyview").append(tds);
	}
}

//--------------修改密码--------------
function user_pwd(){
	updateUserPwdHead();
	updateUserPwdTable();
	showButton();
	$("#submit").click(updatePwdUser);
	$("#reset").click(resetEmp);
}

//修改管理密码表头
function updateUserPwdHead(){
	resetshow();
	var ths = "<thead>" +
			  "    <tr>" +
			  "        <th>原密码</th>" +
			  "        <th>新密码</th>" +
			  "    </tr>" +
			  "</thead>" +
			  "<tbody id='tbodyview'><tbody>";
	$("#tableview").append(ths);
}

//修改管理密码表体
function updateUserPwdTable(){
	var tds = "<tr>" +
			  "    <td><input placeholder='请输入原密码' type='password' id='pwd'/></td>" +
			  "    <td><input placeholder='请输入新密码' id='password'/></td>" +
			  "</tr>";
	$("#tbodyview").append(tds);
}

function updatePwdUser(){
	var url = "user/pwd.do";
	var data = getUserPwdData();
//	console.log(data);
	if(!data)
		return;
	$.post(url, data, function(result){
		console.log(result);
		if(result.state == SUCCESS){
			congratulate("修改成功");
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}

//提交前获得数据
function getUserPwdData(){
	var abc;
	var data;
	var password = $("#pwd").val();
	if(password.length > 10 || password.length < 3){
		warning("旧密码长度必须在3~10位之间");
		return data;
	}
	password = $("#password").val();
	if(password.length > 10 || password.length < 3){
		warning("新密码长度必须在3~10位之间");
		return data;
	}
	data = {"pwd": (abc=$("#pwd").val()) ? abc : $("#pwd").attr("placeholder"),
			"password": (abc=$("#password").val()) ? abc : $("#password").attr("placeholder")
			};
	return data;
}