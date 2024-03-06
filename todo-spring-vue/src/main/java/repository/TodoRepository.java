package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import model.TodoTask;

public interface TodoRepository extends JpaRepository<TodoTask, Long> {
  List<TodoTask> findByPublished(boolean published);
  List<TodoTask> findByTitleContaining(String title);
}