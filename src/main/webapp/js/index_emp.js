//主页管理模块

//页面加载绑定单击事件
$(function(){
	ensure();
	
	$(".div2").click(function(){ 
		$(this).next("div").slideToggle("slow").siblings(".div3:visible").slideUp("slow");
	});
	
	$("#emp_select").click(emp_select);
	$("#emp_update").click(emp_update);
	$("#emp_insert").click(emp_insert);
	$("#emp_delete").click(emp_delete);
	$("#browse_emp").click(browse_emp);
	$("#exit").click(exit);
});

//用一个请求保证登录检查
function ensure(){
	$.post("index.do", null, function(result){
		if(result.state == SUCCESS){
		}else if(result.state == ERROR){
		}else
			window.location = "login.html";
	});
}

//重置表体
function resetTbody(){
	$("#tbodyview").html('');
}

//--------------概览--------------
function browse_emp(){
	//查总条数
	var browseEmpNum;
	var url = "emp/browseEmpNum.do";
	$.post(url, null, function(result){
		if(result.state == SUCCESS){
			browseEmpNum = result.data;
			itemNum = browseEmpNum;
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
	
	//分页显示
	var url = "emp/browse.do";
	var data = {"page":1, "pageSize":6};
	pageNow = data.page;
	pageSize = data.pageSize;
	$.post(url, data, function(result){
		if(result.state == SUCCESS){
			listHead();
			listEmp(result);
			showButton();
			$("#submit").val("上一页");  //按钮上一页
			$("#reset").val("下一页");  //按钮下一页
			pageNum = Math.ceil(itemNum/pageSize);
			$("#page").text(pageNow + "/" + pageNum);
			$("#tableDiv").css("height",parseInt($("tr").css("height").substring(0,2)) * (pageSize + 1) + "px");
			$("#submit").click(function(){
				lastPage(url,listEmp);
			});  //查上一页
			$("#reset").click(function(){
				nextPage(url,listEmp);
			});  //查下一页
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}

//翻页
function turnPage(pageTurnNow ,url,listTableBody){
	resetTbody();
	
//	var url = "emp/browse.do";
	var data = {"page":pageTurnNow, "pageSize":pageSize};
	pageNow = data.page;
	$.post(url, data, function(result){
		if(result.state == SUCCESS){
			listTableBody(result);
			$("#tableDiv").css("height",parseInt($("tr").css("height").substring(0,2)) * (pageSize + 1) + "px");
			$("#page").text(pageNow + "/" + pageNum);
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}

//上一页
function lastPage(url,listTableBody){
	if(pageNow == 1)
		return;
	turnPage(pageNow - 1, url,listTableBody);
}

//下一页
function nextPage(url,listTableBody){
	if(pageNow == pageNum)
		return;
	turnPage(pageNow + 1, url,listTableBody);
}

//--------------退出--------------
function exit(){
	$.post("login/exit.do", null, function(result){
		window.location = "login.html";
	});
}

//--------------添加--------------
function emp_insert(){
	var url = "dept/selectAllDept.do";
	$.post(url, null, function(result){
		if(result.state == SUCCESS){
			allDept = result.data;
			
			listHead();	//表头
			insertEmpTable(); //表体
			$("#tdId").html("编号自动生成");
			$("#wage").html("薪资另行修改");
			showButton(); //显示按钮
			$("#submit").click(insertEmp);  //按钮提交
			$("#reset").click(resetEmp);  //按钮重置
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}

function insertEmpTable(){
	var tds = "<tr>" +
			  "    <td id='tdId'></td>" +
			  "    <td><input placeholder='' id='name'/></td>" +
//			  "    <td><input placeholder='' id='sex'/></td>" +
			  "    <td><input type='radio' name='sex' value='男' checked='checked'/>男 "+
			  "        <input type='radio' name='sex' id='sex' value='女'/>女</td> "+
			  "    <td><input placeholder='' id='age'/></td>" +
//			  "    <td><input placeholder='' id='post'/></td>" +
			  "    <td><select class='positionsel' id='post'> "+
			  "        <option value='总经理'>总经理</option> "+
			  "        <option value='经理'>经理</option> "+
			  "        <option value='员工' selected='selected'>员工</option> "+
			  "    </select></td> "+
			  "    <td><select class='positionsel' id='department'>";
		for(var j=0,length=allDept.length; j<length; j++){
				tds +="        <option value='"+allDept[j]+"'>"+allDept[j]+"</option> ";
		}
//		  "    <td><input placeholder='' id='department'/></td>" +
		tds +="    </select></td>"+
			  "    <td id='wage'></td>" +
			  "</tr>";
	$("#tbodyview").append(tds);
}

//添加提交
function insertEmp(){
	if( !($("#name").val() && $("#sex").val() && $("#age").val() && $("#post").val() && $("#department").val()) ){
		warning("请填写完整");
		return;
	}
	$("#tdId").html("<input id='id' placeholder='0'>");
	var data = getData();
	console.log(data);
	if(!data){
		$("#tdId").html("编号自动生成");
		return;
	}
	var url = "emp/insert.do";
	$.post(url, data, function(result){
		if(result.state == SUCCESS){
			listHead();
			listEmp(result);
			congratulate("添加成功");
		}else if(result.state == ERROR){
			$("#tdId").html("编号自动生成");
			warning(result.message);
		}else
			window.location = "login.html";
	});
}


//--------------修改--------------
function emp_update(){
	var empId = prompt("请输入要修改的员工编号");
	if(!empId){
		return;
	}
	empId = empId.toString();
	if(!empId.match("^\\d+$")){
		warning("员工编号有误");
		return;
	}
	
	var url = "dept/selectAllDept.do";
	$.post(url, null, function(result){
		if(result.state == SUCCESS){
			allDept = result.data;
			
			url = "emp/select.do";
			data = {"empId":empId};
//			console.log(data);
			$.post(url, data, function(result){
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
				  "    <td id='tdId'><input placeholder='" + list[i].id + "' id='id'/></td>" +
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
				  //"    <td><input placeholder='" + list[i].post + "' id='post'/></td>" +
  				  "    <td><select class='positionsel' id='post'> ";
		switch(list[i].post){
			case '总经理':
				tds+="         <option value='总经理' selected='selected'>总经理</option> "+
					 "         <option value='经理'>经理</option> "+
					 "         <option value='员工'>员工</option> ";
				break;
			case '经理':
				tds+="         <option value='总经理'>总经理</option> "+
					 "         <option value='经理' selected='selected'>经理</option> "+
					 "         <option value='员工'>员工</option> ";
				break;
			default:
				tds+="         <option value='总经理'>总经理</option> "+
					 "         <option value='经理'>经理</option> "+
					 "         <option value='员工' selected='selected'>员工</option> ";
		}
		tds +=    "    </select></td> "+
				  "    <td><select class='positionsel' id='department'>";
		for(var j=0,length=allDept.length; j<length; j++){
			if(allDept[j] == list[i].department)
				tds +="        <option selected='selected' value='"+allDept[j]+"'>"+allDept[j]+"</option> ";
			else
				tds +="        <option value='"+allDept[j]+"'>"+allDept[j]+"</option> ";
		}
//		  "    <td><input placeholder='" + list[i].department + "' id='department'/></td>" +
		tds +=    "    </select></td>"+
				  "    <td id='wage'>" + list[i].wage + "</td>" +
				  "</tr>";
		$("#tbodyview").append(tds);
	}
}

//提交前获得数据
function getData(){
	var abc;
	var data;
	var id = (abc=$("#id").val()) ? abc : $("#id").attr("placeholder");
	if(!id.match("^\\d+$")){
		warning("员工编号为数字");
		return data;
	}
	var age = (abc=$("#age").val()) ? abc : $("#age").attr("placeholder");
	if(!age.match("^\\d+$") || age > 200 || age < 1){
		warning("年龄为1~200之间的整数");
		return data;
	}
	data = {"oldId": $("#id").attr("placeholder"),
				"id": id,
				"name": (abc=$("#name").val()) ? abc : $("#name").attr("placeholder"),
				"sex": $("input[name='sex']:checked").val(),
				"age": age,
				"post": $("#post").val(),
				"department": (abc=$("#department").val()) ? abc : $("#department").attr("placeholder"),
				"wage": $("#wage").text()
				};
	return data;
}

//提交按钮
function updateEmp(){
	var data = getData();
	if(!data)
		return;
//	console.log(data);
	var url = "emp/update.do";
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


//--------------查询--------------
function emp_select(){
	var empId = prompt("请输入要查询的员工编号");
	if(!empId){
		return;
	}
	empId = empId.toString();
	if(!empId.match("^\\d+$")){
		warning("员工编号有误");
		return;
	}
	var url = "emp/select.do";
	var data = {"empId":empId};
	$.post(url, data, function(result){
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

//--------------删除--------------
function emp_delete(){
	var empId = prompt("请输入要删除的员工编号");
	if(!empId){
		return;
	}
	empId = empId.toString();
	if(!empId.match("^\\d+$")){
		warning("员工编号有误");
		return;
	}
	var url = "emp/delete.do";
	var data = {"empId":empId};
	$.post(url, data, function(result){
		console.log(result);
		if(result.state == SUCCESS){
			if(result.data == 0){
				warning("没有该员工");
			}else{
				congratulate("删除成功");
			}
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}















