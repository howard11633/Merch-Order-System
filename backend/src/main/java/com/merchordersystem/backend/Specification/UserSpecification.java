package com.merchordersystem.backend.Specification;

import com.merchordersystem.backend.dto.user.UserQueryParams;
import com.merchordersystem.backend.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> build(UserQueryParams params){
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicates = new ArrayList<>();

                // 篩選條件：角色
                if (params.getRole() != null) {
                    predicates.add(cb.equal(root.get("role"), params.getRole()));
                }

                // 關鍵字查詢：姓名（模糊搜尋）
                if (params.getSearch() != null && !params.getSearch().isEmpty()) {
                    predicates.add(
                            cb.like(cb.lower(root.get("name")),
                                    "%" + params.getSearch().toLowerCase() + "%")
                    );
                }

                // 可以加入更多條件，像是日期篩選、狀態、email 等等...

                // 回傳 AND 條件組合（像 WHERE a AND b AND c）
                return cb.and(predicates.toArray(new Predicate[0]));
            }
        };

    }
}
