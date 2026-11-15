<%@ include file="header.jspf" %>

<%-- Evaluate a rule once and reuse the result (true/false) further down the page --%>
<sec:authorize access="hasRole('ADMIN')" var="isAdmin"/>
<%-- A STUDENT only ever reaches their own page (@PreAuthorize), so here they may pick courses too --%>
<sec:authorize access="hasAnyRole('ADMIN', 'STUDENT')" var="canEnroll"/>

<div class="page-head">
    <h1>مشخصات دانشجو</h1>
</div>

<c:if test="${not empty message}">
    <div class="alert alert-success"><c:out value="${message}"/></div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-error"><c:out value="${error}"/></div>
</c:if>

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
        <c:if test="${isAdmin}">
            <a class="btn btn-warning" href="<c:url value='/students/${student.id}/edit'/>">ویرایش</a>
            <form method="post" action="<c:url value='/students/${student.id}/delete'/>"
                  onsubmit="return confirm('آیا از حذف این دانشجو مطمئن هستید؟');">
                <sec:csrfInput/>
                <button type="submit" class="btn btn-danger">حذف</button>
            </form>
        </c:if>
        <sec:authorize access="hasAnyRole('ADMIN', 'VISITOR')">
            <a class="btn btn-link" href="<c:url value='/students'/>">بازگشت به فهرست</a>
        </sec:authorize>
    </div>
</div>

<%-- Enrolled courses (many-to-many) --%>
<div class="section-head">
    <h2>دروس اخذ شده</h2>
    <span class="badge">${totalUnits} از ${maxUnits} واحد</span>
</div>

<c:choose>
    <c:when test="${empty courses}">
        <div class="card empty">هنوز درسی اخذ نشده است.</div>
    </c:when>
    <c:otherwise>
        <div class="table-wrap">
            <table>
                <thead>
                <tr>
                    <th>کد درس</th>
                    <th>عنوان</th>
                    <th>واحد</th>
                    <c:if test="${canEnroll}"><th class="actions-col">عملیات</th></c:if>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="course" items="${courses}">
                    <tr>
                        <td class="ltr"><a href="<c:url value='/courses/${course.id}'/>"><c:out value="${course.code}"/></a></td>
                        <td><c:out value="${course.title}"/></td>
                        <td>${course.units}</td>
                        <c:if test="${canEnroll}">
                            <td class="actions">
                                <form method="post" action="<c:url value='/students/${student.id}/courses/${course.id}/delete'/>"
                                      onsubmit="return confirm('این درس از برنامه‌ی دانشجو حذف شود؟');">
                                    <sec:csrfInput/>
                                    <button type="submit" class="btn btn-sm btn-danger">حذف درس</button>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:otherwise>
</c:choose>

<c:if test="${canEnroll}">
    <c:choose>
        <c:when test="${not empty availableCourses}">
            <form class="card enroll" method="post" action="<c:url value='/students/${student.id}/courses'/>">
                <sec:csrfInput/>
                <label for="courseId">اخذ درس جدید:</label>
                <select id="courseId" name="courseId" required>
                    <option value="">انتخاب درس...</option>
                    <c:forEach var="course" items="${availableCourses}">
                        <option value="${course.id}"><c:out value="${course.code} - ${course.title} (${course.units} واحد)"/></option>
                    </c:forEach>
                </select>
                <button type="submit" class="btn btn-primary">اخذ درس</button>
            </form>
        </c:when>
        <c:otherwise>
            <p class="muted">درس دیگری برای اخذ وجود ندارد.
                <c:if test="${isAdmin}"><a href="<c:url value='/courses/new'/>">افزودن درس</a></c:if>
            </p>
        </c:otherwise>
    </c:choose>
</c:if>

<%@ include file="footer.jspf" %>
