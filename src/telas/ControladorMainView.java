package telas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import avisos.Alertas;
import entidades.servico.ServicoGrupoProduto;
import entidades.servico.ServicoProduto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class ControladorMainView implements Initializable {

	@FXML
	private MenuItem menuItemGrupoProduto;

	@FXML
	private MenuItem menuItemProduto;

	@FXML
	private MenuItem menuItemVersao;

	@FXML
	public void onMenuItemGrupoProdutoAcao() {
		loadView("/telas/GrupoProdutoLista.fxml", (ControladorGrupoProduto controller) -> {
			controller.setServicoGrupoProduto(new ServicoGrupoProduto());
			controller.updateTableView();
		});
	}

	@FXML
	public void onMenuItemProdutoAcao() {
		loadView("/telas/ProdutoLista.fxml", (ControladorProduto controller) -> {
			controller.setServicoProduto(new ServicoProduto());
			controller.updateTableView();
		});
	}

	@FXML
	public void onMenuItemVersaoAcao() {
		loadView("/telas/Versao.fxml", x -> {
		});
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}

	private synchronized <T> void loadView(String nomeAbsoluto, Consumer<T> initializingAction) { // Fun��o para abrir
																									// uma outra tela.
																									// synchronized =
																									// Garante que o
																									// processamento n�o
																									// ser� interrompido
																									// pelo Thread
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(nomeAbsoluto)); // Padr�o do m�todo '
																						// (getClass().getResource(nomeAbsoluto))
																						// '
			VBox newVBox = loader.load();

			Scene mainScene = Main.getMainScene();
			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

			Node mainMenu = mainVBox.getChildren().get(0);

			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVBox.getChildren());

			/*
			 * Com isso � poss�vel manipular a cena principal e incluir o Main Menu e os
			 * filhos da janela que est� sendo aberta pelo m�todo.
			 */
			T controller = loader.getController(); // Ir�o executar a fun��o que est� sendo passada como argumento no
													// m�todo 'onMenuItemDepartamentoAction' entre outros m�todos
			initializingAction.accept(controller); // Ir�o executar a fun��o que est� sendo passada como argumento no
													// m�todo 'onMenuItemDepartamentoAction' entre outros m�todos

		} catch (IOException e) {
			Alertas.showAlert("IO Exception", "Erro carregando a p�gina.", e.getMessage(), AlertType.ERROR);
		}
	}
}
