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

<%-- Students enrolled in this course (many-to-many) --%>
<div class="section-head">
    <h2>دانشجویان ثبت‌نام شده</h2>
    <span class="badge">${students.size()} نفر</span>
</div>

<c:choose>
    <c:when test="${empty students}">
        <div class="card empty">هنوز دانشجویی این درس را اخذ نکرده است.</div>
    </c:when>
    <c:otherwise>
        <div class="table-wrap">
            <table>
                <thead>
                <tr>
                    <th>شماره دانشجویی</th>
                    <th>نام و نام خانوادگی</th>
                    <th>رشته تحصیلی</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="s" items="${students}">
                    <tr>
                        <td class="ltr"><c:out value="${s.studentNumber}"/></td>
                        <td><a href="<c:url value='/students/${s.id}'/>"><c:out value="${s.firstName} ${s.lastName}"/></a></td>
                        <td><c:out value="${empty s.major ? '-' : s.major}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<%@ include file="footer.jspf" %>
