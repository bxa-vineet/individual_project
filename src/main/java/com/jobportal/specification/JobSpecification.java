package com.jobportal.specification;

import com.jobportal.entity.Companies;
import com.jobportal.entity.Jobs;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class JobSpecification {

    public static Specification<Jobs> filterJobs(
            String keyword,
            String companyName,
            Integer minExp,
            Integer maxExp,
            Boolean fresher,
            Double minSalary,
            Double maxSalary
    ) {

        return (root, query, cb) -> {

            var predicates = cb.conjunction();

            if (keyword != null && !keyword.isEmpty()) {
                predicates = cb.and(predicates,
                        cb.like(cb.lower(root.get("title")),
                                "%" + keyword.toLowerCase() + "%"));
            }

            if (companyName != null && !companyName.isEmpty()) {
                Join<Jobs, Companies> companyJoin = root.join("company");

                predicates = cb.and(predicates,
                        cb.like(cb.lower(companyJoin.get("name")),
                                "%" + companyName.toLowerCase() + "%"));
            }

            if (minExp != null) {
                predicates = cb.and(predicates,
                        cb.greaterThanOrEqualTo(
                                cb.coalesce(root.get("experience"), 0),
                                minExp));
            }

            if (maxExp != null) {
                predicates = cb.and(predicates,
                        cb.lessThanOrEqualTo(
                                cb.coalesce(root.get("experience"), 0),
                                maxExp));
            }

            if (fresher != null) {
                predicates = cb.and(predicates,
                        cb.equal(
                                cb.coalesce(root.get("isFresher"), false),
                                fresher));
            }

            if (minSalary != null) {
                predicates = cb.and(predicates,
                        cb.greaterThanOrEqualTo(root.get("salary"), minSalary));
            }

            if (maxSalary != null) {
                predicates = cb.and(predicates,
                        cb.lessThanOrEqualTo(root.get("salary"), maxSalary));
            }

            return predicates;
        };
    }
}