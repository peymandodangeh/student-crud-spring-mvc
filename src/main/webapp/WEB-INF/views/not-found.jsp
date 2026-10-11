<%@ include file="header.jspf" %>

<div class="card empty">
    <h1><c:out value="${empty notFoundTitle ? 'دانشجو یافت نشد' : notFoundTitle}"/></h1>
    <p>مورد درخواستی وجود ندارد یا حذف شده است.</p>
    <a class="btn btn-primary" href="<c:url value='${empty backUrl ? "/students" : backUrl}'/>">بازگشت به فهرست</a>
</div>

<%@ include file="footer.jspf" %>
