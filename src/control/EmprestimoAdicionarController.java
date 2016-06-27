package control;

import java.net.URL;
import java.util.ResourceBundle;

import DAO.UsuarioDAO;
import VO.BibliotecaNegocio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Usuario;

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
						System.out.println("retirar");
					
				}
			}
		else System.out.println("nao");

	}

}
