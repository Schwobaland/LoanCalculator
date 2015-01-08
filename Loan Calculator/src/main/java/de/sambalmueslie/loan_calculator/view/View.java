/**
 *
 */
package de.sambalmueslie.loan_calculator.view;

import java.util.LinkedList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import de.sambalmueslie.loan_calculator.model.Model;
import de.sambalmueslie.loan_calculator.model.founding.Founding;
import de.sambalmueslie.loan_calculator.model.loan.AnnuityLoan;
import de.sambalmueslie.loan_calculator.model.loan.Loan;
import de.sambalmueslie.loan_calculator.view.compare.CompareLoanPanel;
import de.sambalmueslie.loan_calculator.view.loan_mgr.LoanManager;
import de.sambalmueslie.loan_calculator.view.loan_mgr.LoanManagerChangeListener;
import de.sambalmueslie.loan_calculator.view.panel.AnnuityLoanPanel;

/**
 * The view.
 *
 * @author sambalmueslie 2015
 */
public class View extends BorderPane {

	/**
	 * Constructor.
	 *
	 * @param model
	 */
	public View(final Model model) {
		this.model = model;

		modelChangeHandler = new ModelChangeHandler(this);

		loanManager = new LoanManager();
		loanManagerChangeHandler = new LoanManagerChangeHandler(this);
		loanManager.register(loanManagerChangeHandler);
	}

	/**
	 * Register a {@link ViewActionListener}.
	 *
	 * @param listener
	 *            the listener
	 */
	public void listenerRegister(final ViewActionListener listener) {
		if (listener != null && !listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * Unregister a {@link ViewActionListener}.
	 *
	 * @param listener
	 *            the listener
	 */
	public void listenerUnregister(final ViewActionListener listener) {
		if (listener != null) {
			listeners.remove(listener);
		}
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

		tabPane = new TabPane();

		setLeft(loanManager);
		setCenter(tabPane);
		setBottom(statusbar);

		primaryStage.setScene(new Scene(this, 1280, 880));
		primaryStage.show();

		statusbar.setText(primaryStage.getScene().getWidth() + " " + primaryStage.getScene().getHeight());

		primaryStage.widthProperty().addListener((ChangeListener<Number>) (observableValue, oldSceneWidth, newSceneWidth) -> {
			statusbar.setText(primaryStage.getScene().getWidth() + " " + primaryStage.getScene().getHeight());
		});
		primaryStage.heightProperty().addListener((ChangeListener<Number>) (observableValue, oldSceneWidth, newSceneWidth) -> {
			statusbar.setText(primaryStage.getScene().getWidth() + " " + primaryStage.getScene().getHeight());
		});

		model.getAllLoans().forEach(loan -> handleLoanAdded(loan));
		model.getAllFoundings().forEach(founding -> handleFoundingAdded(founding));

		modelChangeHandler.register(model);

	}

	/**
	 * Teardown.
	 */
	public void teardown() {
		modelChangeHandler.unregister(model);
	}

	/**
	 * Handle the addition of a {@link Founding}.
	 *
	 * @param founding
	 *            the founding
	 */
	void handleFoundingAdded(final Founding founding) {
		// TODO Auto-generated method stub

	}

	/**
	 * Handle the removal of a {@link Founding}.
	 *
	 * @param founding
	 *            the founding
	 */
	void handleFoundingRemoved(final Founding founding) {
		// TODO Auto-generated method stub

	}

	/**
	 * Handle the addition of a {@link Loan}.
	 *
	 * @param loan
	 *            the loan
	 */
	void handleLoanAdded(final Loan loan) {
		loanManager.add(loan);

		final Node content = createLoanPanel(loan);
		final String name = loan.getName();
		addTab(content, name, false);
	}

	/**
	 * Handle the removal of a {@link Loan}.
	 *
	 * @param loan
	 *            the loan
	 */
	void handleLoanRemoved(final Loan loan) {
		loanManager.remove(loan);
		tabPane.getTabs().removeIf(t -> t.getText().equals(loan.getName()));
	}

	/**
	 * @see LoanManagerChangeListener#requestAddAnnuityLoan(LoanManager, String, double, double, double, int, double)
	 */
	void notifyRequestAddAnnuityLoan(final LoanManager manager, final String name, final double amount, final double paymentRate,
			final double fixedDebitInterest, final int fixedInterestPeriod, final double estimatedDebitInterest) {
		listeners.forEach(l -> l.requestAddAnnuityLoan(name, amount, paymentRate, fixedDebitInterest, fixedInterestPeriod, estimatedDebitInterest));
	}

	/**
	 * @see LoanManagerChangeListener#requestRemoveLoan(LoanManager, long)
	 */
	void notifyRequestRemoveLoan(final LoanManager manager, final long loanId) {
		listeners.forEach(l -> l.requestRemoveLoan(loanId));
	}

	/**
	 * @see LoanManagerChangeListener#requestUpdateAnnuityLoan(LoanManager, long, String, double, double, double, int, double)
	 */
	void notifyRequestUpdateAnnuityLoan(final LoanManager manager, final long loanId, final String name, final double amount, final double paymentRate,
			final double fixedDebitInterest, final int fixedInterestPeriod, final double estimatedDebitInterest) {
		listeners.forEach(l -> l.requestUpdateAnnuityLoan(loanId, name, amount, paymentRate, fixedDebitInterest, fixedInterestPeriod, estimatedDebitInterest));
	}

	/**
	 * Request to compare a {@link List} of loans.
	 *
	 * @param manager
	 *            the {@link LoanManager}
	 * @param loans
	 *            the {@link Loan}s to compare
	 */
	void requestCompareLoans(final LoanManager manager, final List<Loan> loans) {
		final Node tabContent = new CompareLoanPanel(loans);
		final String tabName = "Compare";
		addTab(tabContent, tabName, true);
	}

	/**
	 * Add a new {@link Tab}.
	 *
	 * @param content
	 *            the content
	 * @param name
	 *            the name
	 * @param closeable
	 *            the closeable flag @see {@link Tab#setClosable(boolean)}
	 */
	private void addTab(final Node content, final String name, final boolean closeable) {
		final Tab tab = new Tab(name);
		tab.setClosable(closeable);
		final ScrollPane scrollPane = new ScrollPane(content);
		tab.setContent(scrollPane);
		tabPane.getTabs().add(tab);
		tabPane.getSelectionModel().select(tab);
	}

	/**
	 * Create a panel for a {@link Loan}.
	 *
	 * @param loan
	 *            the loan
	 * @return the panel {@link Node}
	 */
	private Node createLoanPanel(final Loan loan) {
		if (loan instanceof AnnuityLoan) return new AnnuityLoanPanel((AnnuityLoan) loan);
		return null;
	}

	/** the {@link ViewActionListener}. */
	private final List<ViewActionListener> listeners = new LinkedList<>();
	/** the {@link LoanManager}. */
	private final LoanManager loanManager;
	/** the {@link LoanManagerChangeListener}. */
	private final LoanManagerChangeHandler loanManagerChangeHandler;
	/** the {@link Model}. */
	private final Model model;
	/** the {@link ModelChangeHandler}. */
	private final ModelChangeHandler modelChangeHandler;
	/** the {@link TabPane}. */
	private TabPane tabPane;

}
