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
<link href="${contextPath}/css/dashboard.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/DataTables/datatables.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/DataTables/datatables.min.js"></script>
</head>
<body>

  <jsp:include page="../partial/navbar.jsp" />

  <div class="container-fluid">
    <div class="row">
      <div class="col-sm-3 col-md-2 sidebar">
        <jsp:include page="../partial/sidebar.jsp" />
      </div>
      <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <h1 class="page-header">Batch Details</h1>
        <c:if test="${not empty msg}">
          <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
          </div>
        </c:if>
        <div class="row">
          <label class="col-sm-2">Program</label>
          <div class="col-sm-10">${batch.courseName}</div>
        </div>
        <div class="row">
          <label class="col-sm-2">Program Code</label>
          <div class="col-sm-10">${batch.code}</div>
        </div>
        <div class="row">
          <label class="col-sm-2">Period</label>
          <div class="col-sm-10">${batch.sessionYear}</div>
        </div>
        <div class="row">
          <label class="col-sm-2">Stream</label>
          <div class="col-sm-10">${batch.stream}</div>
        </div>
        <div class="row">
          <label class="col-sm-2">Section</label>
          <div class="col-sm-10">${batch.section}</div>
        </div>
        <div class="row">
          <label class="col-sm-2">Active</label>
          <div class="col-sm-10">${batch.active}</div>
        </div>
        <div class="row">
          <label class="col-sm-2">Center Code</label>
          <div class="col-sm-10">${batch.center.centerCode}</div>
        </div>
        <div class="row">
          <label class="col-sm-2">Attendance</label>
          <div class="col-sm-10">${batch.attendance}</div>
        </div>
        <div class="row">
          <label class="col-sm-2">Center</label>
          <div class="col-sm-10">${batch.center.centerName}</div>
        </div>
      </div>
    </div>
  </div>
</body>

</html>
