package by.novik.caloriecounter.controller;

import by.novik.caloriecounter.entity.Activity;
import by.novik.caloriecounter.service.ActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "activity controller", description = "controller for working with activities")
@RestController
@RequestMapping("activities")
@SecurityRequirement(name = "JWT")
@AllArgsConstructor
public class ActivityController {

    private final ActivityService activityService;


    @GetMapping("list")
    @Operation(summary = "Find all activities", description = "this method returns all activities",
            responses = @ApiResponse(responseCode = "200",
                    description = "List of activities",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Activity.class)))}))
    public List<Activity> getActivities() {
        return activityService.findAll();
    }

    @GetMapping("find/{id}")
    @Operation(summary = "Find activity by id", description = "this method returns activity if present",
            responses = {@ApiResponse(responseCode = "200",
                    description = "needed activity",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Activity.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "name": "running",
                                      "caloriesBurnedPerMinute": 7
                                    }"""))}),
                    @ApiResponse(responseCode = "404",
                            description = "activity not found", content = @Content)
            })
    public Activity findById(@Parameter(name = "id", description = "activity id", required = true)
                             @PathVariable Long id) {
        return activityService.findById(id);
    }


    @PostMapping("add")
    @Operation(summary = "Create activity", description = "This method creates a new activity.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List with new activity",
                            content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Activity.class)))})
            })
    public List<Activity> add(@Parameter(name = "name", description = "new activity name", required = true)
                              @RequestParam String name, @Parameter(name = "calories",
            description = "number of calories burned per minute of activity", required = true)
                              @RequestParam double calories) {
        activityService.add(name, calories);
        return activityService.findAll();
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Delete activity", description = "this method deletes activity if present",
            responses = {@ApiResponse(responseCode = "200",
                    description = "list with deleted activity",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Activity.class)))})})
    public List<Activity> delete(@Parameter(name = "id", description = "activity id", required = true)
                                 @PathVariable Long id) {
        activityService.deleteById(id);
        return activityService.findAll();
    }

    @PutMapping("update/{id}")
    @Operation(summary = "Update activity", description = "this method updates activity if present",
            responses = {@ApiResponse(responseCode = "200",
                    description = "list with updated activity",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Activity.class)))}),
                    @ApiResponse(responseCode = "404",
                            description = "activity not found", content = @Content)
            })
    public List<Activity> update(@Parameter(name = "id", description = "activity id", required = true)
                                 @PathVariable Long id, @Parameter(name = "name", description =
            "activity name", required = true) @RequestParam String name,
                                 @Parameter(name = "calories", description = "number of calories burned " +
                                         "per minute of activity", required = true) @RequestParam
                                 double calories) {
        activityService.updateActivity(id, name, calories);
        return activityService.findAll();
    }
}
