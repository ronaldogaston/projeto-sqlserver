package telas;

import java.net.URL;
import java.util.ResourceBundle;

import avisos.Restricoes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControladorCadastroGrupoProduto implements Initializable{

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtDescricao;

	@FXML
	private Label labelErrortxtDescricao;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancelar;

	@FXML
	public void onBtSalvarAction() {
		System.out.println("onBtSalvarAction");
	}

	@FXML
	public void onBtCancelarAction() {
		System.out.println("onBtCancelarAction");
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Restricoes.setTextFieldInteger(txtId);
		Restricoes.setTextFieldMaxLength(txtDescricao, 30);
	}
}
