package by.novik.caloriecounter.controller;


import by.novik.caloriecounter.dto.DailyDataResponse;
import by.novik.caloriecounter.dto.ProfileEditResponse;
import by.novik.caloriecounter.dto.ProfileResponse;
import by.novik.caloriecounter.entity.User;
import by.novik.caloriecounter.jwt.JwtTokenUtil;
import by.novik.caloriecounter.service.CalculationService;
import by.novik.caloriecounter.service.DailyDataService;
import by.novik.caloriecounter.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@AllArgsConstructor

public class HTMLController {
    private final JwtTokenUtil tokenUtil;
    private final UserService userService;

    private final DailyDataService dailyDataService;


    private final CalculationService calculationService;

    @GetMapping("home")
    public String home(Model model, HttpSession session) {
        String login = tokenUtil.getLoginFromToken((String) session.getAttribute("token"));

        model.addAttribute("login", login);
        return "home";
    }

    @GetMapping("information")
    public String showPersonalInformation(HttpSession session, Model model) {
        System.out.println(tokenUtil.getLoginFromToken((String) session.getAttribute("token")));
        ProfileResponse profileResponse = userService.showPersonalInformation(tokenUtil
                .getLoginFromToken((String)
                        session.getAttribute("token")));
        model.addAttribute("person", profileResponse);
        return "information";
    }

    @GetMapping("edit_profile")
    public String showForm() {
        return "edit_profile";
    }

    @PostMapping("edit_profile")
    public String editProfile(@Valid ProfileEditResponse formUser,
                              HttpSession session) {
        String login = tokenUtil.getLoginFromToken((String) session.getAttribute("token"));
        User user = userService.findByLogin(login).orElseThrow();
        user.setHeight(formUser.getHeight());
        user.setWeight(formUser.getWeight());
        user.setAge(formUser.getAge());
        user.setGender(formUser.getGender());
        user.setActivityLevel(formUser.getActivityLevel());
        userService.save(user);
        return "redirect:/information";
    }

    @GetMapping("diary")
    public String getDataForUser(HttpSession session, Model model) {
        String login = tokenUtil.getLoginFromToken((String) session.getAttribute("token"));
        List<DailyDataResponse> dailyDataResponses = dailyDataService.findByUserName(login);
        model.addAttribute("dailyDataResponses", dailyDataResponses);
        double dailyCalorieNeeds = calculationService.calculateDailyCalorieNeeds(login);
        model.addAttribute("dailyCalorieNeeds", dailyCalorieNeeds);
        return "diary";
    }

    @GetMapping("daily_statistics/{id}")
    public String getDailyDataForUser(@PathVariable Long id, HttpSession session, Model model) {
        DailyDataResponse dailyDataResponse = dailyDataService.findResponseByUserNameAndId(tokenUtil
                .getLoginFromToken((String) session.getAttribute("token")), id);
        model.addAttribute("dailyDataResponse", dailyDataResponse);
        return "daily_statistics";
    }

    @ModelAttribute(name = "user")
    public ProfileEditResponse getNewUser() {
        return new ProfileEditResponse();
    }


}
