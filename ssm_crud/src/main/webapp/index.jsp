<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	pageContext.setAttribute("BASE_PATH", request.getContextPath());
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
<!-- Bootstrap -->
<link
	href="${BASE_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
<script src="${BASE_PATH }/static/js/jquery.min.js"></script>
<!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
<script
	src="${BASE_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<script type="text/javascript">
	
	//定义一个全局的总记录数
	var totalRecord ;
	//定义一个全局变量记录当前页
	var currentNum;
	
	//加载页面
	$(function() {
		//加载页面查询信息
		to_page(1);
		//为员工新增button按钮绑定模态框事件
		$("#emp_add_modal_btn").click(function() {
			
			//0.清除表单数据
			reset_form("#empAddModal form");
			
			//1.弹出模态框之前要先发送ajax请求到数据库查出部分信息
			getDepts("#empAddModal select");
			//点击弹出模特框
			$("#empAddModal").modal({
				backdrop : "static"
			});
		})
		//为员工保存信息增加点击事件，让增加表单提交
		$("#emp_save").click(function(){
			
			//1.需要对表单中的数据进行校验
			if(!validate_add_form()){
				return false;
			};
			
			//1.2对保存按钮的值（通过校验用户名是否存在得到的值）进行判断，是否能点击提交
			//alert($(this).attr("ajax_va"))
			if($(this).attr("ajax_va") == "error"){
				return false;
			}
			
			//2.在模态框中填写的表单数据提交到服务器进行保存
			$.ajax({
				url : "${BASE_PATH}/employee/emp",
				type : "post",
				data : $("#empAddModal form").serialize(),
				success : function(result){
					
					//对增加的信息进行后端校验的状态码进行判断
					if(result.code == 100){		//成功
						//alert("result.message");
						//1.员工保存成功,要关闭模特框
						$('#empAddModal').modal('hide')
						//2.发送ajax请求，到最后一页
						to_page(totalRecord);
					}else{		//失败
						//console.log(result);
						if(undefined != result.map.errorFields.email){
							//显示邮箱错误信息
							show_validate_msg("#email","error",result.map.errorFields.email);
						}
						if(undefined != result.map.errorFields.eName){
							//显示员工信息
							show_validate_msg("#eName","error",result.map.errorFields.eName);
						}
					}	
					
				}
			})
		});
		
		//为员工姓名绑定一个change事件用于校验姓名是否重复
		$("#eName").change(function(){
			$.ajax({
				url : "${BASE_PATH}/employee/checkUser",
				data : "eName="+this.value,
				type : "post",
				success : function(result){
					if(result.code == 100){
						show_validate_msg(eName,"success","用户名xxx可用");
						//如果用户名可用,给保存按钮做个标记，让其能点击提交
						$("#emp_save").attr("ajax_va","success");
					}else{
						show_validate_msg(eName,"success",result.map.va_msg);
						//如果用户名不可用,给保存按钮做个标记，让其能不能点击提交
						$("#emp_save").attr("ajax_va","error");
					}
				}
			})
		})
	});
	
	//表单重置以及清除校验的样式
	function reset_form(ele){
		$(ele)[0].reset();
		//清空表单样式
		$(ele).find("*").removeClass("has-success has-error");
		$(ele).find(".help-block").text("");
	}
	
	//对表单数据进行校验
	function validate_add_form(){
		//1.拿到表单数据，用正则表达式进行校验
		var eName = $("#eName").val();
		var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
		if(!regName.test(eName)){
			//alert("用户名可以是2-5位中文，或英文在6-16位");
			//$("#eName").parent().addClass("has-error");
			//$("#eName").next("span").text("用户名可以是2-5位中文，或英文在6-16位");
			show_validate_msg("#eName","error","用户名可以是2-5位中文，或英文在6-16位");
			return false;
		}else{
			//$("#eName").parent().addClass("has-success");
			//$("#eName").next("span").text("用户名格式正确");
			show_validate_msg("#eName","success","用户名格式正确");
		}
		//2.校验邮箱
		var email = $("#email").val();
		var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
		if(!regEmail.test(email)){
			//alert("邮箱格式错误");
			//$("#email").parent().addClass("has-error");
			//$("#email").next("span").text("邮箱格式错误");
			show_validate_msg("#email","error","邮箱格式错误");
			return false;
		}else{
			//$("#email").parent().addClass("has-success");
			//$("#email").next("span").text("邮箱格式正确");
			show_validate_msg("#email","success","邮箱格式正确");
		}
		return true;
	}
	
	//抽取出校验信息显示方法
	function show_validate_msg(ele,status,msg){
		//清除当前元素的校验状态
		$(ele).parent().removeClass("has-success has-error");
		$(ele).next("span").text("");//清除文本
		if("success"==status){
			$(ele).parent().addClass("has-success");
			$(ele).next("span").text(msg);
		}else if("error"==status){
			$(ele).parent().addClass("has-error");
			$(ele).next("span").text(msg);
		}
	}
	
	//查出所有的员工信息，并显示在下列列表中
	function getDepts(ele){
		$(ele).empty();
		$.ajax({
			url : "${BASE_PATH}/department/depts",
			type : "get",
			success : function(result){
				//alert(result);
				$.each(result.map.depts,function(){
					var option = $("<option></option>").append(this.dName).attr("value",this.dId);
					option.appendTo($(ele));//添加到该模态框的下列列表中
				});
			}
		})
	}

	//根据页码发送ajax请求去指定的页码
	function to_page(pg) {
		$.ajax({
			url : "${BASE_PATH}/employee/emps",
			data : "pg=" + pg,
			type : "get",
			success : function(result) {
				//console.log(result);
				//1.对返回的json数据进行解析
				build_emps_table(result);
				//2.解析并且显示分页信息
				build_pag_info(result);
				//3.解析并显示分页条数据
				build_pag_nav(result);
			}
		})
	}

	//1.解析json字符串并增加到表格中
	function build_emps_table(result) {
		//因为发送的ajax请求。是局部刷新，因此要清空之前的数据
		$("#emp_tables tbody").empty();

		var emps = result.map.pageInfo.list;
		$.each(emps, function(index, item) {
			
			//加上多选框
			var checkBox = $("<td><input type='checkbox' class='check_item'/></td>");
			$("#checkAll").prop("checked",false);
			
			//alert(item.eName);
			var empIdTd = $("<td></td>").append(item.eId);
			var eName = $("<td></td>").append(item.eName);
			var gender = $("<td></td>").append(item.gender == 'm' ? "男" : "女");
			var email = $("<td></td>").append(item.email);
			var dName = $("<td></td>").append(item.department);
			//编辑，和删除按钮
			var editBtn = $("<button></button>").addClass(
					"btn btn-primary btn-sm edit-btn").append(
					$("<span></span>").addClass("glyphicon glyphicon-pencil"))
					.append("编辑");
			
			//在创建编辑按钮的时候，给编辑按钮绑定该id属性值
			editBtn.attr("edit-id",item.eId);
			
			var deleteBtn = $("<button></button>").addClass(
					"btn btn-danger btn-sm delete-btn").append(
					$("<span></span>").addClass("glyphicon glyphicon-trash"))
					.append("删除");
			var btnTd = $("<td></td>").append(editBtn).append(" ").append(
					deleteBtn);
			$("<tr></tr>").append(checkBox).append(empIdTd).append(eName).append(gender).append(
					email).append(dName).append(btnTd).appendTo(
					"#emp_tables tbody");
			
			//在创建删除按钮的时候，给删除按钮绑定该id属性值
			deleteBtn.attr("delete-id",item.eId);
		});
	}
	//2.解析并且显示分页信息
	function build_pag_info(result) {

		//清空分页信息
		$("#page_info").empty();

		$("#page_info").append(
				"当前记录数：" + result.map.pageInfo.pageNum + "页， 总页码："
						+ result.map.pageInfo.pages + ",总记录数："
						+ result.map.pageInfo.total + "");
		totalRecord = result.map.pageInfo.total;//给总记录数赋值
		currentNum = result.map.pageInfo.pageNum;//给当前页赋值
	}
	//3.解析并显示分页条数据
	function build_pag_nav(result) {

		//清空导航信息
		$("#page_nav").empty();

		//1.构建首页和上一页
		var ul = $("<ul></ul>").addClass("pagination");
		var firstLi = $("<li></li>").append(
				$("<a></a>").append("首页").attr("href", "#"));
		var previousLi = $("<li></li>").append($("<a></a>").append("&laquo;"));

		//2.对首页进行判断，是否能点击
		if (result.map.pageInfo.hasPreviousPage == false) {
			firstLi.addClass("disabled")
			previousLi.addClass("disabled")
		} else {
			//为首页和上一页绑定点击事件
			firstLi.click(function() {
				to_page(1);
			});

			previousLi.click(function() {
				to_page(result.map.pageInfo.pageNum - 1);
			});
		}

		var nextLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
		var lastLi = $("<li></li>").append(
				$("<a></a>").append("末页").attr("href", "#"));

		//3.对尾页进行判断，是否能点击
		if (result.map.pageInfo.hasNextPage == false) {
			nextLi.addClass("disabled")
			lastLi.addClass("disabled")
		} else {

			//为末页和下一页绑定点击事件
			nextLi.click(function() {
				to_page(result.map.pageInfo.pageNum + 1);
			});

			lastLi.click(function() {
				to_page(result.map.pageInfo.total);
			});
		}
		//向ul中添加首页和前一页
		ul.append(firstLi).append(previousLi);
		//4.遍历页码号
		$.each(result.map.pageInfo.navigatepageNums, function(index, item) {
			//4.1构建li数字
			var numLi = $("<li></li>").append($("<a></a>").append(item));

			//4.2对遍历的当前页进行判断，如果是当前显示的页码，添加一个样式active
			if (result.map.pageInfo.pageNum == item) {
				numLi.addClass("active");
			}

			//4.3对每一个页码绑定一个点击事件，点击后去指定的页面
			numLi.click(function() {
				to_page(item);
			});

			//5.向ul中添加每一个导航页
			ul.append(numLi);
		})
		//向ul中添加末页和后一页
		ul.append(nextLi).append(lastLi);
		var nav = $("<nav></nav>").append(ul);
		$("#page_nav").append(nav);
	}
