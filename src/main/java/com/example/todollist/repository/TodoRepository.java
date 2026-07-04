package com.example.todollist.repository;

import com.example.todollist.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query("SELECT t FROM Todo t WHERE (:completed IS NULL OR t.completed = :completed) " +
           "AND (:title IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :title, '%')))")
    Page<Todo> findTodosByFilter(@Param("completed") Boolean completed, @Param("title") String title, Pageable pageable);
}
