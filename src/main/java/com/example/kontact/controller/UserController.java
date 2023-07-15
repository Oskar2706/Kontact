package com.example.kontact.controller;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @PostMapping
    public ResponseEntity<User> registration(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO dto) {
        User user = userService.findByUsername(dto.getUsername());
        String token = jwtTokenProvider.generateToken(dto.getUsername(), user.getRoles());
        if (bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().build();
    }
}