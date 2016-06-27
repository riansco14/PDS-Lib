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

import App.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import util.Dialogs;
import util.ValidationField;

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
	 * 
	 * @param url
	 * @param rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		cmbAdmin.getItems().addAll("Usuario", "Administrador");
	}

	@FXML
	private void actionLogin(ActionEvent event) throws IOException {
		if (cmbAdmin.getSelectionModel().getSelectedIndex() >= 0) {
			if (cmbAdmin.getSelectionModel().getSelectedItem().equals("Usuario")) {
				Dialogs.showInformation("Informação", "Função ainda não implementada", "");
			}
			if (cmbAdmin.getSelectionModel().getSelectedItem().equals("Administrador")) {
				if(ValidationField.isEmptyAllFields(fieldUserName,txtPass)) return;
				
				if(fieldUserName.getText().equals("admin") &&txtPass.getText().equals("admin"))
					Main.setSceneAdmin();
				else
					Dialogs.showError("Error", "Usuario ou Senha invalidos", "");
			}
		}
	}

	@FXML
	private void actionSair(ActionEvent event) {
		Platform.exit();
	}
}
