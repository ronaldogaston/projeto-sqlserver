package telas;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import entidades.negocio.GrupoProduto;
import entidades.servico.ServicoGrupoProduto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControladorGrupoProduto implements Initializable{
	
	private ServicoGrupoProduto service; // Injetar a depe�ncia sem colocar a implementa��o 'new ServicoGrupoProduto'

	@FXML
	private TableView<GrupoProduto> tableViewGrupoProduto; // Tipo TableView

	@FXML
	private TableColumn<GrupoProduto, Integer> colunaId; // Tipo Coluna. OBS: Lembrando que s� declarar o mesmo n�o faz com que funcione. Verifique o m�todo 'initialize (URL uri, ResourceBundle rb)'

	@FXML
	private TableColumn<GrupoProduto, Integer> colunaDescGrupo; // Tipo Coluna. OBS: Lembrando que s� declarar o mesmo n�o faz com que funcione. Verifique o m�todo 'initialize (URL uri, ResourceBundle rb)'

	@FXML
	private Button btNew; // Tipo Bot�o
	
	private ObservableList<GrupoProduto> obsList;

	@FXML
	public void onBtNewAction() { // A��o que ocorrer� ap�s o bot�o 'Novo' ser clicado
		System.out.println("onBtNewAction");
	}
	
	public void setServicoGrupoProduto(ServicoGrupoProduto service) {
		this.service = service;
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		InitializeNodes(); // Macete para funcionar um componente da tela. Veja o m�todo 'InitializeNodes()'
	}
	
	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Servi�o est� nulo.");
		}
		List<GrupoProduto> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewGrupoProduto.setItems(obsList);
	}

	private void InitializeNodes() {
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id")); // Comando para iniciar apropriadamento o comportamento da coluna na tabela
		colunaDescGrupo.setCellValueFactory(new PropertyValueFactory<>("descGrupo")); // Comando para iniciar apropriadamento o comportamento da coluna na tabela

		/*
		 * Comando para a tabela acompanhar a altura da janela
		 * ABAIXO 
		 */

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewGrupoProduto.prefHeightProperty().bind(stage.heightProperty());
	}
}
