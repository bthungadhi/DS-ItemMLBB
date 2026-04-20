package demo.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/")
	public String index(){
		return "Bryan Thungadhi Jaya";

	}@GetMapping("/user/{user_id}")
	public ResponseEntity<String> about(@PathVariable String user_id) {
		return ResponseEntity.ok ("<h1>User ID: " + user_id + "<h1>");
	}
}