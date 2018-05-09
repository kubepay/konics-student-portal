<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
<link href="${contextPath}/css/datepicker.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/DataTables/datatables.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap-datepicker.js"></script>
<script type="text/javascript">
$(function(){
	$('#strdtConduct').datepicker({
        format: 'dd/mm/yyyy',
        todayBtn: 'linked'
   });
	$('#strdtResult').datepicker({
        format: 'dd/mm/yyyy',
        todayBtn: 'linked'
   });
	
});

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
      <jsp:include page="../partial/sidebar.jsp" />
      <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

        <c:choose>
          <c:when test="${examForm['new']}">
            <h1 class="page-header">Add Exam</h1>
          </c:when>
          <c:otherwise>
            <h1 class="page-header">Update Exam</h1>
          </c:otherwise>
        </c:choose>
        <br />
        <spring:url value="${contextPath}/exam" var="examActionUrl" />

        <form:form class="form-horizontal" method="post" modelAttribute="examForm" action="${examActionUrl}">
          <form:hidden path="id" />

          <span id="helpBlock" class="help-block">
            *Combination of Exam Name, Subject and Batch should be unique.
          </span>

          <spring:bind path="name">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Exam Name*</label>
              <div class="col-sm-5">
                <form:input path="name" type="text" class="form-control " id="name" placeholder="Enter Exam name" required="required"/>
                <form:errors path="name" class="control-label" />
              </div>
            </div>
          </spring:bind>
          
          <spring:bind path="subject">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Subject*</label>
              <div class="col-sm-5">
                <form:select path="subject" class="form-control">
                  <form:options items="${subjectList}" />
                </form:select>
                <form:errors path="subject" class="control-label" />
              </div>
              <div class="col-sm-5"></div>
            </div>
          </spring:bind>
          
          <spring:bind path="totalMarks">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Total Marks</label>
              <div class="col-sm-5">
                <form:input path="totalMarks" type="text" class="form-control " id="totalMarks" placeholder="Enter Total Marks" onkeypress="return isNumberKey(event)"/>
                <form:errors path="totalMarks" class="control-label" />
              </div>
            </div>
          </spring:bind>
          
          <spring:bind path="passingMarks">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Passing marks</label>
              <div class="col-sm-5">
                <form:input path="passingMarks" type="text" class="form-control " id="passingMarks" placeholder="Enter Passing marks"  onkeypress="return isNumberKey(event)"/>
                <form:errors path="passingMarks" class="control-label" />
              </div>
            </div>
          </spring:bind>
          
          <spring:bind path="preparedBy">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Prepared By</label>
              <div class="col-sm-5">
                <form:input path="preparedBy" type="text" class="form-control " id="preparedBy" placeholder="Enter Prepared By" />
                <form:errors path="preparedBy" class="control-label" />
              </div>
            </div>
          </spring:bind>
         
          <spring:bind path="evaluatedBy">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Evaluated By</label>
              <div class="col-sm-5">
                <form:input path="evaluatedBy" type="text" class="form-control " id="evaluatedBy" placeholder="Enter Evaluated By" />
                <form:errors path="evaluatedBy" class="control-label" />
              </div>
            </div>
          </spring:bind>
          
          <spring:bind path="batchId">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Batch*</label>
              <div class="col-sm-5">
                <form:select path="batchId" class="form-control">
                  <form:options items="${batchList}" />
                </form:select>
                <form:errors path="batchId" class="control-label" />
              </div>
              <div class="col-sm-5"></div>
            </div>
          </spring:bind>

          <spring:bind path="activeId">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Active*</label>
              <div class="col-sm-10">
                <label class="radio-inline"> <form:radiobutton path="activeId" value="1"/> Yes
                </label> <label class="radio-inline"> <form:radiobutton path="activeId" value="0" /> No
                </label> <br />
                <form:errors path="activeId" class="control-label" />
              </div>
            </div>
          </spring:bind>
          
          <spring:bind path="strdtConduct">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Date of Conduct</label>
              <div class="col-sm-5">
                <form:input path="strdtConduct" type="text" class="form-control span2" id="strdtConduct" placeholder="dd/mm/yyyy"/>
                <form:errors path="strdtConduct" class="control-label" />
              </div>
            </div>
          </spring:bind>
          
          <spring:bind path="strdtResult">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Date of Result</label>
              <div class="col-sm-5">
                <form:input path="strdtResult" type="text" class="form-control span2" id="strdtResult" placeholder="dd/mm/yyyy"/>
                <form:errors path="strdtResult" class="control-label" />
              </div>
            </div>
          </spring:bind>

          <spring:bind path="remarks">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Remarks</label>
              <div class="col-sm-5">
                <form:textarea path="remarks" rows="5" class="form-control" id="remarks" placeholder="Enter Remarks" />
                <form:errors path="remarks" class="control-label" />
              </div>
            </div>
          </spring:bind>

          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <c:choose>
                <c:when test="${examForm['new']}">
                  <button type="submit" class="btn-lg btn-primary pull-right">Add</button>
                </c:when>
                <c:otherwise>
                  <button type="submit" class="btn-lg btn-primary pull-right">Update</button>
                </c:otherwise>
              </c:choose>
            </div>
          </div>
        </form:form>
      </div>
    </div>
  </div>
</body>

</html>
