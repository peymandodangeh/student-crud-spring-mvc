<%@ include file="header.jspf" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:choose>
    <c:when test="${course['new']}">
        <c:set var="title" value="افزودن درس جدید"/>
        <c:url var="action" value="/courses"/>
    </c:when>
    <c:otherwise>
        <c:set var="title" value="ویرایش درس"/>
        <c:url var="action" value="/courses/${course.id}"/>
    </c:otherwise>
</c:choose>

<div class="page-head">
    <h1>${title}</h1>
</div>

<form:form modelAttribute="course" method="post" action="${action}" cssClass="card form">
    <div class="grid">
        <div class="field">
            <form:label path="code">کد درس *</form:label>
            <form:input path="code" cssClass="ltr" cssErrorClass="ltr invalid" placeholder="CS101"/>
            <form:errors path="code" cssClass="error"/>
        </div>
        <div class="field">
            <form:label path="units">تعداد واحد *</form:label>
            <form:select path="units" cssErrorClass="invalid">
                <form:option value="" label="انتخاب کنید"/>
                <form:option value="1"/>
                <form:option value="2"/>
                <form:option value="3"/>
                <form:option value="4"/>
            </form:select>
            <form:errors path="units" cssClass="error"/>
        </div>
        <div class="field full">
            <form:label path="title">عنوان درس *</form:label>
            <form:input path="title" cssErrorClass="invalid"/>
            <form:errors path="title" cssClass="error"/>
        </div>
    </div>

    <div class="form-actions">
        <button type="submit" class="btn btn-primary">ذخیره</button>
        <a class="btn btn-link" href="<c:url value='/courses'/>">انصراف</a>
    </div>
</form:form>

<%@ include file="footer.jspf" %>
