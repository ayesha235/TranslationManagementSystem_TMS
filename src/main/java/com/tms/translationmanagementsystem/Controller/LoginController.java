package com.tms.translationmanagementsystem.Controller;

import com.tms.translationmanagementsystem.Dto.LoginRequest;
import com.tms.translationmanagementsystem.Feature.CustomUserDetailsService;
import com.tms.translationmanagementsystem.Utils.JwtUtil;
import com.tms.translationmanagementsystem.Utils.Response;
import com.tms.translationmanagementsystem.Utils.ResponseCodesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tms")
@CrossOrigin
@RequiredArgsConstructor
public class LoginController {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<Response<?>> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate user
            if (userDetailsService.authenticate(loginRequest.getUsername(), loginRequest.getPassword())) {
                // Load user details
                UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

                // Generate token
                String token = jwtUtil.generateToken(userDetails);

                // Create response
                Map<String, Object> tokenResponse = new HashMap<>();
                tokenResponse.put("token", token);
                tokenResponse.put("username", userDetails.getUsername());
                tokenResponse.put("message", "Login successful");

                Response<?> response= new Response<>(ResponseCodesEnum.SUCCESS.status(),getClass().getName(),ResponseCodesEnum.SUCCESS.code(),tokenResponse);

                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("message", "Invalid credentials");
                Response<?> response= new Response<>(ResponseCodesEnum.UNAUTHORIZED.status(),getClass().getName(),ResponseCodesEnum.UNAUTHORIZED.code(),errorResponse);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            Response<?> response= new Response<>(ResponseCodesEnum.UNAUTHORIZED.status(),getClass().getName(),ResponseCodesEnum.UNAUTHORIZED.code(),null);
            return ResponseEntity.badRequest().body(response);
        }
    }
}