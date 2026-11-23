package com.example.userapi.controller;

import com.example.userapi.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Simula un CRUD de usuarios. No hay base de datos: cada endpoint
 * escribe un mensaje por consola y devuelve una respuesta de ejemplo.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        log.info("Creando usuario: {}", user);
        User created = new User(1L, user.name(), user.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        log.info("Consultando usuario con id {}", id);
        return ResponseEntity.ok(new User(id, "Usuario " + id, "usuario" + id + "@example.com"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        log.info("Actualizando usuario con id {}: {}", id, user);
        return ResponseEntity.ok(new User(id, user.name(), user.email()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Eliminando usuario con id {}", id);
        return ResponseEntity.noContent().build();
    }
}
