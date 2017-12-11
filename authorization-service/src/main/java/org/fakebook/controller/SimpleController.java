package org.fakebook.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

  @GetMapping("/random-name")
  public ResponseEntity<Map> getRandomName() {
    Map<String, String> map = new HashMap<>();
    map.put("name", "Casey");
    return ResponseEntity.ok(map);
  }
}
