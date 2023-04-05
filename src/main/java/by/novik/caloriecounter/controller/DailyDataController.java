package by.novik.caloriecounter.controller;

import by.novik.caloriecounter.dto.DailyDataResponse;

import by.novik.caloriecounter.jwt.JwtTokenUtil;
import by.novik.caloriecounter.service.CalculationService;
import by.novik.caloriecounter.service.DailyDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "daily data controller", description = "food and activity diary controller")
@AllArgsConstructor
@RestController
@RequestMapping("calorie_counter")
@SecurityRequirement(name = "JWT")
public class DailyDataController {

    private final DailyDataService dailyDataService;

    private final JwtTokenUtil tokenUtil;

    private final CalculationService calculationService;

    @PostMapping
    @Operation(summary = "Create daily data", description = "this method creates new daily data",
            responses = {@ApiResponse(responseCode = "200",
                    description = "new daily data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DailyDataResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "date": "2023-04-03",
                                      "consumedCalories": 0,
                                      "foods": [
                                        null
                                      ],
                                      "activities": [
                                        null
                                      ],
                                      "burnedCalories": 0,
                                      "weight": 60
                                    }"""))})})
    public DailyDataResponse createDailyData(@Parameter(name = "year", description = "year", required = true)
                                             @RequestParam @Min(1970) int year,
                                             @Parameter(name = "month", description = "month", required = true)
                                             @RequestParam @Min(1) @Max(12) int month,
                                             @Parameter(name = "day", description = "day", required = true)
                                             @RequestParam @Min(1) @Max(31) int day, HttpSession session) {
        String login = tokenUtil.getLoginFromToken((String) session.getAttribute("token"));
        return dailyDataService.createDailyData(login, year, month, day);
    }


    @PostMapping("{dailyDataId}/food/{foodId}")
    @Operation(summary = "Add food", description = "this method adds food to daily data",
            responses = {@ApiResponse(responseCode = "200",
                    description = "updated daily data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DailyDataResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "date": "2023-04-03",
                                      "consumedCalories": 2000,
                                      "foods": [
                                        "string"
                                      ],
                                      "activities": [
                                        "string"
                                      ],
                                      "burnedCalories": 2100,
                                      "weight": 60
                                    }"""))}),
                    @ApiResponse(responseCode = "404",
                            description = "daily data not found", content = @Content)
            })
    public DailyDataResponse addFoodToDailyData(@Parameter(name = "dailyDataId", description =
            "id of daily data", required = true) @PathVariable Long dailyDataId, @Parameter(name = "foodId",
            description = "id of food", required = true) @PathVariable Long foodId,
                                                @Parameter(name = "grams", description =
                                                        "food weight in grams", required = true)
                                                @RequestParam double grams, HttpSession session) {
        String login = tokenUtil.getLoginFromToken((String) session.getAttribute("token"));
        return dailyDataService.addFoodToDailyData(login, dailyDataId, foodId, grams);
    }

    @DeleteMapping("{dailyDataId}/delete/{foodIndex}")
    @Operation(summary = "Delete food", description = "this method deletes food from daily data",
            responses = {@ApiResponse(responseCode = "200",
                    description = "updated daily data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DailyDataResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "date": "2023-04-03",
                                      "consumedCalories": 2000,
                                      "foods": [
                                        "string"
                                      ],
                                      "activities": [
                                        "string"
                                      ],
                                      "burnedCalories": 2100,
                                      "weight": 60
                                    }"""))}),
                    @ApiResponse(responseCode = "404",
                            description = "daily data not found", content = @Content)
            })
    public DailyDataResponse deleteFoodFromDailyData(@Parameter(name = "dailyDataId", description =
            "id of daily data", required = true) @PathVariable Long dailyDataId,
                                                     @Parameter(name = "foodIndex", description =
                                                             "index of the product in list to be deleted",
                                                             required = true) @PathVariable int foodIndex,
                                                     HttpSession session) {
        String login = tokenUtil.getLoginFromToken((String) session.getAttribute("token"));
        return dailyDataService.deleteFoodFromDailyData(login, dailyDataId, foodIndex);
    }

    @PostMapping("{dailyDataId}/activity/{activityId}")
    @Operation(summary = "Add activity", description = "this method adds activity to daily data",
            responses = {@ApiResponse(responseCode = "200",
                    description = "updated daily data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DailyDataResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "date": "2023-04-03",
                                      "consumedCalories": 2000,
                                      "foods": [
                                        "string"
                                      ],
                                      "activities": [
                                        "string"
                                      ],
                                      "burnedCalories": 2100,
                                      "weight": 60
                                    }"""))}),
                    @ApiResponse(responseCode = "404",
                            description = "daily data not found", content = @Content)
            })
    public DailyDataResponse addActivityToDailyData(@Parameter(name = "dailyDataId", description =
            "id of daily data", required = true) @PathVariable Long dailyDataId,
                                                    @Parameter(name = "activityId", description =
                                                            "id of activity", required = true)
                                                    @PathVariable Long activityId,
                                                    @Parameter(name = "minutes", description =
                                                            "minutes of activity", required = true)
                                                    @RequestParam int minutes, HttpSession session) {
        String login = tokenUtil.getLoginFromToken((String) session.getAttribute("token"));
        return dailyDataService.addActivityToDailyData(login, dailyDataId, activityId, minutes);
    }

    @DeleteMapping("{dailyDataId}/activity/{activityIndex}")
    @Operation(summary = "Delete activity", description = "this method deletes activity from daily data",
            responses = {@ApiResponse(responseCode = "200",
                    description = "updated daily data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DailyDataResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "date": "2023-04-03",
                                      "consumedCalories": 2000,
                                      "foods": [
                                        "string"
                                      ],
                                      "activities": [
                                        "string"
                                      ],
                                      "burnedCalories": 2100,
                                      "weight": 60
                                    }"""))}),
                    @ApiResponse(responseCode = "404",
                            description = "daily data not found", content = @Content)
            })
    public DailyDataResponse deleteActivityFromDailyData(@Parameter(name = "dailyDataId", description =
            "id of daily data", required = true) @PathVariable Long dailyDataId,
                                                         @Parameter(name = "activityIndex", description =
                                                                 "index of the activity in list to be deleted",
                                                                 required = true)
                                                         @PathVariable int activityIndex,
                                                         HttpSession session) {
        String login = tokenUtil.getLoginFromToken((String) session.getAttribute("token"));
        return dailyDataService.deleteActivityFromDailyData(login, dailyDataId, activityIndex);
    }


    @GetMapping("{id}")
    @Operation(summary = "Find daily data", description = "this method finds daily data by daily data id",
            responses = {@ApiResponse(responseCode = "200",
                    description = "daily data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DailyDataResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "date": "2023-04-03",
                                      "consumedCalories": 2000,
                                      "foods": [
                                        "string"
                                      ],
                                      "activities": [
                                        "string"
                                      ],
                                      "burnedCalories": 2100,
                                      "weight": 60
                                    }"""))}),
                    @ApiResponse(responseCode = "404",
                            description = "daily data not found", content = @Content)
            })
    public DailyDataResponse getDailyDataForUser(@Parameter(name = "id", description = "id of daily data",
            required = true) @PathVariable Long id, HttpSession session) {
        return dailyDataService.findResponseByUserNameAndId(tokenUtil.getLoginFromToken((String) session
                .getAttribute("token")), id);
    }

    @GetMapping
    @Operation(summary = "Find all daily data", description = "this method finds all daily for user",
            responses = {@ApiResponse(responseCode = "200",
                    description = "List of daily data",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DailyDataResponse.class)))}),
            })
    public List<DailyDataResponse> getDataForUser(HttpSession session) {
        return dailyDataService.findByUserName(tokenUtil.getLoginFromToken((String)
                session.getAttribute("token")));
    }

    @GetMapping("calorie_needs")
    @Operation(summary = "Calculate calorie needs", description =
            "this method calculates daily calorie needs for user",
            responses = {@ApiResponse(responseCode = "200",
                    description = "daily calorie needs",
                    content = {@Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = "2100")},
                            schema = @Schema(type = "integer"))})
            })
    public double calculateDailyCalorieNeeds(HttpSession session) {
        return calculationService.calculateDailyCalorieNeeds(tokenUtil.getLoginFromToken((String)
                session.getAttribute("token")));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete daily data", description = "Delete daily data by id",
            responses = {@ApiResponse(responseCode = "204", description = "Daily data successfully deleted")})
    public void delete(@Parameter(name = "id", description = "id of daily data", required = true)
                       @PathVariable Long id) {
        dailyDataService.deleteById(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update weight",
            description = "this method updates weight of user in daily data and profile",
            responses = {@ApiResponse(responseCode = "200",
                    description = "updated daily data",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DailyDataResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "date": "2023-04-03",
                                      "consumedCalories": 2000,
                                      "foods": [
                                        "string"
                                      ],
                                      "activities": [
                                        "string"
                                      ],
                                      "burnedCalories": 2100,
                                      "weight": 60
                                    }"""))}),
                    @ApiResponse(responseCode = "404",
                            description = "daily data not found", content = @Content)
            })
    public DailyDataResponse updateWeight(@Parameter(name = "id", description =
            "id of daily data", required = true) @PathVariable Long id, HttpSession session,
                                          @Parameter(name = "weight", description = "new weight",
                                                  required = true) @RequestParam double weight) {
        return dailyDataService.updateWeight(tokenUtil.getLoginFromToken((String)
                session.getAttribute("token")), id, weight);
    }
}
