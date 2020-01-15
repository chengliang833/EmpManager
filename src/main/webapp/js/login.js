//加载运行
$(function(){
	$('#test11').click(function(){
		console.log("test");
//		$.post("http://jsy.edawtech.com/jsy/wx/getBuckle", {"accessNumber":"8986031646202413402"}, function(result){
//			console.log(result);
//		});
		$.ajax({
		    headers: {
		        Accept: "application/json; charset=utf-8"
		    },
		    url:"http://jsy.edawtech.com/jsy/wx/getBuckle",
		    type: "post",
		    data:{"accessNumber":"8986031646202413402"},
		    beforeSend: function(request) {
		    	request.setRequestHeader("Access-Control-Allow-Origin", "*");
		    },
		    success: function (data) {
		    	console.log(data);
		    }
		});
	});

	$("#codeImg").attr('src', "login/code.do?"+Math.random());
	
	$(window).keydown(function(event){
		if(event.keyCode ==13){
			login();
		}
	});
	$("#login").click(login);
	
	//验证码单击刷新
	$("#codeImg").click(function(){
		var url = "login/code.do?"+Math.random();
		$(this).attr('src', url);
	});
	
	//验证码输完验证
	$('#code').blur(codeVerify);
	$('#username').blur(usernameVerify);
	$('#password').blur(passwordVerify);

});

//用户名验证
function usernameVerify(){
	var username = $("#username").val();
	if(username.length > 10 || username.length < 3){
		warning("用户名长度必须在3~10位之间");
		return false;
	}
	if(username.charCodeAt(0) >= 48 && username.charCodeAt(0) <= 57){
		warning("用户名开头不能为数字");
		return false;
	}
	for(var i=0; i<username.length; i++){
		if( !( ( 48 <= username.charCodeAt(i) && username.charCodeAt(i) <= 57 )   //数字
		    || ( 65 <= username.charCodeAt(i) && username.charCodeAt(i) <= 90 )   //大写字母
		    || ( 97 <= username.charCodeAt(i) && username.charCodeAt(i) <= 122 )   //小写字母
		    || username.charCodeAt(i) == 95   )  //下划线
		  ){
			warning("用户名只能包含_字母_数字_下划线");
			return false;
		}else
			resetshowprompt();
	}
	return true;
}

//密码格式验证
function passwordVerify(){
	var password = $("#password").val();
	if(password.length > 10 || password.length < 3){
		warning("密码长度必须在3~10位之间");
		return false;
	}
	resetshowprompt();
	return true;
}

//验证码验证
function codeVerify(){
	var code=$(this).val();
	if(code == undefined || code == ""){
		warning("请输入验证码");
		return false;
	}
	var url="login/checkCode.do?code="+code;
	$.getJSON(url, function(result){
		if(result.state==SUCCESS){
			resetshowprompt();
		}else{
			warning(result.message);
		}
	});
}

//登录按钮
function login(){
	var username = $("#username").val();
	var password = $("#password").val();
	if(!usernameVerify() || !passwordVerify())
		return;
	var code = $("#code").val();
	if(code == undefined || code == ""){
		warning("请输入验证码");
	}
	var url = "login/login.do";
	var data = {'username':username, 'password':password, "code":code};
//	console.log(data);
	$.post(url, data, function(result){
//		console.log(result);
		if(result.state == SUCCESS){
			if(result.type == ADMIN){
				var admin = result.data;
				setCookie("username",admin.username);
				window.location = "index.html";
			}
			if(result.type == USER){
				var user = result.data;
				setCookie("userId",user.id);
				setCookie("username",user.name);
				window.location = "user.html";
			}
		}else{
			warning(result.message);
		}
	});
}

//警告
function warning(warn){
	$("#error_box").html("---- " + warn + " ----");
}
//重置警告
function resetshowprompt(){
	$("#error_box").html("");
}








