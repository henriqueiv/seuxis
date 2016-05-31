/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Matheus
 */
public class Session {
    
    private ObservableList<Ingredient> ingredients;
    private int qtdIngredients = 1;
   
    
    
    public Session() {
	ingredients = FXCollections.observableArrayList();
    }
    
    public ObservableList<Ingredient> getIngredients() {
	return ingredients;
    }
	
    public Ingredient createNewIngredient() throws IOException {
	Ingredient i = new Ingredient("Ingrediente " + qtdIngredients);
	qtdIngredients++;
        
	ingredients.add(i);
	return i;
    }
    
    public void removeIngredient(Ingredient i) {
        ingredients.remove(i);
    }
    
}
