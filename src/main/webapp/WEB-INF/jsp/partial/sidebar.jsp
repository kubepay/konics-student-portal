<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<div class="col-sm-3 col-md-2 sidebar">
  <ul class="nav nav-sidebar">
    <c:choose>
      <c:when test="${highlight == 'welcome'}">
        <li class="active"><a href="${contextPath}/welcome">Overview<span class="sr-only">(current)</span></a></li>
      </c:when>
      <c:otherwise>
        <li><a href="${contextPath}/welcome">Overview</a></li>
      </c:otherwise>
    </c:choose>

    <c:choose>
      <c:when test="${highlight == 'student'}">
        <li class="active"><a href="${contextPath}/student">Students<span class="sr-only">(current)</span></a></li>
      </c:when>
      <c:otherwise>
        <li><a href="${contextPath}/student">Students</a></li>
      </c:otherwise>
    </c:choose>

    <c:choose>
      <c:when test="${highlight == 'batch'}">
        <li class="active"><a href="${contextPath}/batch">Batch<span class="sr-only">(current)</span></a></li>
      </c:when>
      <c:otherwise>
        <li><a href="${contextPath}/batch">Batch</a></li>
      </c:otherwise>
    </c:choose>

    <c:choose>
      <c:when test="${highlight == 'exam'}">
        <li class="active"><a href="${contextPath}/exam">Exam<span class="sr-only">(current)</span></a></li>
      </c:when>
      <c:otherwise>
        <li><a href="${contextPath}/exam">Exam</a></li>
      </c:otherwise>
    </c:choose>
  </ul>
</div>
