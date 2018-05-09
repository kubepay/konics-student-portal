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
        <h1 class="page-header">Exam Details</h1>
        <c:if test="${not empty msg}">
          <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
          </div>
        </c:if>
        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <label class="col-sm-4">Exam Name</label>
              <div class="col-sm-8">${exam.name}</div>
            </div>
            <hr />
            <div class="row">
              <label class="col-sm-4">Course Name</label>
              <div class="col-sm-8">${exam.batch.courseName}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Period</label>
              <div class="col-sm-8">${exam.batch.sessionYear}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Total Marks</label>
              <div class="col-sm-8">${exam.totalMarks}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Prepared By</label>
              <div class="col-sm-8">${exam.preparedBy}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Date Of Conduct</label>
              <div class="col-sm-8">${exam.strdtConduct}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Center</label>
              <div class="col-sm-8">${exam.batch.center.centerName}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Center Active</label>
              <div class="col-sm-8">${exam.batch.center.active}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Remarks</label>
              <div class="col-sm-8">${exam.remarks}</div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <label class="col-sm-4">Exam Subject</label>
              <div class="col-sm-8">${exam.subject}</div>
            </div>
            <hr />
            <div class="row">
              <label class="col-sm-4">Stream</label>
              <div class="col-sm-8">${exam.batch.stream}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Section</label>
              <div class="col-sm-8">${exam.batch.section}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Passing Marks</label>
              <div class="col-sm-8">${exam.passingMarks}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Evaluated By</label>
              <div class="col-sm-8">${exam.evaluatedBy}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Date of Result</label>
              <div class="col-sm-8">${exam.strdtResult}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Active</label>
              <div class="col-sm-8">${exam.active}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Batch Active</label>
              <div class="col-sm-8">${exam.batch.active}</div>
            </div>
          </div>
        </div>
        <hr />
















      </div>
    </div>
  </div>
</body>

</html>
