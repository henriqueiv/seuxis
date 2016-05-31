/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.transform.Affine;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import models.Ingredient;
import models.Session;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javafx.scene.control.TabPane;
import javax.imageio.ImageIO;

public class MainWindowController implements Initializable {

    private Session session;
    
    @FXML private VBox editPanel;
    @FXML private TextField ingredientNameField, ingredientPriceField, ingredientDescriptionField;
    @FXML private ListView<Ingredient> ingredientListView;
    @FXML private ImageView ingredientImageView;
    @FXML private CheckBox ingredientCheckBox;
    @FXML private TabPane tabPane;
    
    
    private Ingredient selectedIngredient;
    private BufferedImage img;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        session = new Session();
        editPanel.setVisible(false);

        ingredientListView.setItems(session.getIngredients());
        
        ingredientListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedIngredient = newValue;			
            updateEditingPane();
            ingredientListView.scrollTo(selectedIngredient);
        });
        
        ingredientNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedIngredient != null) {
                selectedIngredient.setName(newValue);
                ingredientListView.refresh();
            }
        });
        
        ingredientPriceField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedIngredient != null) {
                selectedIngredient.setPrice(Double.parseDouble(newValue));
            }
        });
        
        ingredientDescriptionField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedIngredient != null) {
                selectedIngredient.setDescription(newValue);
            }
        });
        
        ingredientCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (selectedIngredient != null) {
                selectedIngredient.setAvailable(newValue);
            }
        });
    }

    @FXML
    private void createIngredient() throws IOException {
	selectedIngredient = session.createNewIngredient();
		
	ingredientListView.getSelectionModel().select(selectedIngredient);
    }
    
    @FXML
    private void resetImage() throws IOException {
	selectedIngredient.resetImg();
        updateEditingPane();
    }
    
    @FXML
    private void deleteIngredient() {
	session.removeIngredient(selectedIngredient);
    }
    
    @FXML
    private void selectImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Simulation File");
	fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); 
	fileChooser.getExtensionFilters().add(new ExtensionFilter("JPG ou JPEG", "*.jpg", "*.jpeg", "*.JPG", "*.JPEG"));
		
	File f = fileChooser.showOpenDialog(tabPane.getScene().getWindow());
		
	if (f != null) {
            img = ImageIO.read(f);
            selectedIngredient.setImg(img);
        }
        updateEditingPane();
    }
    
    private void updateEditingPane() {
	editPanel.setVisible(selectedIngredient != null);
		
	if (selectedIngredient == null)
            return;
		
	ingredientNameField.setText(selectedIngredient.getName());
        ingredientPriceField.setText(String.valueOf(selectedIngredient.getPrice()));
        ingredientDescriptionField.setText(selectedIngredient.getDescription());
        ingredientImageView.setImage(SwingFXUtils.toFXImage(selectedIngredient.getImg(), null));
        ingredientCheckBox.setSelected(selectedIngredient.isAvailable());

    }
	
    
}
