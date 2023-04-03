package by.novik.caloriecounter.controller;

import by.novik.caloriecounter.dto.AuthRequest;
import by.novik.caloriecounter.dto.AuthResponse;
import by.novik.caloriecounter.dto.RegisterAuthRequest;
import by.novik.caloriecounter.entity.User;
import by.novik.caloriecounter.jwt.JwtTokenUtil;
import by.novik.caloriecounter.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "authentication controller", description = "controller for login and registration")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil tokenUtil;

    @PostMapping("registration")
    @Operation(summary = "Registration", description = "this method is for registration",
            responses = {@ApiResponse(responseCode = "200",
                    description = "you are you signed up")
            })
    public void createUser(@RequestBody RegisterAuthRequest request) {
        userService.save(request);
    }

    @PostMapping("login")
    @Operation(summary = "Authorization", description = "this method is for authorization",
            responses = {@ApiResponse(responseCode = "200",
                    description = "you are logged in"),
                    @ApiResponse(responseCode = "404",
                            description = "account not found", content = @Content)
            })
    public AuthResponse login(@RequestBody AuthRequest authRequest, HttpSession session) {
        User user = userService.getTokenForUserIfExists(authRequest);
        session.setAttribute("token", tokenUtil.generateToken(user.getLogin()));
        return new AuthResponse(tokenUtil.generateToken(user.getLogin()));
    }
}
