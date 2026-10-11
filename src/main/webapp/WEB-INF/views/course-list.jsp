<%@ include file="header.jspf" %>

<div class="page-head">
    <h1>فهرست دروس</h1>
    <a class="btn btn-primary" href="<c:url value='/courses/new'/>">+ افزودن درس</a>
</div>

<c:if test="${not empty message}">
    <div class="alert alert-success"><c:out value="${message}"/></div>
</c:if>

<c:choose>
    <c:when test="${empty courses}">
        <div class="empty">هنوز درسی ثبت نشده است.</div>
    </c:when>
    <c:otherwise>
        <div class="table-wrap">
            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th>کد درس</th>
                    <th>عنوان</th>
                    <th>تعداد واحد</th>
                    <th class="actions-col">عملیات</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="course" items="${courses}" varStatus="st">
                    <tr>
                        <td>${st.count}</td>
                        <td class="ltr"><c:out value="${course.code}"/></td>
                        <td><c:out value="${course.title}"/></td>
                        <td>${course.units}</td>
                        <td class="actions">
                            <a class="btn btn-sm" href="<c:url value='/courses/${course.id}'/>">مشاهده</a>
                            <a class="btn btn-sm btn-warning" href="<c:url value='/courses/${course.id}/edit'/>">ویرایش</a>
                            <form method="post" action="<c:url value='/courses/${course.id}/delete'/>"
                                  onsubmit="return confirm('با حذف درس، ثبت‌نام دانشجویان در آن هم حذف می‌شود. ادامه می‌دهید؟');">
                                <button type="submit" class="btn btn-sm btn-danger">حذف</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <p class="muted">تعداد: ${courses.size()}</p>
    </c:otherwise>
</c:choose>

<%@ include file="footer.jspf" %>
