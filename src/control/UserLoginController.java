/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import DAO.AutenticManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author herudi
 */
public class UserLoginController implements Initializable {
    @FXML
    private TextField fieldUserName;
    @FXML
    private PasswordField txtPass;
    @FXML
    private ComboBox<String> cmbAdmin;
    Stage stage;
    

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbAdmin.getItems().addAll("Usuario","Administrador");
    }    

    @FXML
    private void actionLogin(ActionEvent event) throws IOException {
    	if(cmbAdmin.getSelectionModel().getSelectedIndex()>=0)
    	{
    		if(cmbAdmin.getSelectionModel().getSelectedItem().equals("Usuario"))
    	{
    		
    		if(new AutenticManager().autenticarUsuario(Long.parseLong(fieldUserName.getText()), txtPass.getText()))
    			JOptionPane.showMessageDialog(null, "Autenticado com sucesso");
    		else
    			JOptionPane.showMessageDialog(null, "Algum campo está incorreto");
    	}
    	}
    }
    private Pane loadMainPane() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream("/userLogin.fxml"));
        return mainPane;
        
    }
    
    private Scene createScene(Pane mainPane) {
        Scene scene = new Scene(mainPane);
        scene.getStylesheets().setAll(getClass().getResource("/gambar/baju.css").toExternalForm());        
        return scene;
    }
    
    @FXML
    private void actionSair(ActionEvent event){
        Platform.exit();
    }
}
