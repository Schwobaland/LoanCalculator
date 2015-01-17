/**
 *
 */
package de.sambalmueslie.loan_calculator.view;

import static de.sambalmueslie.loan_calculator.view.Constants.CLASS_PANEL_EMPTY;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

import de.sambalmueslie.loan_calculator.controller.file.LoanFile;
import de.sambalmueslie.loan_calculator.model.Model;
import de.sambalmueslie.loan_calculator.view.entry_mgr.tabs.EntryTabPane;
import de.sambalmueslie.loan_calculator.view.entry_mgr.tree.EntryTree;
import de.sambalmueslie.loan_calculator.view.menu.MainMenu;

/**
 * The view.
 *
 * @author sambalmueslie 2015
 */
@SuppressWarnings("deprecation")
public class View extends BorderPane {

	/**
	 * Register a {@link ViewActionListener}.
	 *
	 * @param listener
	 *            the listener
	 */
	public void listenerRegister(final ViewActionListener listener) {
		actionListenerMgr.register(listener);
	}

	/**
	 * Unregister a {@link ViewActionListener}.
	 *
	 * @param listener
	 *            the listener
	 */
	public void listenerUnregister(final ViewActionListener listener) {
		actionListenerMgr.unregister(listener);
	}

	/**
	 * Setup.
	 *
	 * @param primaryStage
	 *            the primary {@link Stage}
	 */
	public void setup(final Stage primaryStage) {
		primaryStage.setTitle("Loan calculator by sambalmueslie!");
		final Label statusbar = new Label();

		content = new BorderPane();
		content.getStyleClass().add(CLASS_PANEL_EMPTY);

		final MainMenu mainMenu = new MainMenu(actionListenerMgr);

		getStyleClass().add(CLASS_PANEL_EMPTY);
		setTop(mainMenu);
		setCenter(content);
		setBottom(statusbar);

		final Scene scene = new Scene(this, 1480, 940);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

		statusbar.setText(primaryStage.getScene().getWidth() + " " + primaryStage.getScene().getHeight());

		primaryStage.widthProperty().addListener((ChangeListener<Number>) (observableValue, oldSceneWidth, newSceneWidth) -> {
			statusbar.setText(primaryStage.getScene().getWidth() + " " + primaryStage.getScene().getHeight());
		});
		primaryStage.heightProperty().addListener((ChangeListener<Number>) (observableValue, oldSceneWidth, newSceneWidth) -> {
			statusbar.setText(primaryStage.getScene().getWidth() + " " + primaryStage.getScene().getHeight());
		});

	}

	/**
	 * Show.
	 *
	 * @param file
	 *            the {@link LoanFile}
	 */
	public void show(final LoanFile file) {
		final Model model = file.getModel();
		final EntryTree entryTree = new EntryTree(model, actionListenerMgr);
		final EntryTabPane entryTabPane = new EntryTabPane(model, actionListenerMgr);

		content.setLeft(entryTree);
		content.setCenter(entryTabPane);
	}

	/**
	 * Show the dialog to ask the user if it is his will to refuse the unsaved changes.
	 *
	 * @return <code>true</code> if he like to refuse, othewise <code>false</code>
	 */
	public boolean showDialogRefuseUnsavedChanges() {
		final Action response = Dialogs.create().title("Refuse unsaved changes?").message("There are unsaved changes, do you realy want to continue?")
				.showConfirm();
		return response == Dialog.ACTION_YES;
	}

	/**
	 * Teardown.
	 */
	public void teardown() {
		// TODO not implemented yet
	}

	/** the {@link ViewActionListenerMgr}. */
	private final ViewActionListenerMgr actionListenerMgr = new ViewActionListenerMgr();
	/** the content pane. */
	private BorderPane content;

}
