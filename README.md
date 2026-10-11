# Student CRUD — Spring MVC + JSP

پروژه‌ی نمونه‌ی **Spring MVC** با عملیات CRUD (ایجاد، مشاهده، ویرایش، حذف) روی موجودیت دانشجو و رابط کاربری **JSP** (راست‌به‌چپ، فارسی).

## تکنولوژی‌ها

| بخش | ابزار |
|-----|-------|
| وب | Spring MVC 6.1 (پیکربندی Java-based، بدون Spring Boot) |
| نما | JSP + JSTL 3 + Spring Form Tags |
| داده | Spring JDBC (`JdbcTemplate`) + پایگاه‌داده‌ی H2 (فایلی) |
| اعتبارسنجی | Jakarta Bean Validation (Hibernate Validator) |
| اجرا | Jetty 12 (`jetty-ee10-maven-plugin`) — یا هر Servlet 6 container مثل Tomcat 10.1+ |
| ساخت | Maven، Java 17+ |

## اجرا

```bash
mvn jetty:run
```

سپس در مرورگر باز کنید: <http://localhost:8080/students>

برای ساخت فایل WAR (قابل استقرار روی Tomcat 10.1+):

```bash
mvn package
```

خروجی: `target/student-crud.war`

> داده‌ها در پایگاه‌داده‌ی H2 در مسیر `~/student-crud-data/` ذخیره می‌شوند و پس از راه‌اندازی مجدد باقی می‌مانند.

## ساختار پروژه

```
src/main/java/com/example/student
├── config/
│   ├── WebAppInitializer.java   # جایگزین web.xml: ثبت DispatcherServlet و فیلتر UTF-8
│   ├── AppConfig.java           # DataSource، JdbcTemplate، تراکنش‌ها
│   └── WebConfig.java           # @EnableWebMvc، ViewResolver برای JSP
├── model/Student.java           # مدل + قواعد اعتبارسنجی
├── repository/StudentRepository.java  # دسترسی به داده با JdbcTemplate
├── service/StudentService.java        # لایه‌ی سرویس
└── controller/StudentController.java  # کنترلر CRUD
src/main/resources/schema.sql          # ساخت جدول student
src/main/webapp/WEB-INF/views/         # صفحات JSP
src/main/webapp/resources/css/         # استایل
```

## مسیرها (Endpoints)

| متد | مسیر | عملیات |
|-----|------|--------|
| GET | `/students?q=...` | فهرست و جستجوی دانشجویان |
| GET | `/students/{id}` | مشاهده‌ی جزئیات |
| GET | `/students/new` | فرم افزودن |
| POST | `/students` | ثبت دانشجوی جدید |
| GET | `/students/{id}/edit` | فرم ویرایش |
| POST | `/students/{id}` | ذخیره‌ی ویرایش |
| POST | `/students/{id}/delete` | حذف |
| GET | `/courses` | فهرست دروس |
| GET | `/courses/{id}` | مشاهده‌ی درس و دانشجویان ثبت‌نام‌شده |
| GET/POST | `/courses/new`، `/courses` | افزودن درس |
| GET/POST | `/courses/{id}/edit`، `/courses/{id}` | ویرایش درس |
| POST | `/courses/{id}/delete` | حذف درس |
| POST | `/students/{id}/courses` | اخذ درس (پارامتر `courseId`) |
| POST | `/students/{id}/courses/{courseId}/delete` | حذف درس از برنامه‌ی دانشجو |

## جلسات

| جلسه | برنچ | موضوع |
|------|------|-------|
| ۱ | `main` | CRUD دانشجو با Spring MVC و JSP |
| ۳ | `session-03` | افزودن درس و رابطه‌ی چندبه‌چند — [طرح درس](docs/session-03.md) |
