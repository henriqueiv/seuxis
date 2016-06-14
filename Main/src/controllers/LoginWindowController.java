/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import models.User;
import dao.UserDAO;

/**
 * FXML Controller class
 *
 * @author Matheus
 */
public class LoginWindowController implements Initializable {

    @FXML
    private TextField cpfLoginTextField;
    @FXML
    private TextField cpfRegisterTextField;
    @FXML
    private TextField nameRegisterTextField;
    @FXML
    private TextField emailRegisterTextField;
    @FXML
    private Pane loginPane;
    @FXML
    private Pane registerPane;
    @FXML
    private ImageView logoLoginImageView;
    @FXML
    private ImageView logoRegisterImageView;
    private BufferedImage logo;

    UserDAO userDB = new UserDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        configureCpfTextField(cpfLoginTextField);
        configureCpfTextField(cpfRegisterTextField);
        try {
            logo = ImageIO.read(this.getClass().getResource("/resources/seu_xis.png"));
        } catch (IOException ex) {
            Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        logoLoginImageView.setImage(SwingFXUtils.toFXImage(logo, null));
        logoRegisterImageView.setImage(SwingFXUtils.toFXImage(logo, null));
    }

    @FXML
    private void enterWithoutLogin() {
        Stage loginWindow = (Stage) loginPane.getScene().getWindow();
        loginWindow.close();
        try {
            openUserWindow();
        } catch (IOException ex) {
            Logger.getLogger(LoginWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void enterWithLogin() {
        String cpf = cpfLoginTextField.getText().replaceAll("[\\-\\.]", "");

        if (cpf.length() < 11) {
            Alert alert = new Alert(AlertType.ERROR, "CPF deve conter 11 dígitos", ButtonType.OK);
            alert.show();
        } else {
            User user = userDB.getUserByCpf(cpf);
            if (user == null) {
                Alert alert = new Alert(AlertType.ERROR, "CPF não cadastrado", ButtonType.OK);
                alert.show();
                return;
            }
            
            user.setAsLoggedUser();
            if (!user.isManager())
                enterWithoutLogin();

        }

    }

    private void openUserWindow() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/UserWindow.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Seu Xis");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void newRegister() {
        loginPane.setVisible(false);
        registerPane.setVisible(true);
        cpfRegisterTextField.setText(cpfLoginTextField.getText());
    }
    
    @FXML
    private void backToLogin() {
        registerPane.setVisible(false);
        loginPane.setVisible(true);
    }

    @FXML
    private void register() {
        User user = new User();
        String cpf = cpfRegisterTextField.getText().replaceAll("[\\-\\.]", "");
        String name = nameRegisterTextField.getText();
        String email = emailRegisterTextField.getText();

        if (cpf.equals("") || name.equals("")) {
            Alert alert = new Alert(AlertType.ERROR, "Os campos 'CPF' e 'Nome', são obrigatórios", ButtonType.OK);
            alert.show();
        } else if (cpf.length() < 11) {
            Alert alert = new Alert(AlertType.ERROR, "CPF deve conter 11 dígitos", ButtonType.OK);
            alert.show();
        } else {

            user.setId(userDB.getMaxId() + 1);
            user.setCpf(cpf);
            user.setName(name);
            user.setEmail(email);
            user.setManager(false);
            
            if (userDB.getUserByCpf(user.getCpf()) != null) {
                Alert alert = new Alert(AlertType.INFORMATION, "CPF já cadastrado", ButtonType.OK);
                alert.show();
            } else {
                userDB.save(user);
                registerPane.setVisible(false);
                loginPane.setVisible(true);
                cpfLoginTextField.setText(cpfRegisterTextField.getText());
            }
        }

    }

    /**
     * Função para configurar um textfield com máscara de CPF
     *
     * @param tf textfield a ser configurado
     */
    private void configureCpfTextField(TextField tf) {

        tf.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            String valueDigits = tf.getText().replaceAll("[\\-\\.]", "");
            tf.endOfNextWord();
            if (valueDigits.matches("\\d*")) {
                tf.setText(newValue);
            } else {
                tf.setText(oldValue);
            }
        });

        tf.lengthProperty().addListener((ObservableValue<? extends Number> observableValue, Number number, Number number2) -> {
            String mascara = "###.###.###-##";
            String alphaAndDigits = tf.getText().replaceAll("[\\-\\.]", "");
            StringBuilder resultado = new StringBuilder();
            int i = 0;
            int quant = 0;

            if (number2.intValue() != number.intValue()) {
                if (tf.getText().length() <= mascara.length()) {
                    while (i < mascara.length()) {
                        if (quant < alphaAndDigits.length()) {
                            if ("#".equals(mascara.substring(i, i + 1))) {
                                resultado.append(alphaAndDigits.substring(quant, quant + 1));
                                quant++;
                            } else {
                                resultado.append(mascara.substring(i, i + 1));
                            }
                        }
                        i++;
                    }
                    tf.setText(resultado.toString());
                }
                if (tf.getText().length() > mascara.length()) {
                    tf.setText(tf.getText(0, mascara.length()));
                }
            }
        });
    }

}
