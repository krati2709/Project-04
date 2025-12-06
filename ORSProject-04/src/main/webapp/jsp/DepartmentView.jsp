<%@page import="in.co.rays.proj4.controller.DepartmentCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.UserCtl"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<html>
<head>
<title>Add Department</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<%@ include file="Header.jsp"%>
	<form action="<%=ORSView.DEPARTMENT_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.DepartmentBean"
			scope="request"></jsp:useBean>


		<div align="center">
			<h1 align="center" style="margin-bottom: -15; color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>Update<%
					} else {
				%>Add<%
					}
				%>
				Department
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<H3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</H3>

				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>
				<tr>
					<th align="left">Department Name<span style="color: red">*</span></th>
					<td><input type="text" name="name"
						placeholder="Enter Role Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<tr>
					<th align="left">Description<span style="color: red">*</span></th>

					<td><textarea name="desc" placeholder="Enter description"
							rows="4" cols="35"><%=DataUtility.getStringData(bean.getDescription())%></textarea>
					</td>

					<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("desc", request)%></font>
					</td>
				</tr>

				<th></th>
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				<td align="left" colspan="2"><input type="submit"
					name="operation" value="<%=DepartmentCtl.OP_UPDATE%>"> <input
					type="submit" name="operation" value="<%=DepartmentCtl.OP_CANCEL%>">
					<%
						} else {
					%>
				<td align="left" colspan="2"><input type="submit"
					name="operation" value="<%=DepartmentCtl.OP_SAVE%>"> <input
					type="submit" name="operation" value="<%=DepartmentCtl.OP_RESET%>">
					<%
						}
					%>
				</tr>
			</table>
		</div>
	</form>
	<%@ include file="Footer.jsp"%>
</body>
</html>