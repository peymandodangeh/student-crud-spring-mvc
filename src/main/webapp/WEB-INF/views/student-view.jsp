<%@ include file="header.jspf" %>

<div class="page-head">
    <h1>مشخصات دانشجو</h1>
</div>

<div class="card">
    <dl class="details">
        <dt>شماره دانشجویی</dt>
        <dd class="ltr"><c:out value="${student.studentNumber}"/></dd>
        <dt>نام</dt>
        <dd><c:out value="${student.firstName}"/></dd>
        <dt>نام خانوادگی</dt>
        <dd><c:out value="${student.lastName}"/></dd>
        <dt>ایمیل</dt>
        <dd class="ltr"><c:out value="${student.email}"/></dd>
        <dt>رشته تحصیلی</dt>
        <dd><c:out value="${empty student.major ? '-' : student.major}"/></dd>
    </dl>

    <div class="form-actions">
        <a class="btn btn-warning" href="<c:url value='/students/${student.id}/edit'/>">ویرایش</a>
        <form method="post" action="<c:url value='/students/${student.id}/delete'/>"
              onsubmit="return confirm('آیا از حذف این دانشجو مطمئن هستید؟');">
            <button type="submit" class="btn btn-danger">حذف</button>
        </form>
        <a class="btn btn-link" href="<c:url value='/students'/>">بازگشت به فهرست</a>
    </div>
</div>

<%@ include file="footer.jspf" %>
