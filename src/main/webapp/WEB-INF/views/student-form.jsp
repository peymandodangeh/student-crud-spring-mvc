<%@ include file="header.jspf" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:choose>
    <c:when test="${student['new']}">
        <c:set var="title" value="افزودن دانشجوی جدید"/>
        <c:url var="action" value="/students"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="ویرایش دانشجو"/>
        <c:url var="action" value="/students/${student.id}"/>
    </c:otherwise>
</c:choose>

<div class="page-head">
    <h1>${title}</h1>
</div>

<form:form modelAttribute="student" method="post" action="${action}" cssClass="card form">
    <div class="grid">
        <div class="field">
            <form:label path="firstName">نام *</form:label>
            <form:input path="firstName" cssErrorClass="invalid"/>
            <form:errors path="firstName" cssClass="error"/>
        </div>
        <div class="field">
            <form:label path="lastName">نام خانوادگی *</form:label>
            <form:input path="lastName" cssErrorClass="invalid"/>
            <form:errors path="lastName" cssClass="error"/>
        </div>
        <div class="field">
            <form:label path="studentNumber">شماره دانشجویی *</form:label>
            <form:input path="studentNumber" cssClass="ltr" cssErrorClass="ltr invalid" inputmode="numeric"/>
            <form:errors path="studentNumber" cssClass="error"/>
        </div>
        <div class="field">
            <form:label path="email">ایمیل *</form:label>
            <form:input path="email" type="email" cssClass="ltr" cssErrorClass="ltr invalid"/>
            <form:errors path="email" cssClass="error"/>
        </div>
        <div class="field full">
            <form:label path="major">رشته تحصیلی</form:label>
            <form:input path="major" cssErrorClass="invalid"/>
            <form:errors path="major" cssClass="error"/>
        </div>
    </div>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">ذخیره</button>
        <a class="btn btn-link" href="<c:url value='/students'/>">انصراف</a>
    </div>
</form:form>

<%@ include file="footer.jspf" %>
