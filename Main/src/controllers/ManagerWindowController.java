package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import models.Ingredient;
import models.Menu;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javax.imageio.ImageIO;

public class ManagerWindowController implements Initializable {

    private Menu menu;
    
    @FXML private VBox editPanel;
    @FXML private TextField ingredientNameField, ingredientPriceField;
    @FXML private TextArea ingredientDescriptionField;
    
    @FXML private ListView<Ingredient> ingredientListView;
    @FXML private ImageView ingredientImageView;
    @FXML private CheckBox ingredientCheckBox;
    @FXML private TabPane tabPane;
    
    
    private Ingredient selectedIngredient;
    private BufferedImage img;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        menu = new Menu();
        editPanel.setVisible(false);
        
        ingredientDescriptionField.setWrapText(true);
                
        ingredientListView.setItems(menu.getIngredients());
        
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
	selectedIngredient = menu.createNewIngredient();
		
	ingredientListView.getSelectionModel().select(selectedIngredient);
        updateEditingPane();
    }
    
    @FXML
    private void resetImage() throws IOException {
	selectedIngredient.resetImg();
        updateEditingPane();
    }
    
    @FXML
    private void deleteIngredient() {
	menu.removeIngredient(selectedIngredient);
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
    
    @FXML
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
