<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
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
</head>
<body>
	<!-- 使用bootstrap快速搭建显示页面 -->
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
				<button type="button" class="btn btn-primary">新增</button>
				<button type="button" class="btn btn-danger">删除</button>
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover">
					<tr>
						<th>#</th>
						<th>empName</th>
						<th>gender</th>
						<th>email</th>
						<th>deptName</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${pageInfo.list }" var="emp">
						<tr>
							<th>${emp.eId }</th>
							<th>${emp.eName }</th>
							<th>${emp.gender == "m" ? "男" : "女"}</th>
							<th>${emp.email }</th>
							<th>${emp.department.dName }</th>
							<th>
								<button type="button" class="btn btn-primary btn-sm">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
									编辑
								</button>
								<button type="button" class="btn btn-danger btn-sm">
									<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
									删除
								</button>
							</th>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<!-- 分页码 -->
		<div class="row">
			<!-- 分页文字信息 -->
			<div class="col-md-6">当前记录数：${pageInfo.pageNum }页，总页码：${pageInfo.pages },总记录数：${pageInfo.total }</div>
			<!-- 分页条 -->
			<div class="col-md-6">
				<nav aria-label="Page navigation">
					<ul class="pagination">
						<li>
							<a href="${BASE_PATH }/employee/emps?pg=1">首页</a>
						</li>
						<li>
							<c:if test="${pageInfo.hasPreviousPage }">
								<a href="${BASE_PATH }/employee/emps?pg=${pageInfo.pageNum-1 }" aria-label="Previous"> 
									<span aria-hidden="true">&laquo;</span>
								</a>
							</c:if>
						</li>
						<c:forEach items="${pageInfo.navigatepageNums }" var="page">
							<c:if test="${page == pageInfo.pageNum }">
								<li class="active"><a href="#">${page }</a></li>
							</c:if>
							<c:if test="${page != pageInfo.pageNum }">
								<li><a href="${BASE_PATH }/employee/emps?pg=${page }">${page }</a></li>
							</c:if>
							
						</c:forEach>
						
						<!-- <li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">5</a></li> -->
						<li>
							<c:if test="${pageInfo.hasNextPage }">
								<a href="${BASE_PATH }/employee/emps?pg=${pageInfo.pageNum+1 }" aria-label="Next">
									<span aria-hidden="true">&raquo;</span>
								</a>
							</c:if>
						</li>
						<li>
							<a href="${BASE_PATH }/employee/emps?pg=${pageInfo.pages }">末页</a>
						</li>
					</ul>
				</nav>
			</div>
		</div>
	</div>


</body>
</html>