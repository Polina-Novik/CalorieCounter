package by.novik.caloriecounter.controller;

import by.novik.caloriecounter.dto.ProfileResponse;
import by.novik.caloriecounter.jwt.JwtTokenUtil;
import by.novik.caloriecounter.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

@Tag(name = "profile controller", description = "controller for viewing and editing profile")
@AllArgsConstructor
@RestController
@RequestMapping("profile")
@SecurityRequirement(name = "JWT")
public class ProfileController {
    private final JwtTokenUtil tokenUtil;
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Show profile", description = "this method shows personal information",
            responses = {@ApiResponse(responseCode = "200",
                    description = "personal information",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema,
                            examples = @ExampleObject(value = """
                                    {
                                      "login": "login",
                                      "height": 180,
                                      "weight": 80,
                                      "age": 20,
                                      "gender": "male",
                                      "activityLevel": "moderate"
                                    }"""))}),
                    @ApiResponse(responseCode = "404",
                            description = "profile not found", content = @Content)
            })
    public ProfileResponse showPersonalInformation(HttpSession session) {
        System.out.println(tokenUtil.getLoginFromToken((String) session.getAttribute("token")));
        return userService.showPersonalInformation(tokenUtil.getLoginFromToken((String)
                session.getAttribute("token")));
    }


    @PutMapping
    @Operation(summary = "Update profile", description = "this method updates personal information",
            responses = {@ApiResponse(responseCode = "200",
                    description = "updated personal information",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema,
                            examples = @ExampleObject(value = """
                                    {
                                      "login": "login",
                                      "height": 180,
                                      "weight": 80,
                                      "age": 20,
                                      "gender": "male",
                                      "activityLevel": "moderate"
                                    }"""))}),
                    @ApiResponse(responseCode = "404",
                            description = "profile not found", content = @Content)
            })
    public ProfileResponse editProfile(HttpSession session, @Parameter(name = "height",
            description = "new height", required = true) @RequestParam int height,
                                       @Parameter(name = "weight", description = "new weight",
                                               required = true) @RequestParam double weight,
                                       @Parameter(name = "age", description = "new age", required = true)
                                       @RequestParam int age, @Parameter(name = "gender", description =
            "new gender", required = true) @RequestParam String gender, @Parameter(name = "activityLevel",
            description = "new activity level", required = true) @RequestParam String activityLevel) {
        return userService.editProfile(tokenUtil.getLoginFromToken((String)
                session.getAttribute("token")), height, weight, age, gender, activityLevel);
    }
}
