package com.savvycom.controller;

import com.savvycom.entity.Role;
import com.savvycom.entity.RoleStatus;
import com.savvycom.entity.User;
import com.savvycom.jwt.JwtUtils;
import com.savvycom.payload.request.LoginRequest;
import com.savvycom.payload.request.SignupRequest;
import com.savvycom.payload.response.JwtResponse;
import com.savvycom.payload.response.MessageResponse;
import com.savvycom.repository.RoleRepository;
import com.savvycom.repository.UserRepository;
import com.savvycom.services.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lam.le
 * @created 29/09/2021
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleStatus.ROLE_DM)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "hr":
                        Role hrRole = roleRepository.findByName(RoleStatus.ROLE_HR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(hrRole);

                        break;
                    case "sale":
                        Role saleRole = roleRepository.findByName(RoleStatus.ROLE_SALE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(saleRole);

                        break;
                    case "dm":
                        Role dmRole = roleRepository.findByName(RoleStatus.ROLE_DM)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(dmRole);

                        break;
                    case "pm":
                        Role pmRole = roleRepository.findByName(RoleStatus.ROLE_PM)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(pmRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(RoleStatus.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
