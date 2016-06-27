package util;

public enum Location {
	TELA_LIVRO_CADASTRAR("/view/livroCadastrar.fxml"),
	TELA_LIVRO_CRUD("/view/LivroCRUD.fxml"),
	TELA_GERENCIADORA("/view/GerenciadorTela.fxml"),
	TELA_USER_CRUD("/view/userCRUD.fxml"),
	TELA_USER_LOGIN("/view/userLogin.fxml"),
	TELA_USER_CADASTRAR("/view/userCadastro.fxml"),
	TELA_EMPRESTIMO_INSERIR("/view/emprestimoInsert.fxml"),
	TELA_EMPRESTIMO_DEVOLVER("/view/emprestimoUpdate.fxml"),
	TELA_ESTOQUE_CRUD("/view/exemplarModificar.fxml"),
	ESTILO_CSS("/gambar/baju.css")
	
	;
	
	private String text;
	private Location(final String text){
		this.text = text;
	}
	
	public String toString(){
		return text;
	}
}
