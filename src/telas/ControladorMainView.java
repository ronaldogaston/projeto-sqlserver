package telas;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
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
		System.out.println("onMenuItemGrupoProdutoAcao");
	}

	@FXML
	public void onMenuItemProdutoAcao() {
		System.out.println("onMenuItemProdutoAcao");
	}

	@FXML
	public void onMenuItemVersaoAcao() {
		System.out.println("onMenuItemVersaoAcao");
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}

	private synchronized <T> void loadView(String nomeAbsoluto, Consumer<T> initializingAction) { // Função para abrir
																									// uma outra tela.
																									// synchronized =
																									// Garante que o
																									// processamento não
																									// será interrompido
																									// pelo Thread
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(nomeAbsoluto)); // Padrão do método '
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
			 * Com isso é possível manipular a cena principal e incluir o Main Menu e os
			 * filhos da janela que está sendo aberta pelo método.
			 */

			T controller = loader.getController(); // Irão executar a função que está sendo passada como argumento no
													// método 'onMenuItemDepartamentoAction' entre outros métodos
			initializingAction.accept(controller); // Irão executar a função que está sendo passada como argumento no
													// método 'onMenuItemDepartamentoAction' entre outros métodos
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
