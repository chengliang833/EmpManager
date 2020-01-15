
$(function(){
	$("#wage_select").click(wage_select);
	$("#wage_update").click(wage_update);
	$("#browse_wage").click(browse_wage);
});

//--------------查询--------------------
function wage_select(){
	var empId = prompt("请输入要查询的员工编号");
	if(!empId){
		return;
	}
	empId = empId.toString();
	if(!empId.match("^\\d+$")){
		warning("员工编号有误");
		return;
	}
	var url = "wage/select.do";
	var data = {"empId":empId};
	$.post(url, data, function(result){
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
				  "    <td>" + (list[i].grantdate?new Date(list[i].grantdate):new Date("1970-01-01")).format("yyyy-MM-dd") + "</td>" +
				  "</tr>";
		$("#tbodyview").append(tds);
	}
}

//--------------修改--------------
function wage_update(){
	var empId = prompt("请输入要修改的员工编号");
	if(!empId){
		return;
	}
	empId = empId.toString();
	if(!empId.match("^\\d+$")){
		warning("员工编号有误");
		return;
	}
	var url = "wage/select.do";
	var data = {"empId":empId};
//	console.log(data);
	$.post(url, data, function(result){
		if(result.state == SUCCESS){
			listwageHead();
			updateWageTable(result);
			showButton();
			$("#submit").click(updateWage);
			$("#reset").click(resetEmp);
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}

//修改薪资表体
function updateWageTable(result){
	var list = result.data;
//	console.log(list);
	for(var i=0; i<list.length; i++){
		var tds = "<tr>" +
				  "    <td id='id'>" + list[i].id + "</td>" +
				  "    <td id='name'>" + list[i].name + "</td>" +
				  "    <td id='wage'>" + list[i].wage + "</td>" +
				  "    <td id='wagetype'>" + list[i].wagetype + "</td>" +
				  "    <td><input placeholder='" + list[i].basewage + "' id='basewage'/></td>" +
				  "    <td><input placeholder='" + list[i].bonus + "' id='bonus'/></td>" +
				  "    <td><input placeholder='" + list[i].deductwage + "' id='deductwage'/></td>" +
				  "    <td>" + (list[i].grantdate?new Date(list[i].grantdate):new Date("1970-01-01")).format("yyyy-MM-dd") + "</td>" +
				  "</tr>";
		$("#tbodyview").append(tds);
	}
//	<input type="text" placeholder="请输入账户名" id="username"/>
}

//提交薪资按钮
function updateWage(){
	var data = getWageData();
	if(!data)
		return;
//	console.log(data);
	var url = "wage/update.do";
	$.post(url, data, function(result){
//		console.log(result);
		if(result.state == SUCCESS){
			listwageHead();
			listWage(result);
			congratulate("修改成功");
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}

//提交前获得数据
function getWageData(){
	var abc;
	var data;
	
	var basewage = (abc=$("#basewage").val()) ? abc : $("#basewage").attr("placeholder");
	if(!basewage.match("^\\d+$")){
		warning("基本工资为数字");
		return data;
	}

	var bonus = (abc=$("#bonus").val()) ? abc : $("#bonus").attr("placeholder");
	if(!bonus.match("^\\d+$")){
		warning("奖金为数字");
		return data;
	}

	var deductwage = (abc=$("#deductwage").val()) ? abc : $("#deductwage").attr("placeholder");
	if(!deductwage.match("^\\d+$")){
		warning("罚金为数字");
		return data;
	}
	
	if((abc = parseInt(basewage) + parseInt(bonus)) < parseInt(deductwage)){
		deductwage = abc;
	}

	data = {"id": $("#id").text(),
			"name": $("#name").text(),
			"wage": abc - deductwage,
			"wagetype": parseInt((abc - deductwage)/1000),
			"basewage": basewage,
			"bonus": bonus,
			"deductwage": deductwage
			};
	return data;
}

//--------------概览--------------
function browse_wage(){
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
	var url = "wage/browse.do";
	var data = {"page":1, "pageSize":6};
	pageNow = data.page;
	pageSize = data.pageSize;
	$.post(url, data, function(result){
		if(result.state == SUCCESS){
			listwageHead();
			listWage(result);
			showButton();
			$("#submit").val("上一页");  //按钮上一页
			$("#reset").val("下一页");  //按钮下一页
			pageNum = Math.ceil(itemNum/pageSize);
			$("#page").text(pageNow + "/" + pageNum);
			$("#tableDiv").css("height",parseInt($("tr").css("height").substring(0,2)) * (pageSize + 1) + "px");
			$("#submit").click(function(){
				lastPage(url,listWage);
			});  //查上一页
			$("#reset").click(function(){
				nextPage(url,listWage);
			});  //查下一页
		}else if(result.state == ERROR){
			warning(result.message);
		}else
			window.location = "login.html";
	});
}





