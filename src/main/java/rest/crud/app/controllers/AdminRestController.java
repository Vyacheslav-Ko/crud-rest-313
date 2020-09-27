package rest.crud.app.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.crud.app.model.User;
import rest.crud.app.service.UserDetailsServiceAdded;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE) //, produces = MediaType.APPLICATION_JSON_VALUE
public class AdminRestController {

    public static final String REST_URL = "/admin/api/users";
    private final UserDetailsServiceAdded userDetailsServiceAdded;

    public AdminRestController(UserDetailsServiceAdded userDetailsServiceAdded) {
        this.userDetailsServiceAdded = userDetailsServiceAdded;
    }

    @PostMapping(value = "/")
    public ResponseEntity<User> newUser(@RequestBody @Valid User user) {
        HttpHeaders hh = new HttpHeaders();
        userDetailsServiceAdded.addUser(user);
        System.out.println(user);
        return new ResponseEntity<>(user, hh, HttpStatus.CREATED);
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<User>> allUsersList() {
        return new ResponseEntity<>(userDetailsServiceAdded.getAllUsers(), HttpStatus.FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<>(userDetailsServiceAdded.findById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userDetailsServiceAdded.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        userDetailsServiceAdded.removeUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}