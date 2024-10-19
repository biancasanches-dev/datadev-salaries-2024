package org.example.model;

public record DevSalary (
        int year,
        String experience_level,
        String employment_type,
        String job_title,
        int salary,
        String salary_currency,
        int salary_in_usd,
        String employee_residence,
        int remote_ratio,
        String company_location,
        String company_size
) { }
