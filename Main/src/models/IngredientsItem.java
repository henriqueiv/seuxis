/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author valcanaia
 */
public class IngredientsItem {
    
    private int id;
    private Ingredient ingredient;
    private Item item;

    public IngredientsItem() {
    }

    public IngredientsItem(int id, Ingredient ingredient, Item Item) {
        this.id = id;
        this.ingredient = ingredient;
        this.item = Item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item Item) {
        this.item = Item;
    }
    
}
