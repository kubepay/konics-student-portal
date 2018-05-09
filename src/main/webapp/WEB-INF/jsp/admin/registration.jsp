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
<meta name="description" content="">
<meta name="author" content="">
<title>Log in with your account</title>
<title>Konics Dashboard</title>
<link href="${contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
</head>

<body>

  <div class="container">
  <%-- 
    <form:form method="POST" modelAttribute="userForm" class="form-signin">
      <h2 class="form-signin-heading">Create your account</h2>
      <spring:bind path="username">
        <div class="form-group ${status.error ? 'has-error' : ''}">
          <form:input type="text" path="username" class="form-control" placeholder="Username" autofocus="true"></form:input>
          <form:errors path="username"></form:errors>
        </div>
      </spring:bind>

      <spring:bind path="password">
        <div class="form-group ${status.error ? 'has-error' : ''}">
          <form:input type="password" path="password" class="form-control" placeholder="Password"></form:input>
          <form:errors path="password"></form:errors>
        </div>
      </spring:bind>

      <spring:bind path="passwordConfirm">
        <div class="form-group ${status.error ? 'has-error' : ''}">
          <form:input type="password" path="passwordConfirm" class="form-control" placeholder="Confirm your password"></form:input>
          <form:errors path="passwordConfirm"></form:errors>
        </div>
      </spring:bind>

      <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
    </form:form>
    --%>
  </div>
</body>
</html>