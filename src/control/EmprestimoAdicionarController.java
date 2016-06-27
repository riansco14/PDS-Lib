package control;

import java.net.URL;
import java.util.ResourceBundle;

import DAO.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Usuario;
import negocio.BibliotecaNegocio;
import util.Dialogs;
import util.ValidationField;

public class EmprestimoAdicionarController implements Initializable {

	@FXML
	private PasswordField fieldPassword;

	@FXML
	private TextField fieldISBN;

	@FXML
	private TextField fieldCPF;

	BibliotecaNegocio negocio = new BibliotecaNegocio();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	void actionEmprestimo(ActionEvent event) {
		boolean fieldIsEmpty=ValidationField.isEmptyAllFields(fieldCPF,fieldISBN,fieldPassword);
		if(fieldIsEmpty) return;
		
			String isbn = fieldISBN.getText();
			String password = fieldPassword.getText();
			long idUsuario = Long.parseLong(fieldCPF.getText());

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Usuario usuario = usuarioDAO.localizar(idUsuario);
			if (negocio.buscarQuantidade(isbn) > 0)
				if (usuario == null) {

				} else {
					if (usuario.getSenha().equals(password)) {
						  if(negocio.retirarLivro(isbn, idUsuario))
							   Dialogs.showInformation("Transação realizada com sucesso", "Informação", "Emprestimo realizado com sucesso");
						   else 
							   Dialogs.showError("Houve algum problema na Transação", "Error", "Emprestimo não foi realizado");
					}
				}
			else 
				Dialogs.showInformation("Informação", "Ops...", "Infelizmente não temos exemplares disponiveis para emprestimo");

		}
		
	

}