</script>

<script type="text/javascript">

	//加载页面
	$(function(){
		//注意：我们是在按钮创建之前就绑定了click,所有没效果
		//:解决办法：1.最简单的就是，在按钮创建的时候绑定事件：2.可以使用on，给后来创建的子选择器，绑定事件
		$(document).on("click",".edit-btn",function(){
			//alert("sss");
			//先查询出员工显示在模态框中
			getEmp($(this).attr("edit-id"));
			
			//把员工id传递到与昂工修改的按钮上
			$("#emp_update").attr("edit-id",$(this).attr("edit-id"));
			
			//点击弹出模特框
			getDepts("#empUpdateModal select");
			$("#empUpdateModal").modal({
				backdrop : "static"
			});
		})
		//2.为每个员工删除绑定点击事件
		$(document).on("click",".delete-btn",function(){
			//2.1弹出是否删除的对话框
			//2.2拿到要删除的员工的id值
			var eName = $(this).parents("tr").find("td:eq(2)").text();
			if(confirm("确定要删除"+eName+"吗?")){
				//确认发送ajax请求删除
				$.ajax({
					url:"${BASE_PATH}/employee/emp/"+$(this).attr("delete-id"),
					type:"delete",
					success:function(result){
						//alert(result.message);
						//alert("xxx")
						to_page(currentNum);
					}
				})
			}
		})
		
		//3.为修改的员工的按钮绑定点击事件
		$("#emp_update").click(function(){
			//alert("dddd");
			//1.需要对修改的表单中的邮箱进行验证是否合法
			var email = $("#update_email").val();
			var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if(!regEmail.test(email)){
				show_validate_msg("#email","error","邮箱格式错误");
				return false;
			}else{
				show_validate_msg("#email","success","邮箱格式正确");
			}
			//发送ajax请求保存与员工信息
			//ajax不能直接发送put请求，因为发送的请求，后台拿不到前台传递的参数1.在请求路径中传递&_method=put
			$.ajax({
				url:"${BASE_PATH}/employee/emp/"+$(this).attr("edit-id"),
				data:$("#empUpdateModal form").serialize(),
				type:"put",
				success:function(result){
					//alert(result);
					//1.关闭模态框
					$("#empUpdateModal").modal("hide");
					//2.回到修改页面
					to_page(currentNum);
				}
			})
		})
		
		//4.做删除，为删除做全选效果
		$("#checkAll").click(function(){
			$(".check_item").prop("checked",$(this).prop("checked"))
		});
		
		//当本页面所有的单选框都选中时，上面的全选框就全部选中
		$(document).on("click",".check_item",function(){
			//判断当前选中的单选框是否为本页的所有记录数
			var flag = $(".check_item:checked").length == $(".check_item").length;
			$("#checkAll").prop("checked",flag);
		});
			
		//给全部删除的与昂工按钮添加绑定事件
		$("#emp_delete_all").click(function(){
			var eNames = "";//定义一个字符串存储所有的员工名字
			var del_ids = "";//定义一个字符串存储所有的员工id
			$(".check_item:checked").each(function(){//遍历每一个被选中的多选框
				//组装员工姓名
				eNames += $(this).parents("tr").find("td:eq(2)").text()+","; 
				//组装员工id的字符串
				del_ids += $(this).parents("tr").find("td:eq(1)").text()+"-";
			});
			eNames = eNames.substring(0, eNames.length-1);//去除最后那个逗号
			if(confirm("确认删除：["+eNames+"]吗?")){
				del_ids = del_ids.substring(0, del_ids.length-1);//去除最后那个-
				alert(del_ids)
				//确认后发送ajax请求
				$.ajax({
					url : "${BASE_PATH}/employee/emp/"+del_ids,
					type : "delete",
					success : function(result){
						//alert("ssss")
						//回到当前页面
						to_page(currentNum);
					}
				});
			}
		});
		
	});
	
	//查询员工id并显示到修改的模态框中
	function getEmp(id){
		$.ajax({
			url:"${BASE_PATH}/employee/emp/"+id,
			type:"get",
			success:function(result){
				//console.log(result);
				var emp = result.map.emp;
				$("#empName").text(emp.eName);
				$("#update_email").val(emp.email);
				$("#empUpdateModal input[name=gender]").val([emp.gender]);
				$("#empUpdateModal select").val([emp.dId]);
			}
		});
	}
