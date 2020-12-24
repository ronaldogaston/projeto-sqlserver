package telas;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import entidades.negocio.GrupoProduto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ControladorGrupoProduto implements Initializable{

	@FXML
	private TableView<GrupoProduto> tableViewGrupoProduto; // Tipo TableView

	@FXML
	private TableColumn<GrupoProduto, Integer> colunaId; // Tipo Coluna. OBS: Lembrando que só declarar o mesmo não faz com que funcione. Verifique o método 'initialize (URL uri, ResourceBundle rb)'

	@FXML
	private TableColumn<GrupoProduto, Integer> colunaDescGrupo; // Tipo Coluna. OBS: Lembrando que só declarar o mesmo não faz com que funcione. Verifique o método 'initialize (URL uri, ResourceBundle rb)'

	@FXML
	private Button btNew; // Tipo Botão

	@FXML
	public void onBtNewAction() { // Ação que ocorrerá após o botão 'Novo' ser clicado
		System.out.println("onBtNewAction");
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		InitializeNodes(); // Macete para funcionar um componente da tela. Veja o método 'InitializeNodes()'
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
