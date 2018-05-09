<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Konics Portal">
<meta name="author" content="Abhishek Pandey">
<title>Konics Dashboard</title>
<link href="${contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/DataTables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/dashboard.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/DataTables/datatables.min.js"></script>
<script type="text/javascript">
	function post(path, params, method) {
		method = method || "post";

		var form = document.createElement("form");
		form.setAttribute("method", method);
		form.setAttribute("action", path);

		for ( var key in params) {
			if (params.hasOwnProperty(key)) {
				var hiddenField = document.createElement("input");
				hiddenField.setAttribute("type", "hidden");
				hiddenField.setAttribute("name", key);
				hiddenField.setAttribute("value", params[key]);

				form.appendChild(hiddenField);
			}
		}

		document.body.appendChild(form);
		form.submit();
	}
</script>
</head>
<body>
  <jsp:include page="../partial/navbar.jsp" />
  <div class="container-fluid">
    <div class="row">
      <jsp:include page="../partial/sidebar.jsp" />
      <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <h1 class="page-header">Exam Management</h1>

        <c:if test="${user.role ne 'GUEST'}">
          <h2 class="sub-header">
            <spring:url value="/exam/add" var="urlAddUser" />
            <a href="${urlAddUser}" class="btn btn-primary btn-lg" role="button">Add Exam</a>
          </h2>
        </c:if>

        <c:if test="${not empty msg}">
          <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
          </div>
        </c:if>
        <div class="table-responsive">
          <table class="table table-striped table-bordered" id="batch_table">
            <thead>
              <tr>
                <th class="col-md-1">#</th>
                <th class="col-md-2">Name</th>
                <th class="col-md-1">Subject</th>
                <th class="col-md-1">Date of Conduct</th>
                <th class="col-md-3">Batch</th>
                <th class="col-md-1">Active</th>
                <th class="col-md-3">Action</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="exam" items="${exams}">
                <tr>
                  <td>${exam.id}</td>
                  <td>${exam.name}</td>
                  <td>${exam.subject}</td>
                  <td>${exam.strdtConduct}</td>
                  <td>${exam.batch.courseName} ${exam.batch.section} ${exam.batch.sessionYear}</td>
                  <td>${exam.active}</td>
                  <td>
                    <div class="btn-group" role="group">
                      <spring:url value="/exam/${exam.id}" var="examUrl" />
                      <button class="btn btn-info" onclick="location.href='${examUrl}'">
                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                      </button>

                      <c:if test="${user.role ne 'GUEST'}">
                        <spring:url value="/exam/${exam.id}/update" var="updateUrl" />
                        <button class="btn btn-primary" onclick="location.href='${updateUrl}'">
                          <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                        </button>
                      </c:if>

                      <c:if test="${user.role == 'ADMIN'}">
                        <spring:url value="/exam/${exam.id}/delete" var="deleteUrl" />
                        <button class="btn btn-danger" onclick="this.disabled=true;post('${deleteUrl}')">
                          <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        </button>
                      </c:if>
                      <c:if test="${user.role ne 'GUEST'}">
                        <spring:url value="/exam/${exam.id}/marks" var="reportCardUrl" />
                        <button class="btn btn-info" onclick="location.href='${reportCardUrl}'">
                          <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>
                        </button>
                      </c:if>
                      <c:if test="${user.role ne 'GUEST'}">
                      <spring:url value="/exam/${exam.id}/reportcard" var="reportCardUrl" />
                      <button class="btn btn-info" onclick="location.href='${reportCardUrl}'">
                        <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
                      </button>
                      </c:if>
                    </div>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
            <tfoot>
              <tr>
                <th class="col-md-1">#</th>
                <th class="col-md-2">Name</th>
                <th class="col-md-1">Subject</th>
                <th class="col-md-1">Date of Conduct</th>
                <th class="col-md-3">Batch</th>
                <th class="col-md-1">Active</th>
                <th class="col-md-3">Action</th>
              </tr>
            </tfoot>
          </table>
        </div>
      </div>
    </div>
  </div>
  <script type="text/javascript">
			$(document).ready(function() {
				$('#batch_table').DataTable();
			});
		</script>
</body>
</html>
