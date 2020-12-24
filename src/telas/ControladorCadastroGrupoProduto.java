package telas;

import java.net.URL;
import java.util.ResourceBundle;

import avisos.Alertas;
import avisos.Restricoes;
import db.ExcecoesDB;
import entidades.negocio.GrupoProduto;
import entidades.servico.ServicoGrupoProduto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utilitario.Utils;

public class ControladorCadastroGrupoProduto implements Initializable{
	
	private GrupoProduto entity;
	
	private ServicoGrupoProduto srvGrupoProduto;

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

	public void setServicoGrupoProduto (ServicoGrupoProduto srvGrupoProduto) {
		this.srvGrupoProduto = srvGrupoProduto;
	}
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		System.out.println("onBtSalvarAction");			if (entity == null) {
			throw new IllegalStateException("Entity está nulo");
		}
		if (srvGrupoProduto == null) {
			throw new IllegalStateException("srvGrupoProduto está nulo");
		}
		try {
			entity = getFormData();
			srvGrupoProduto.inserirOuAtualizarGrupoProduto(entity);
			Utils.currentStage(event).close();
		}
		catch (ExcecoesDB e) {
			Alertas.showAlert("Erro ao salvar departamento", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private GrupoProduto getFormData() {
		GrupoProduto grp = new GrupoProduto();

		grp.setId(Utils.tryParseToInt(txtId.getText())); // Verifica se o campo está preenchido com número inteiro
		grp.setDescGrupo(txtDescGrupo.getText());

		return grp;
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		System.out.println("onBtCancelarAction");			
		Utils.currentStage(event).close();
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
