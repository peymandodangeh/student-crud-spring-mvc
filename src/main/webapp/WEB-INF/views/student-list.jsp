<%@ include file="header.jspf" %>

<div class="page-head">
    <h1>فهرست دانشجویان</h1>
    <a class="btn btn-primary" href="<c:url value='/students/new'/>">+ افزودن دانشجو</a>
</div>

<c:if test="${not empty message}">
    <div class="alert alert-success"><c:out value="${message}"/></div>
</c:if>

<form class="search" method="get" action="<c:url value='/students'/>">
    <input type="text" name="q" value="<c:out value='${q}'/>" placeholder="جستجو بر اساس نام، شماره دانشجویی، ایمیل یا رشته...">
    <button type="submit" class="btn">جستجو</button>
    <c:if test="${not empty q}">
        <a class="btn btn-link" href="<c:url value='/students'/>">پاک کردن</a>
    </c:if>
</form>

<c:choose>
    <c:when test="${empty students}">
        <div class="empty">
            <c:choose>
                <c:when test="${not empty q}">نتیجه‌ای برای «<c:out value="${q}"/>» یافت نشد.</c:when>
                <c:otherwise>هنوز دانشجویی ثبت نشده است.</c:otherwise>
            </c:choose>
        </div>
    </c:when>
    <c:otherwise>
        <div class="table-wrap">
            <table>
                <thead>
                <tr>
                    <th>#</th>
                    <th>شماره دانشجویی</th>
                    <th>نام</th>
                    <th>نام خانوادگی</th>
                    <th>ایمیل</th>
                    <th>رشته تحصیلی</th>
                    <th class="actions-col">عملیات</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="s" items="${students}" varStatus="st">
                    <tr>
                        <td>${st.count}</td>
                        <td class="ltr"><c:out value="${s.studentNumber}"/></td>
                        <td><c:out value="${s.firstName}"/></td>
                        <td><c:out value="${s.lastName}"/></td>
                        <td class="ltr"><c:out value="${s.email}"/></td>
                        <td><c:out value="${empty s.major ? '-' : s.major}"/></td>
                        <td class="actions">
                            <a class="btn btn-sm" href="<c:url value='/students/${s.id}'/>">مشاهده</a>
                            <a class="btn btn-sm btn-warning" href="<c:url value='/students/${s.id}/edit'/>">ویرایش</a>
                            <form method="post" action="<c:url value='/students/${s.id}/delete'/>"
                                  onsubmit="return confirm('آیا از حذف این دانشجو مطمئن هستید؟');">
                                <button type="submit" class="btn btn-sm btn-danger">حذف</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <p class="muted">تعداد: ${students.size()}</p>
    </c:otherwise>
</c:choose>

<%@ include file="footer.jspf" %>
