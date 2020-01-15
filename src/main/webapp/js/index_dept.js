
$(function(){
	$("#dept_select").click(dept_select);
	$("#dept_update").click(dept_update);
	$("#dept_insert").click(dept_insert);
	$("#dept_delete").click(dept_delete);
	$("#browse_dept").click(browse_dept);
});

//--------------查询--------------------
function dept_select(){
	var deptId = prompt("请输入要查询的部门编号");
	if(!deptId){
		return;
	}
	deptId = deptId.toString();
	if(!deptId.match("^\\d+$")){
		warning("部门编号有误");
		return;
	}
	var url = "dept/select.do";
	var data = {"deptId":deptId};
	$.post(url, data, function(result){
//		console.log(result);
		if(result.state == SUCCESS){
			listDeptHead();
			listDept(result);
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}

//显示部门表头
function listDeptHead(){
	resetshow();
	var ths = "<thead>" +
			  "    <tr>" +
			  "        <th>编号</th>" +
			  "        <th>名称</th>" +
			  "        <th>人数</th>" +
			  "    </tr>" +
			  "</thead>" +
			  "<tbody id='tbodyview'><tbody>";
	$("#tableview").append(ths);
}

//显示部门表体
function listDept(result){
	var list = result.data;
//	console.log(list);
	for(var i=0; i<list.length; i++){
		var tds = "<tr class='tableTr'>" +
				  "    <td>" + list[i].dtmt_id + "</td>" +
				  "    <td>" + list[i].dtmt_name + "</td>" +
				  "    <td>" + list[i].emp_num + "</td>" +
				  "</tr>";
		$("#tbodyview").append(tds);
	}
}

//--------------修改--------------
function dept_update(){
	var deptId = prompt("请输入要修改的部门编号");
	if(!deptId){
		return;
	}
	deptId = deptId.toString();
	if(!deptId.match("^\\d+$")){
		warning("部门编号有误");
		return;
	}
	var url = "dept/select.do";
	var data = {"deptId":deptId};
//	console.log(data);
	$.post(url, data, function(result){
		if(result.state == SUCCESS){
			listDeptHead();
//			listDept(result);
			updateDeptTable(result);
			showButton();
			$("#submit").click(updateDept);
			$("#reset").click(resetEmp);
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}

//修改部门表体
function updateDeptTable(result){
	var list = result.data;
//	console.log(list);
	for(var i=0; i<list.length; i++){
		var tds = "<tr>" +
				  "    <td id='dtmt_id'>" + list[i].dtmt_id + "</td>" +
				  "    <td><input placeholder='" + list[i].dtmt_name + "' id='dtmt_name'/></td>" +
				  "    <td id='emp_num'>" + list[i].emp_num + "</td>" +
				  "</tr>";
		$("#tbodyview").append(tds);
	}
//	<input type="text" placeholder="请输入账户名" id="username"/>
}

//提交部门按钮
function updateDept(){
	var data = getDeptData();
	if(!data)
		return;
//	console.log(data);
	var url = "dept/update.do";
	$.post(url, data, function(result){
//		console.log(result);
		if(result.state == SUCCESS){
			listDeptHead();
			listDept(result);
			congratulate("修改成功");
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}

//提交前获得数据
function getDeptData(){
	var abc;
	var data;

	data = {"dtmt_id": $("#dtmt_id").text(),
			"dtmt_name": (abc=$("#dtmt_name").val()) ? abc : $("#dtmt_name").attr("placeholder"),
			"emp_num": $("#emp_num").text()
			};
	return data;
}

//--------------添加--------------
function dept_insert(){
	listDeptHead();	//表头
	insertDeptTable(); //表体
	$("#dtmt_id").html("编号自动生成");
	$("#emp_num").html("0");
	showButton(); //显示按钮
	$("#submit").click(insertDept);  //按钮提交
	$("#reset").click(resetEmp);  //按钮重置
}

//添加表体
function insertDeptTable(){
	var tds = "<tr>" +
			  "    <td id='dtmt_id'></td>" +
			  "    <td><input placeholder='' id='dtmt_name'/></td>" +
			  "    <td id='emp_num'></td>" +
			  "</tr>";
	$("#tbodyview").append(tds);
}

//添加提交
function insertDept(){
	var data = {"dtmt_name": (abc=$("#dtmt_name").val()) ? abc : $("#dtmt_name").attr("placeholder"),
				"emp_num": $("#emp_num").text()
				};
	console.log(data);
	if(!data){
		$("#dtmt_id").html("编号自动生成");
		return;
	}
	var url = "dept/insert.do";
	$.post(url, data, function(result){
		if(result.state == SUCCESS){
			listDeptHead();
			listDept(result);
			congratulate("添加成功");
		}else if(result.state == ERROR){
			$("#tdId").html("编号自动生成");
			warning(result.message);
		}else
			window.location = "login.html";
	});
}

//--------------删除--------------
function dept_delete(){
	var deptId = prompt("请输入要删除的部门编号");
	if(!deptId){
		return;
	}
	deptId = deptId.toString();
	if(!deptId.match("^\\d+$")){
		warning("部门编号有误");
		return;
	}
	var url = "dept/delete.do";
	var data = {"deptId":deptId};
	$.post(url, data, function(result){
		console.log(result);
		if(result.state == SUCCESS){
			if(result.data == 0){
				warning("没有该部门");
			}else{
				congratulate("删除成功");
			}
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}

//--------------概览--------------
function browse_dept(){
	//查总条数
	var browseEmpNum;
	var url = "dept/browseDeptNum.do";
	$.post(url, null, function(result){
		if(result.state == SUCCESS){
//			console.log(result);
			browseEmpNum = result.data;
			itemNum = browseEmpNum;
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
	
	//分页显示
	var url = "dept/browse.do";
	var data = {"page":1, "pageSize":6};
	pageNow = data.page;
	pageSize = data.pageSize;
	$.post(url, data, function(result){
		if(result.state == SUCCESS){
			listDeptHead();
			listDept(result);
			showButton();
			$("#submit").val("上一页");  //按钮上一页
			$("#reset").val("下一页");  //按钮下一页
			pageNum = Math.ceil(itemNum/pageSize);
			$("#page").text(pageNow + "/" + pageNum);
			$("#tableDiv").css("height",parseInt($("tr").css("height").substring(0,2)) * (pageSize + 1) + "px");
			$("#submit").click(function(){
				lastPage(url,listDept);
			});  //查上一页
			$("#reset").click(function(){
				nextPage(url,listDept);
			});  //查下一页
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}






