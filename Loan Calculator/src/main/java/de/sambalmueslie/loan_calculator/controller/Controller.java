/**
 *
 */
package de.sambalmueslie.loan_calculator.controller;

import javafx.application.Application;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.sambalmueslie.loan_calculator.model.Loan;
import de.sambalmueslie.loan_calculator.model.LoanFactory;
import de.sambalmueslie.loan_calculator.model.Model;
import de.sambalmueslie.loan_calculator.view.View;
import de.sambalmueslie.loan_calculator.view.ViewActionListener;

/**
 * The controller.
 *
 * @author sambalmueslie 2015
 */
public class Controller extends Application {

	/** the logger. */
	private static final Logger logger = LogManager.getLogger(Controller.class);

	/**
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(final Stage primaryStage) throws Exception {
		model = new Model();
		view = new View(model);

		view.setup(primaryStage);
		viewActionHandler = new ViewActionHandler(this);
		view.listenerRegister(viewActionHandler);
	}

	/**
	 * @see ViewActionListener#requestAddLoan(String, double, double, double, int, double)
	 */
	void handleRequestAddLoan(final String name, final double amount, final double paymentRate, final double fixedDebitInterest, final int fixedInterestPeriod,
			final double estimatedDebitInterest) {
		if (logger.isDebugEnabled()) {
			logger.debug("Handle request to add loan " + name + ", " + amount + ", " + paymentRate + ", " + fixedDebitInterest + ", " + fixedInterestPeriod
					+ ", " + estimatedDebitInterest);
		}
		try {
			final Loan loan = LoanFactory.createAnnuityLoan(name, amount, paymentRate, fixedDebitInterest, fixedInterestPeriod, estimatedDebitInterest);
			model.add(loan);
		} catch (final IllegalArgumentException e) {
			logger.error("Cannot add loan " + name + ", " + amount + ", " + paymentRate + ", " + fixedDebitInterest + ", " + fixedInterestPeriod + ", "
					+ estimatedDebitInterest + " cause " + e.getMessage());
			// TODO handle error
		}
	}

	/**
	 * @see ViewActionListener#requestRemoveLoan(long)
	 */
	void handleRequestRemoveLoan(final long loanId) {
		if (logger.isDebugEnabled()) {
			logger.debug("Handle request to remove loan " + loanId);
		}
		final Loan loan = model.get(loanId);
		if (loan != null) {
			model.remove(loan);
		}
	}

	/**
	 * @see ViewActionListener#requestUpdateLoan(long, String, double, double, double, int, double)
	 */
	void handleRequestUpdateLoan(final long loanId, final String name, final double amount, final double paymentRate, final double fixedDebitInterest,
			final int fixedInterestPeriod, final double estimatedDebitInterest) {
		if (logger.isDebugEnabled()) {
			logger.debug("Handle request to update loan " + loanId + ", " + name + ", " + amount + ", " + paymentRate + ", " + fixedDebitInterest + ", "
					+ fixedInterestPeriod + ", " + estimatedDebitInterest);
		}
		try {
			final Loan loan = LoanFactory.createAnnuityLoan(name, amount, paymentRate, fixedDebitInterest, fixedInterestPeriod, estimatedDebitInterest);
			model.add(loan);
		} catch (final IllegalArgumentException e) {
			logger.error("Cannot update loan " + name + ", " + amount + ", " + paymentRate + ", " + fixedDebitInterest + ", " + fixedInterestPeriod + ", "
					+ estimatedDebitInterest + " cause " + e.getMessage());
			// TODO handle error
		}
	}

	/** the {@link Model}. */
	private Model model;
	/** the {@link View}. */
	private View view;
	/** the {@link ViewActionHandler}. */
	private ViewActionHandler viewActionHandler;

}