</script>

</head>
<body>


	<!-- 员工修改模态框 -->
	<div class="modal fade" id="empUpdateModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">员工修改</h4>
				</div>
				<div class="modal-body">

					<!-- 写入表单信息 -->
					<form class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">员工姓名</label>
							<div class="col-sm-10">
								<p class="form-control-static" id="empName"></p>
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">员工邮箱</label>
							<div class="col-sm-10">
								<input type="email" name="email" class="form-control" id="update_email"
									placeholder="email@znsd.com">
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">员工性别</label>
							<div class="col-sm-10">
								<label class="radio-inline"> <input type="radio"
									name="gender" id="update_gender1" value="m" checked="checked"> 男
								</label> <label class="radio-inline"> <input type="radio"
									name="gender" id="update_gender2" value="f"> 女
								</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">所属部门</label>
							<div class="col-sm-4">
								<select class="form-control" name="dId">
									<!-- 部门编号需要从数据库中查询出来 -->
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="emp_update">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- 新增模态框 -->
	<div class="modal fade" id="empAddModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">员工增加</h4>
				</div>
				<div class="modal-body">

					<!-- 写入表单信息 -->
					<form class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-2 control-label">员工姓名</label>
							<div class="col-sm-10">
								<input type="text" name="eName" class="form-control" id="eName"
									placeholder="empName">
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">员工邮箱</label>
							<div class="col-sm-10">
								<input type="email" name="email" class="form-control" id="email"
									placeholder="email@znsd.com">
								<span class="help-block"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">员工性别</label>
							<div class="col-sm-10">
								<label class="radio-inline"> <input type="radio"
									name="gender" id="gender1" value="m" checked="checked"> 男
								</label> <label class="radio-inline"> <input type="radio"
									name="gender" id="gender2" value="f"> 女
								</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">所属部门</label>
							<div class="col-sm-4">
								<select class="form-control" name="dId">
									<!-- 部门编号需要从数据库中查询出来 -->
								</select>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="emp_save">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题栏 -->
		<div class="row">
			<div class="col-md-12">
				<h1>SSM-CRUD</h1>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-8">
				<button type="button" class="btn btn-primary" id="emp_add_modal_btn">新增</button>
				<button type="button" class="btn btn-danger" id="emp_delete_all">删除</button>
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover" id="emp_tables">
					<thead>
						<tr>
							<th>
								<input type="checkbox" id="checkAll"/>
							</th>
							<th>#</th>
							<th>empName</th>
							<th>gender</th>
							<th>email</th>
							<th>deptName</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<tr></tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 分页码 -->
		<div class="row">
			<!-- 分页文字信息 -->
			<div class="col-md-6" id="page_info"></div>
			<!-- 分页条 -->
			<div class="col-md-6" id="page_nav"></div>
		</div>
	</div>


</body>
</html>