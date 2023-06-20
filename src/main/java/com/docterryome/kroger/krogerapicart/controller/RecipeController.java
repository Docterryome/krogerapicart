package com.docterryome.kroger.krogerapicart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.docterryome.kroger.krogerapicart.dao.Recipe;
import com.docterryome.kroger.krogerapicart.dao.RecipeRepository;

@RestController
public class RecipeController {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository){
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/recipe")
    public ResponseEntity<List<Recipe>> getRecipes(){
        List<Recipe> recipes = this.recipeRepository.findAll();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @PostMapping("/recipe/add")
    public ResponseEntity<?> addRecipe(@RequestBody Recipe recipe){
        recipeRepository.save(recipe);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }     
    
}