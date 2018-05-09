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
        <h1 class="page-header">Student Details</h1>
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
              <div class="col-sm-8"><strong>${student.name}</strong></div>
            </div>
            <hr />
            <div class="row">
              <label class="col-sm-4">Gender</label>
              <div class="col-sm-8">${student.gender}</div>
            </div>

            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Course Name</label>
              <div class="col-sm-8">${student.batch.courseName}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Period</label>
              <div class="col-sm-8">${student.batch.sessionYear}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Center</label>
              <div class="col-sm-8">${student.batch.center.centerName}</div>
            </div>
            <div class="row">
              <label class="col-sm-4">Batch Active</label>
              <div class="col-sm-8">${student.batch.active}</div>
            </div>
            <hr/>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Father's Name</label>
              <div class="col-sm-8">${student.fatherName}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">G Occupation</label>
              <div class="col-sm-8">${student.occupation}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Date of Joining</label>
              <div class="col-sm-8">${student.strdtJoining}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Address Line 1</label>
              <div class="col-sm-8">${student.address1}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Address Line 3</label>
              <div class="col-sm-8">${student.address3}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Phone Number</label>
              <div class="col-sm-8">${student.phone}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Mobile Number</label>
              <div class="col-sm-8">${student.mobile}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Remarks</label>
              <div class="col-sm-8">${student.remarks}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">School Name</label>
              <div class="col-sm-8">${student.schoolName}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Attendance</label>
              <div class="col-sm-8">${student.attendance}</div>
            </div>
          </div>

          <div class="col-md-6">
            <div class="row">
              <label class="col-sm-4">Roll Number</label>
              <div class="col-sm-8"><strong>${student.rollNumber}</strong></div>
            </div>
            <hr />
            <div class="row">
              <label class="col-sm-4">Reg. Number</label>
              <div class="col-sm-8">${student.regno}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Stream</label>
              <div class="col-sm-8">${student.batch.stream}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Section</label>
              <div class="col-sm-8">${student.batch.section}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Active</label>
              <div class="col-sm-8">${student.active}</div>
            </div>
            <div class="row">
              <label class="col-sm-4">Center Active</label>
              <div class="col-sm-8">${student.batch.center.active}</div>
            </div>
            <hr/>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Mother's Name</label>
              <div class="col-sm-8">${student.motherName}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Date Of Birth</label>
              <div class="col-sm-8">${student.strdtBirth}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Date of Leaving</label>
              <div class="col-sm-8">${student.strdtLeaving}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Address Line 2</label>
              <div class="col-sm-8">${student.address2}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">State</label>
              <div class="col-sm-8">${student.state}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Alternate Number</label>
              <div class="col-sm-8">${student.otherPhone}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Email Id</label>
              <div class="col-sm-8">${student.email}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Blood Group</label>
              <div class="col-sm-8">${student.bloodGroup}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Percentage</label>
              <div class="col-sm-8">${student.percentage}</div>
            </div>
            <div class="row" style="line-height: 50%;">&nbsp;</div>
            <div class="row">
              <label class="col-sm-4">Category</label>
              <div class="col-sm-8">${student.category}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>

</html>
