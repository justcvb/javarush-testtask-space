package com.space.specification;

import com.space.model.Ship;
import com.space.model.ShipType;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
import java.util.*;

public class ShipSpecification implements Specification<Ship> {

    private Map<String, String> params;

    public ShipSpecification(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();

        if (params.containsKey("name")) {
            predicates.add(
                    cb.like(root.get("name"), "%" + params.get("name") + "%")
            );
        }
        if (params.containsKey("planet")) {
            predicates.add(
                    cb.like(root.get("planet"), "%" + params.get("planet") + "%")
            );
        }
        if (params.containsKey("shipType")) {
            predicates.add(
                    cb.equal(root.get("shipType"), ShipType.valueOf(params.get("shipType")))
            );
        }
        if (params.containsKey("after")) {
            predicates.add(
                    cb.greaterThanOrEqualTo(root.get("prodDate"), new Date(Long.parseLong(params.get("after"))))
            );
        }
        if (params.containsKey("before")) {
            predicates.add(
                    cb.lessThanOrEqualTo(root.get("prodDate"), new Date(Long.parseLong(params.get("before"))))
            );
        }
        if (params.containsKey("isUsed")) {
            predicates.add(
                    cb.equal(root.get("isUsed"), Boolean.parseBoolean(params.get("isUsed")))
            );
        }
        if (params.containsKey("minSpeed")) {
            predicates.add(
                    cb.greaterThanOrEqualTo(root.get("speed"), Double.parseDouble(params.get("minSpeed")))
            );
        }
        if (params.containsKey("maxSpeed")) {
            predicates.add(
                    cb.lessThanOrEqualTo(root.get("speed"), Double.parseDouble(params.get("maxSpeed")))
            );
        }
        if (params.containsKey("minCrewSize")) {
            predicates.add(
                    cb.greaterThanOrEqualTo(root.get("crewSize"), Integer.parseInt(params.get("minCrewSize")))
            );
        }
        if (params.containsKey("maxCrewSize")) {
            predicates.add(
                    cb.lessThanOrEqualTo(root.get("crewSize"), Integer.parseInt(params.get("maxCrewSize")))
            );
        }
        if (params.containsKey("minRating")) {
            predicates.add(
                    cb.greaterThanOrEqualTo(root.get("rating"), Double.parseDouble(params.get("minRating")))
            );
        }
        if (params.containsKey("maxRating")) {
            predicates.add(
                    cb.lessThanOrEqualTo(root.get("rating"), Double.parseDouble(params.get("maxRating")))
            );
        }

        return cb.and(predicates.toArray(new Predicate[0]));

    }

}
