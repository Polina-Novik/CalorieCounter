package by.novik.caloriecounter.controller;


import by.novik.caloriecounter.entity.Food;
import by.novik.caloriecounter.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "food controller", description = "controller for working with products")
@RestController
@RequiredArgsConstructor
@RequestMapping("foods")
@SecurityRequirement(name = "JWT")
public class FoodController {

    private final FoodService foodService;

    @GetMapping("list")
    @Operation(summary = "Find all foods", description = "this method returns all products",
            responses = @ApiResponse(responseCode = "200",
                    description = "List of foods",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Food.class)))}))
    public List<Food> getAllFoods() {
        return foodService.findAll();
    }

    @GetMapping("find/{id}")
    @Operation(summary = "Find food by id", description = "this method returns food if present",
            responses = {@ApiResponse(responseCode = "200",
                    description = "needed food",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Food.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "id": 1,
                                      "name": "Pineapple",
                                      "calories": 50
                                    }"""))}),
                    @ApiResponse(responseCode = "404",
                            description = "food not found", content = @Content)
            })
    public Food getFoodById(@Parameter(name = "id", description = "food id", required = true)
                            @PathVariable Long id) {
        return foodService.findById(id);
    }

    @PostMapping("add")
    @Operation(summary = "Create food", description = "this method creates new food",
            responses = {@ApiResponse(responseCode = "200",
                    description = "list with new food",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Food.class)))})})
    public List<Food> add(@Parameter(name = "name", description = "new food name", required = true)
                          @RequestParam String name, @Parameter(name = "calories", description =
            "number of calories per 100 grams", required = true) @RequestParam double calories) {
        foodService.add(name, calories);
        return foodService.findAll();
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Delete food", description = "this method deletes food if present",
            responses = {@ApiResponse(responseCode = "200",
                    description = "list with deleted food",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Food.class)))})})
    public List<Food> delete(@Parameter(name = "id", description = "food id", required = true)
                             @PathVariable Long id) {
        foodService.deleteById(id);
        return foodService.findAll();
    }

    @PutMapping("update/{id}")
    @Operation(summary = "Update food", description = "this method updates food if present",
            responses = {@ApiResponse(responseCode = "200",
                    description = "list with updated food",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Food.class)))}),
                    @ApiResponse(responseCode = "404",
                            description = "food not found", content = @Content)
            })
    public List<Food> update(@Parameter(name = "id", description = "food id", required = true)
                             @PathVariable Long id, @Parameter(name = "name", description = "food name",
            required = true) @RequestParam String name, @Parameter(name = "calories", description =
            "number of calories per 100 grams", required = true) @RequestParam double calories) {
        foodService.update(id, name, calories);
        return foodService.findAll();
    }
}
