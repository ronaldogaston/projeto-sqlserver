package telas;

import java.net.URL;
import java.util.ResourceBundle;

import avisos.Restricoes;
import entidades.negocio.GrupoProduto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ControladorCadastroGrupoProduto implements Initializable{
	
	private GrupoProduto entity;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtDescGrupo;

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
	
	public void setGrupoProduto (GrupoProduto entity) {
		this.entity = entity;
	}
	
	public void updateDados() {
		if (entity == null) {
			throw new IllegalStateException("GrupoProduto nulo!");
		}
		txtId.setText(String.valueOf(entity.getId()));
		txtDescGrupo.setText(entity.getDescGrupo());
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Restricoes.setTextFieldInteger(txtId);
		Restricoes.setTextFieldMaxLength(txtDescGrupo, 30);
	}
}
