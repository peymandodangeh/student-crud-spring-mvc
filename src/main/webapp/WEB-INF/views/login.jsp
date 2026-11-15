<%@ include file="header.jspf" %>

<div class="card login">
    <h1>ورود به سامانه</h1>

    <c:if test="${param.error != null}">
        <div class="alert alert-error">نام کاربری یا رمز عبور اشتباه است</div>
    </c:if>
    <c:if test="${param.logout != null}">
        <div class="alert alert-success">با موفقیت خارج شدید</div>
    </c:if>

    <%-- Spring Security processes POST /login itself; field names must be username and password --%>
    <form method="post" action="<c:url value='/login'/>">
        <sec:csrfInput/>
        <div class="field">
            <label for="username">نام کاربری</label>
            <input type="text" id="username" name="username" class="ltr" autocomplete="username" required autofocus>
        </div>
        <div class="field">
            <label for="password">رمز عبور</label>
            <input type="password" id="password" name="password" class="ltr" autocomplete="current-password" required>
        </div>
        <button type="submit" class="btn btn-primary btn-block">ورود</button>
    </form>
    <p class="muted">دانشجویان: نام کاربری و رمز عبور اولیه، شماره دانشجویی است.</p>
</div>

<%@ include file="footer.jspf" %>
