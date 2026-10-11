package com.example.student.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Course {

    private Long id;

    @NotBlank(message = "کد درس الزامی است")
    @Pattern(regexp = "[A-Za-z0-9-]{2,10}", message = "کد درس باید ۲ تا ۱۰ کاراکتر (حروف انگلیسی، عدد یا -) باشد")
    private String code;

    @NotBlank(message = "عنوان درس الزامی است")
    @Size(max = 100, message = "عنوان درس حداکثر ۱۰۰ کاراکتر است")
    private String title;

    @NotNull(message = "تعداد واحد الزامی است")
    @Min(value = 1, message = "تعداد واحد باید بین ۱ تا ۴ باشد")
    @Max(value = 4, message = "تعداد واحد باید بین ۱ تا ۴ باشد")
    private Integer units;

    public Course() {
    }

    public Course(Long id, String code, String title, Integer units) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.units = units;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public boolean isNew() {
        return id == null;
    }
}
