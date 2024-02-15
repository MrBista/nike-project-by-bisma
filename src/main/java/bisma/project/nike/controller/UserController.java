package bisma.project.nike.controller;

import bisma.project.nike.dto.request.SignInRequestDTO;
import bisma.project.nike.dto.request.SignUpRequestDTO;
import bisma.project.nike.dto.response.CommonResponse;
import bisma.project.nike.model.ERole;
import bisma.project.nike.model.Role;
import bisma.project.nike.model.User;
import bisma.project.nike.repository.RoleRepository;
import bisma.project.nike.repository.UserRepository;
import bisma.project.nike.services.auth.JwtUtils;
import bisma.project.nike.services.auth.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
//    @GetMapping
//    public

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ResponseEntity<Object> userSignUp(@Valid @RequestBody SignUpRequestDTO signUpRequestDTO) {
        if (userRepository.existsByEmail(signUpRequestDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"email already exits");
        }
        if (userRepository.existsByUsername(signUpRequestDTO.getUsername())) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"username already exits");
        }


        User createUser = new User();
        createUser.setEmail(signUpRequestDTO.getEmail());
        createUser.setUsername(signUpRequestDTO.getUsername());
        createUser.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
        createUser.setLastName(signUpRequestDTO.getLastName());
        createUser.setFirstName(signUpRequestDTO.getFirstName());

        List<String> roles = signUpRequestDTO.getRoles();
        Set<Role> createRoles = new HashSet<>();
        if (signUpRequestDTO.getRoles() == null) {
            Role role = roleRepository.findByRoleName(ERole.USER).orElseThrow(() -> new RuntimeException("role not found"));
            createRoles.add(role);
        } else {
            roles.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "superAdmin":
                        Role superAdmin = roleRepository.findByRoleName(ERole.SUPER_ADMIN).orElseThrow(() -> new RuntimeException("Role is not found"));
                        createRoles.add(superAdmin);
                        break;
                    case "admin":
                        Role admin = roleRepository.findByRoleName(ERole.ADMIN).orElseThrow(() -> new RuntimeException("Role is not found"));
                        createRoles.add(admin);
                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(ERole.USER).orElseThrow(() -> new RuntimeException("Role is not found"));
                        createRoles.add(userRole);
                        break;

                }
            });
        }
        createUser.setRoles(createRoles);

        User user = userRepository.save(createUser);
        Map<String, Object> resData = new HashMap<>();
        resData.put("id", user.getId());
        resData.put("username", user.getUsername());
        resData.put("email", user.getEmail());
        resData.put("firstName", user.getFirstName());
        resData.put("lastName", user.getLastName());


        return CommonResponse.generateResponse(resData, "succesfully create user", HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<Object>userLogin(@Valid @RequestBody SignInRequestDTO signInRequestDTO) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(signInRequestDTO.getEmail() != null ? signInRequestDTO.getEmail() : signInRequestDTO.getUsername(), signInRequestDTO.getPassword()));
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        String generatedToken = jwtUtils.generateJwtToken(authentication);

        Map<String, Object> resData = new HashMap<>();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        resData.put("id", userDetails.getId());
        resData.put("username", userDetails.getUsername());
        resData.put("email", userDetails.getEmail());
        resData.put("roles", userDetails.getAuthorities().stream().map(val -> val.getAuthority()).collect(Collectors.toList()));
        resData.put("token", generatedToken);
        return CommonResponse.generateResponse(resData, "Successfully login", HttpStatus.OK) ;
    }


}
