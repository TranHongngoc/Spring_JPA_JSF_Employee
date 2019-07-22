package vn.vissoft.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import vn.vissoft.employee.model.User;
import vn.vissoft.employee.service.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    @Autowired
    public UserService userService;

    //API trả về List User.
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> accounts = userService.findAll();
        if (accounts.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(accounts, HttpStatus.OK);
    }

    //API trả về User có ID trên url.
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        System.out.println("Fetching User with id " + id);
        User account = userService.findById(id);
        if (account == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(account, HttpStatus.OK);
    }

    //API tạo một User mới.
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + user.getUsername());
        userService.create(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }


    //API cập nhật một User với ID trên url.
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<User> updateAdmin(@PathVariable("id") long id, @RequestBody User user) {
        System.out.println("Updating User " + id);

        User curremUser = userService.findById(id);

        if (curremUser == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        curremUser.setUsername(user.getUsername());
        curremUser.setPassword(user.getPassword());
        curremUser.setId(user.getId());

        userService.update(curremUser);
        return new ResponseEntity<User>(curremUser, HttpStatus.OK);
    }

    //API xóa một User với ID trên url.
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
        System.out.println("Fetching & Deleting User with id " + id);

        User user = userService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        userService.delete(user);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

//    @RequestMapping(value = "/usersname/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<User> getAvatarByName(@PathVariable("id") String id) {
//        System.out.println("Fetching User with name " + id);
//        User account = userService.findByName(id);
//        if (account == null) {
//            System.out.println("User with name " + id + " not found");
//            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<User>(account, HttpStatus.OK);
//    }
}
