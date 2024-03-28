package com.fishb0ness.autoawaremonitor.adapter.input.user;

import com.fishb0ness.autoawaremonitor.application.UserUseCases;
import com.fishb0ness.autoawaremonitor.domain.user.UserId;
import com.fishb0ness.autoawaremonitor.domain.user.UserName;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserUseCases userUseCase;

    public UserController(UserUseCases userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping("/users")
    public UserDTO postUser(@RequestBody UserName userName) {
        return UserDTO.fromDomain(userUseCase.createUser(userName));
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> getById(@RequestParam(value = "id") String id) {
        Optional<UserDTO> userDto = userUseCase.getUserById(new UserId(UUID.fromString(id))).map(UserDTO::fromDomain);
        return userDto.map(it -> ResponseEntity.ok(it)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public List<UserDTO> getAll() {
        return userUseCase.getAllUsers().stream().map(UserDTO::fromDomain).collect(Collectors.toList());
    }
}
