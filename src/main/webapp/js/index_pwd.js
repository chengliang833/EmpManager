
$(function(){
	$("#pwd_emp").click(pwd_emp);
	$("#pwd_admin").click(pwd_admin);
});

//--------------员工密码--------------
function pwd_emp(){
	updatePwdHead();
	updatePwdTable();
	showButton();
	$("#submit").click(updatePwdEmp);
	$("#reset").click(resetEmp);
}

function updatePwdEmp(){
	var url = "emp/pwdemp.do";
	var data = getPwdData();
//	console.log(data);
	if(!data)
		return;
	$.post(url, data, function(result){
		console.log(result);
		if(result.state == SUCCESS){
			if(result.data == 0){
				warning("没有该员工");
			}else{
				congratulate("修改成功");
			}
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}

//修改密码表头
function updatePwdHead(){
	resetshow();
	var ths = "<thead>" +
			  "    <tr>" +
			  "        <th>管理密码</th>" +
			  "        <th>编号</th>" +
			  "        <th>新密码</th>" +
			  "    </tr>" +
			  "</thead>" +
			  "<tbody id='tbodyview'><tbody>";
	$("#tableview").append(ths);
}

//修改密码表体
function updatePwdTable(){
	var tds = "<tr>" +
			  "    <td><input placeholder='请输入管理密码' type='password' id='adminPwd'/></td>" +
			  "    <td><input placeholder='请输入员工编号' id='id'/></td>" +
			  "    <td><input placeholder='请输入新密码' id='password'/></td>" +
			  "</tr>";
	$("#tbodyview").append(tds);
}

//提交前获得数据
function getPwdData(){
	var abc;
	var data;
	var id = (abc=$("#id").val()) ? abc : $("#id").attr("placeholder");
	if(!id.match("^\\d+$")){
		warning("员工编号为数字");
		return data;
	}
	var password = $("#adminPwd").val();
	if(password.length > 10 || password.length < 3){
		warning("管理密码长度必须在3~10位之间");
		return data;
	}
	password = $("#password").val();
	if(password.length > 10 || password.length < 3){
		warning("新密码长度必须在3~10位之间");
		return data;
	}
	data = {"adminPwd": (abc=$("#adminPwd").val()) ? abc : $("#adminPwd").attr("adminPwd"),
			"id": id,
			"password": (abc=$("#password").val()) ? abc : $("#password").attr("placeholder")
			};
	return data;
}

//--------------管理密码--------------
function pwd_admin(){
	updateAdminPwdHead();
	updateAdminPwdTable();
	showButton();
	$("#submit").click(updatePwdAdmin);
	$("#reset").click(resetEmp);
}

//修改管理密码表头
function updateAdminPwdHead(){
	resetshow();
	var ths = "<thead>" +
			  "    <tr>" +
			  "        <th>管理密码</th>" +
			  "        <th>新密码</th>" +
			  "    </tr>" +
			  "</thead>" +
			  "<tbody id='tbodyview'><tbody>";
	$("#tableview").append(ths);
}

//修改管理密码表体
function updateAdminPwdTable(){
	var tds = "<tr>" +
			  "    <td><input placeholder='请输入管理密码' type='password' id='adminPwd'/></td>" +
			  "    <td><input placeholder='请输入新密码' id='password'/></td>" +
			  "</tr>";
	$("#tbodyview").append(tds);
}

function updatePwdAdmin(){
	var url = "emp/pwdadmin.do";
	var data = getAdminPwdData();
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
function getAdminPwdData(){
	var abc;
	var data;
	var password = $("#adminPwd").val();
	if(password.length > 10 || password.length < 3){
		warning("管理密码长度必须在3~10位之间");
		return data;
	}
	password = $("#password").val();
	if(password.length > 10 || password.length < 3){
		warning("管理密码长度必须在3~10位之间");
		return data;
	}
	data = {"adminPwd": (abc=$("#adminPwd").val()) ? abc : $("#adminPwd").attr("adminPwd"),
			"password": (abc=$("#password").val()) ? abc : $("#password").attr("placeholder")
			};
	return data;
}




