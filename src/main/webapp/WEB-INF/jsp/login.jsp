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
<meta name="description" content="">
<meta name="author" content="">
<title>Log in with your account</title>
<title>Konics Dashboard</title>
<link href="${contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/js/jquery.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/bootstrap.min.js"></script>
</head>

<body>
  <div class="container">
    <form method="POST" action="${contextPath}/login" class="form-signin">
      <h2 class="form-heading">Log in</h2>
      <div class="form-group ${error != null ? 'has-error' : ''}">
        <span>${message}</span>
        <input name="email" type="text" class="form-control" placeholder="Username" autofocus="autofocus"
          value="admin.faridabad@konics.com" /> <input name="password" type="password" class="form-control"
          placeholder="Password" value="password" />
        <span>${error}</span>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
        <%--
        <h4 class="text-center">
          <a href="${contextPath}/registration">Create an account</a>
        </h4>
         --%>
      </div>
    </form>
  </div>
</body>
</html>