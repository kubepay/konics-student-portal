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
	  
  $('#strdtBirth').datepicker({
        format: 'dd/mm/yyyy',
        todayBtn: 'linked'
   });
  $('#strdtJoining').datepicker({
        format: 'dd/mm/yyyy',
        todayBtn: 'linked'
   });
  
  $('#strdtLeaving').datepicker({
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
          <c:when test="${studentForm['new']}">
            <h1 class="page-header">Add Student</h1>
          </c:when>
          <c:otherwise>
            <h1 class="page-header">Update Student</h1>
          </c:otherwise>
        </c:choose>
        <br />
        <spring:url value="${contextPath}/student" var="studentActionUrl" />

        <form:form class="form-horizontal" method="post" modelAttribute="studentForm" action="${studentActionUrl}">
          <form:hidden path="id" />
          <div class="row">
            <div class="col-md-6">
              <div class="row">
                <spring:bind path="rollNumber">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Roll Number</label>
                    <div class="col-sm-7">
                      <form:input path="rollNumber" type="text" class="form-control " id="rollNumber" placeholder="Enter Roll Number" />
                      <form:errors path="rollNumber" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="name">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Student Name</label>
                    <div class="col-sm-7">
                      <form:input path="name" type="text" class="form-control " id="name" placeholder="Enter Student Name" required="required"/>
                      <form:errors path="name" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="batchId">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Batch</label>
                    <div class="col-sm-7">
                      <form:select path="batchId" class="form-control">
                        <form:option value="0" label="--- Select ---" />
                        <form:options items="${batchList}" />
                      </form:select>
                      <form:errors path="batchId" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="fatherName">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Father's Name</label>
                    <div class="col-sm-7">
                      <form:input path="fatherName" type="text" class="form-control " id="fatherName" placeholder="Enter Father's Name" />
                      <form:errors path="fatherName" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="occupation">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Parent's Occupation</label>
                    <div class="col-sm-7">
                      <form:input path="occupation" type="text" class="form-control " id="occupation" placeholder="Enter Parent's Occupation" />
                      <form:errors path="occupation" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="strdtJoining">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Date of Joining</label>
                    <div class="col-sm-7">
                      <form:input path="strdtJoining" type="text" class="form-control span2" id="strdtJoining" placeholder="dd/mm/yyyy"/>
                      <form:errors path="strdtJoining" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="address1">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Address Line 1</label>
                    <div class="col-sm-7">
                      <form:input path="address1" type="text" class="form-control " id="address1" placeholder="Enter Address Line 1" />
                      <form:errors path="address1" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="address3">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Address Line 3</label>
                    <div class="col-sm-7">
                      <form:input path="address3" type="text" class="form-control " id="address3" placeholder="Enter Address Line 3" />
                      <form:errors path="address3" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="phone">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Phone Number</label>
                    <div class="col-sm-7">
                      <form:input path="phone" type="text" class="form-control " id="phone" placeholder="Enter Phone Number" />
                      <form:errors path="phone" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
               <spring:bind path="mobile">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Mobile Number</label>
                    <div class="col-sm-7">
                      <form:input path="mobile" type="text" class="form-control " id="mobile" placeholder="Enter Mobile Number" />
                      <form:errors path="mobile" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="category">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Category</label>
                    <div class="col-sm-7">
                      <form:input path="category" type="text" class="form-control " id="category" placeholder="Enter Category" />
                      <form:errors path="category" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="bloodGroup">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Blood Group</label>
                    <div class="col-sm-7">
                      <form:select path="bloodGroup" class="form-control">
                        <form:option value="NONE" label="--- Select ---" />
                        <form:options items="${bloodGroupsList}" />
                      </form:select>
                      <form:errors path="bloodGroup" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="remarks">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Remarks</label>
                    <div class="col-sm-7">
                      <form:textarea path="remarks" rows="5" class="form-control" id="remarks" placeholder="Enter Remarks" />
                      <form:errors path="remarks" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
           </div>
           <div class="col-md-6">
              <div class="row">
                <spring:bind path="regno">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Registration Number</label>
                    <div class="col-sm-7">
                      <form:input path="regno" type="text" class="form-control " id="regno" placeholder="Enter Registration Number" />
                      <form:errors path="regno" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="attendance">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Attendance</label>
                    <div class="col-sm-7">
                      <form:input path="attendance" type="text" class="form-control " id="attendance" placeholder="Enter Attendance" />
                      <form:errors path="attendance" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
               <spring:bind path="activeId">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Active</label>
                    <div class="col-sm-7">
                      <label class="radio-inline"> <form:radiobutton path="activeId" value="1"/> Yes
                      </label> <label class="radio-inline"> <form:radiobutton path="activeId" value="0" /> No
                      </label> <br />
                      <form:errors path="activeId" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="motherName">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Mother's Name</label>
                    <div class="col-sm-7">
                      <form:input path="motherName" type="text" class="form-control " id="motherName" placeholder="Enter Mother's Name" />
                      <form:errors path="motherName" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="strdtBirth">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Date of Birth</label>
                    <div class="col-sm-7">
                      <form:input path="strdtBirth" type="text" class="form-control span2" id="strdtBirth" placeholder="dd/mm/yyyy"/>
                      <form:errors path="strdtBirth" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="strdtLeaving">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Date of Leaving</label>
                    <div class="col-sm-7">
                      <form:input path="strdtLeaving" type="text" class="form-control span2" id="strdtLeaving" placeholder="dd/mm/yyyy"/>
                      <form:errors path="strdtLeaving" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="address2">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Address Line 2</label>
                    <div class="col-sm-7">
                      <form:input path="address2" type="text" class="form-control " id="address2" placeholder="Address Line 2" />
                      <form:errors path="address2" class="control-label" />
                    </div>
                  </div>
              </spring:bind>
              </div>
              <div class="row">
               <spring:bind path="state">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">State</label>
                    <div class="col-sm-7">
                      <form:select path="state" class="form-control">
                        <form:option value="NONE" label="--- Select ---" />
                        <form:options items="${statesList}" />
                      </form:select>
                      <form:errors path="state" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
               <spring:bind path="otherPhone">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Alternate Phone Number</label>
                    <div class="col-sm-7">
                      <form:input path="otherPhone" type="text" class="form-control " id="otherPhone" placeholder="Alternate Phone Number" />
                      <form:errors path="otherPhone" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="email">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Email Id</label>
                    <div class="col-sm-7">
                      <form:input path="email" type="text" class="form-control " id="email" placeholder="Enter Email Id" />
                      <form:errors path="email" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
              <div class="row">
              <spring:bind path="gender">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                  <label class="col-sm-4 control-label">Gender</label>
                  <div class="col-sm-7">
                    <form:select path="gender" class="form-control">
                      <form:options items="${gendersList}" />
                    </form:select>
                    <form:errors path="gender" class="control-label" />
                  </div>
                </div>
              </spring:bind>
              </div>
              <div class="row">
              <spring:bind path="schoolName">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                  <label class="col-sm-4 control-label">School Name</label>
                  <div class="col-sm-7">
                    <form:input path="schoolName" type="text" class="form-control " id="category" placeholder="Enter School Name" />
                    <form:errors path="schoolName" class="control-label" />
                  </div>
                </div>
              </spring:bind>
              </div>
              <div class="row">
                <spring:bind path="percentage">
                  <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-4 control-label">Percentage</label>
                    <div class="col-sm-7">
                      <form:input path="percentage" type="text" class="form-control " id="percentage" placeholder="Enter Percentage" />
                      <form:errors path="percentage" class="control-label" />
                    </div>
                  </div>
                </spring:bind>
              </div>
           </div>
         </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <c:choose>
                <c:when test="${studentForm['new']}">
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
