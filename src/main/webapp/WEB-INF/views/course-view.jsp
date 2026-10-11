<%@ include file="header.jspf" %>

<div class="page-head">
    <h1>مشخصات درس</h1>
</div>

<div class="card">
    <dl class="details">
        <dt>کد درس</dt>
        <dd class="ltr"><c:out value="${course.code}"/></dd>
        <dt>عنوان</dt>
        <dd><c:out value="${course.title}"/></dd>
        <dt>تعداد واحد</dt>
        <dd>${course.units}</dd>
    </dl>

    <div class="form-actions">
        <a class="btn btn-warning" href="<c:url value='/courses/${course.id}/edit'/>">ویرایش</a>
        <form method="post" action="<c:url value='/courses/${course.id}/delete'/>"
              onsubmit="return confirm('با حذف درس، ثبت‌نام دانشجویان در آن هم حذف می‌شود. ادامه می‌دهید؟');">
            <button type="submit" class="btn btn-danger">حذف</button>
        </form>
        <a class="btn btn-link" href="<c:url value='/courses'/>">بازگشت به فهرست</a>
    </div>
</div>

<%@ include file="footer.jspf" %>
