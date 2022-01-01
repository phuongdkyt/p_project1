package com.example.demo.repositories.impl;

import com.example.demo.models.dto.MessageDTO;
import com.example.demo.models.dto.Page;
import com.example.demo.models.dto.Response;
import com.example.demo.models.dto.UserDTO;
import com.example.demo.models.entity.User;
import com.example.demo.repositories.UserCustomRepo;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.List;


public class UserCustomRepoImpl implements UserCustomRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ResponseEntity<MessageDTO<List<User>>> findAllUser() {
       TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u ", User.class);

        return Response.ok(query.getResultList());
    }

    @Override
    public ResponseEntity<MessageDTO<List<User>>> find100User() {
        Query query = entityManager.createNativeQuery("SELECT * from users limit 1000000", User.class);
        List<User> users = query.getResultList();
        return Response.ok(null);
    }

//    public <T> Page<T> getPaging(Query query, int page,int size) {
//        int startIndex = pag
//        query.setFirstResult(0);
//        query.setMaxResults(10);
//        List<T> users = query.getResultList();
//
//    }
}
