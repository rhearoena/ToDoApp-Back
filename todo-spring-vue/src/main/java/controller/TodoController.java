package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.TodoTask;
import repository.TodoRepository;


	@CrossOrigin(origins = "http://localhost:8081")
	@RestController
	@RequestMapping("/api")
	public class TodoController {

	  @Autowired
	  TodoRepository todoRepository;

	  @GetMapping("/todotasks")
	  public ResponseEntity<List<TodoTask>> getAllTodoTasks(@RequestParam(required = false) String title) {
		  try {
		      List<TodoTask> todotasks = new ArrayList<TodoTask>();

		      if (title == null)
		    	  todoRepository.findAll().forEach(todotasks::add);
		      else
		    	  todoRepository.findByTitleContaining(title).forEach(todotasks::add);

		      if (todotasks.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		      }

		      return new ResponseEntity<>(todotasks, HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }

	  }

	  @GetMapping("/todotasks/{id}")
	  public ResponseEntity<TodoTask> getTodoTaskById(@PathVariable("id") long id) {
	
		  Optional<TodoTask> tutorialData = todoRepository.findById(id);

		    if (tutorialData.isPresent()) {
		      return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		    } else {
		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
	  }

	  @PostMapping("/todotasks")
	  public ResponseEntity<TodoTask> createTodoTask(@RequestBody TodoTask todotask) {
		
		  try {
			  TodoTask _todotask = todoRepository
					  .save(new TodoTask(todotask.getTitle(), todotask.getDescription(), false));
		      return new ResponseEntity<>(_todotask, HttpStatus.CREATED);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	  }

	  @PutMapping("/todotasks/{id}")
	  public ResponseEntity<TodoTask> updateTodoTask(@PathVariable("id") long id, @RequestBody TodoTask todotask) {
	
		  Optional<TodoTask> todotaskData = todoRepository.findById(id);

		    if (todotaskData.isPresent()) {
		    	TodoTask _tutorial = todotaskData.get();
		      _tutorial.setTitle(todotask.getTitle());
		      _tutorial.setDescription(todotask.getDescription());
		      _tutorial.setPublished(todotask.isPublished());
		      return new ResponseEntity<>(todoRepository.save(_tutorial), HttpStatus.OK);
		    } else {
		      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
	  }

	  @DeleteMapping("/todotasks/{id}")
	  public ResponseEntity<HttpStatus> deleteTodoTasks(@PathVariable("id") long id) {
		  try {
			  todoRepository.deleteById(id);
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    } catch (Exception e) {
		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		  
	  }

	  @DeleteMapping("/todotasks")
	  public ResponseEntity<HttpStatus> deleteAllTodoTasks() {
		  try {
			  todoRepository.deleteAll();
		      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		    } catch (Exception e) {
		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	
	  }

	  @GetMapping("/todotasks/published")
	  public ResponseEntity<List<TodoTask>> findByPublished() {
		  try {
		      List<TodoTask> tutorials = todoRepository.findByPublished(true);

		      if (tutorials.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		      }
		      return new ResponseEntity<>(tutorials, HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	  }
	  
	  @GetMapping("/error")
	  public void ReturnError() {
	
	  }
	  

}
