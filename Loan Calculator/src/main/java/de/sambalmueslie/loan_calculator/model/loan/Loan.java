/**
 *
 */
package de.sambalmueslie.loan_calculator.model.loan;

import java.util.List;

/**
 * A loan.
 *
 * @author sambalmueslie 2015
 */
public interface Loan {

	/**
	 * @return the amount of the loan (kredit).
	 */
	double getAmount();

	/**
	 * @return the id.
	 */
	long getId();

	/**
	 * @return the name.
	 */
	String getName();

	/**
	 * @return the redemption plan.
	 */
	List<RedemptionPlanEntry> getRedemptionPlan();

	/**
	 * @return the term in months.
	 */
	int getTerm();

	/**
	 * @return the total interest to pay.
	 */
	double getTotalInterest();

	/**
	 * @return the total payment (interest and amount).
	 */
	double getTotalPayment();

	/**
	 * Register the {@link LoanChangeListener}.
	 * 
	 * @param listener
	 *            the listener
	 */
	void register(LoanChangeListener listener);

	/**
	 * Unregister the {@link LoanChangeListener}.
	 * 
	 * @param listener
	 *            the listener
	 */
	void unregister(LoanChangeListener listener);
}