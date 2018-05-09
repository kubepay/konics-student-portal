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
<script type="text/javascript">
function isNumberKey(evt)
{
  var charCode = (evt.which) ? evt.which : evt.keyCode;
  if (charCode != 46 && charCode > 31 
  && (charCode < 48 || charCode > 57))
  return false;
  return true;
}
</script>
</head>
<body>

  <jsp:include page="../partial/navbar.jsp" />

  <div class="container-fluid">
    <div class="row">
      <div class="col-sm-3 col-md-2 sidebar">
        <jsp:include page="../partial/sidebar.jsp" />
      </div>
      <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
        <h1 class="page-header">Marks Form</h1>
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
              <label class="col-sm-4">Name</label>
              <div class="col-sm-8"><strong>${exam.name}</strong></div>
            </div>
            <div class="row">
              <label class="col-sm-4">Total Marks</label>
              <div class="col-sm-8"><strong>${exam.totalMarks}</strong></div>
            </div>
            <div class="row">
              <label class="col-sm-4">Prepared By</label>
              <div class="col-sm-8"><strong>${exam.preparedBy}</strong></div>
            </div>
            <div class="row">
              <label class="col-sm-4">strdtConduct</label>
              <div class="col-sm-8"><strong>${exam.strdtConduct}</strong></div>
            </div>
            <div class="row">
              <label class="col-sm-4">Course Name</label>
              <div class="col-sm-8"><strong>${exam.batch.courseName}</strong></div>
            </div>
            <div class="row">
              <label class="col-sm-4">Stream</label>
              <div class="col-sm-8"><strong>${exam.batch.stream}</strong></div>
            </div>
            <div class="row">
              <label class="col-sm-4">Attendance</label>
              <div class="col-sm-8"><strong>${exam.batch.attendance}</strong></div>
            </div>
            <div class="row">
              <label class="col-sm-4">Remarks</label>
              <div class="col-sm-8"><strong>${exam.remarks}</strong></div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <label class="col-sm-4">Subject</label>
              <div class="col-sm-8"><strong>${exam.subject}</strong></div>
            </div>
            <div class="row">
              <label class="col-sm-4">Passing Marks</label>
              <div class="col-sm-8"><strong>${exam.passingMarks}</strong></div>
            </div>
            <div class="row">
              <label class="col-sm-4">Evaluated By</label>
              <div class="col-sm-8"><strong>${exam.evaluatedBy}</strong></div>
            </div>
            <div class="row">
              <label class="col-sm-4">strdtResult</label>
              <div class="col-sm-8"><strong>${exam.strdtResult}</strong></div>
            </div>
            <div class="row">
              <label class="col-sm-4">Active</label>
              <div class="col-sm-8"><strong>${exam.active}</strong></div>
            </div>
            <div class="row">
              <label class="col-sm-4">Session Year</label>
              <div class="col-sm-8"><strong>${exam.batch.sessionYear}</strong></div>
            </div>
            <div class="row">
              <label class="col-sm-4">Section</label>
              <div class="col-sm-8"><strong>${exam.batch.section}</strong></div>
            </div>
            <div class="row">
              <label class="col-sm-4">Batch Active</label>
              <div class="col-sm-8"><strong>${exam.batch.active}</strong></div>
            </div>
          </div>
        </div>
        <div class="row">
          <span id="helpBlock" class="help-block">
            *Leave TextBox blank, if you want to mark student absent.
          </span>
          <spring:url value="${contextPath}/marks" var="marksActionUrl" />
          <form:form class="form-horizontal" method="post" modelAttribute="marksForm" action="${marksActionUrl}">
          <div class="table-responsive">
          <table class="table table-striped table-bordered" id="marks_table">
              <tr>
                <th>Id</th>
                <th>Roll No.</th>
                <th>Student Name</th>
                <th>Mark</th>
              </tr>
              <c:forEach items="${marks}" var="mark" varStatus="status">
                <tr>
                  <td align="center">${status.count}
                  <input type="hidden" name="marks[${status.index}].id" value="${mark.id}" />
                  <input type="hidden" name="marks[${status.index}].active" value="${mark.active}" />
                  <input type="hidden" name="marks[${status.index}].studentId" value="${mark.studentId}" />
                  <input type="hidden" name="marks[${status.index}].centerId" value="${mark.centerId}" />
                  <input type="hidden" name="marks[${status.index}].batchId" value="${mark.batchId}" />
                  <input type="hidden" name="marks[${status.index}].examId" value="${mark.examId}" />
                  </td>
                  <td><input name="marks[${status.index}].rollNumber" value="${mark.rollNumber}" class="form-control" disabled /></td>
                  <td><input name="marks[${status.index}].studentName" value="${mark.studentName}" class="form-control" disabled /></td>
                  <td><input name="marks[${status.index}].mark" value="${mark.mark}"  class="form-control" onkeypress="return isNumberKey(event)"/></td>
                </tr>
              </c:forEach>
            </table>
            </div>
            <br />
            <div class="form-group">
              <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn-lg btn-primary pull-right">Save</button>
              </div>
            </div>
          </form:form>
        </div>
        <hr />
      </div>
    </div>
  </div>
</body>
</html>
