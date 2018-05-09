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
</head>
<body>
  <jsp:include page="../partial/navbar.jsp" />
  <div class="container-fluid">
    <div class="row">
      <jsp:include page="../partial/sidebar.jsp" />
      <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">

        <c:choose>
          <c:when test="${batchForm['new']}">
            <h1 class="page-header">Add Batch</h1>
          </c:when>
          <c:otherwise>
            <h1 class="page-header">Update Batch</h1>
          </c:otherwise>
        </c:choose>
        <br />
        <spring:url value="${contextPath}/batch" var="userActionUrl" />

        <form:form class="form-horizontal" method="post" modelAttribute="batchForm" action="${userActionUrl}">
          <form:hidden path="id" />

          <spring:bind path="courseName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Program*</label>
              <div class="col-sm-5">
                <form:input path="courseName" type="text" class="form-control " id="courseName" placeholder="Enter Course Name" required="required"/>
                <form:errors path="courseName" class="control-label" />
              </div>
            </div>
          </spring:bind>
          
          <spring:bind path="code">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Program Code</label>
              <div class="col-sm-5">
                <form:input path="code" type="text" class="form-control " id="code" placeholder="Enter Course Name" />
                <form:errors path="code" class="control-label" />
              </div>
            </div>
          </spring:bind>
          
          <spring:bind path="attendance">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Attendance</label>
              <div class="col-sm-5">
                <form:input path="attendance" type="text" class="form-control " id="attendance" placeholder="Enter Attendance" />
                <form:errors path="attendance" class="control-label" />
              </div>
            </div>
          </spring:bind>
          
          <spring:bind path="sessionYear">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Period*</label>
              <div class="col-sm-5">
                <form:select path="sessionYear" class="form-control">
                  <form:options items="${sessionList}" />
                </form:select>
                <form:errors path="sessionYear" class="control-label" />
              </div>
              <div class="col-sm-5"></div>
            </div>
          </spring:bind>

          <spring:bind path="stream">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Stream*</label>
              <div class="col-sm-5">
                <form:select path="stream" class="form-control">
                  <form:options items="${streamList}" />
                </form:select>
                <form:errors path="stream" class="control-label" />
              </div>
              <div class="col-sm-5"></div>
            </div>
          </spring:bind>
          
           <spring:bind path="section">
            <div class="form-group ${status.error ? 'has-error' : ''}">
              <label class="col-sm-2 control-label">Section*</label>
              <div class="col-sm-5">
                <form:select path="section" class="form-control">
                  <form:options items="${sectionList}" />
                </form:select>
                <form:errors path="section" class="control-label" />
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

          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <c:choose>
                <c:when test="${batchForm['new']}">
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
