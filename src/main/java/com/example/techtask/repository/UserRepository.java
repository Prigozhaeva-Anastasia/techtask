package com.example.techtask.repository;

import com.example.techtask.model.User;
import com.example.techtask.model.enumiration.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("""
        SELECT u
        FROM User u
        JOIN u.orders o
        WHERE YEAR(o.createdAt) = :year and o.orderStatus = 'DELIVERED'
        GROUP BY u.id
        ORDER BY SUM(o.price * o.quantity) DESC
        LIMIT 1
    """)
    User findUserWithMaxSumOfDeliveredOrdersInYear(@Param("year") int year);

    @Query("""
        SELECT u
        FROM User u
        JOIN u.orders o
        WHERE YEAR(o.createdAt) = :year and o.orderStatus = 'PAID'
    """)
    List<User> findUsersWithPaidOrdersInYear(@Param("year") int year);
}
