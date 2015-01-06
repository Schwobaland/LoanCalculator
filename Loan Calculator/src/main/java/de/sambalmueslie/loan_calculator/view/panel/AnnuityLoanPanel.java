/**
 *
 */
package de.sambalmueslie.loan_calculator.view.panel;

import de.sambalmueslie.loan_calculator.model.AnnuityLoan;
import de.sambalmueslie.loan_calculator.model.Redemption;
import de.sambalmueslie.loan_calculator.view.chart.LoanChart;
import de.sambalmueslie.loan_calculator.view.chart.LoanChartFactory;

/**
 * The {@link LoanPanel} for an {@link AnnuityLoan}.
 *
 * @author sambalmueslie 2015
 */
public class AnnuityLoanPanel extends LoanPanel<AnnuityLoan> {

	/**
	 * Constructor.
	 */
	public AnnuityLoanPanel(final AnnuityLoan loan) {
		super(loan);

		addInfo("Payment rate", loan.getPaymentRate(), "%.2f %%");
		addInfo("Fixed debit interest", loan.getFixedDebitInterest(), "%.2f %%");
		addInfo("Fixed interest period", loan.getFixedInterestPeriod(), "%d");
		addInfo("Estimated debit interest", loan.getEstimatedDebitInterest(), "%.2f %%");

		final Redemption redemption = loan.getRedemptionPlan().get(1);
		addInfo("Annuity interest", redemption.getInterest(), "%,.2f �");
		addInfo("Annuity redemption", redemption.getRedemption(), "%,.2f �");
		addInfo("Annuity total", redemption.getInterest() + redemption.getRedemption(), "%,.2f �");

		addInfo("Duration", loan.getRedemptionPlan().size() - 1, "%d");

		addInfo("Total amount", loan.getAmount(), "%,.2f �");
		addInfo("Total interest", loan.getTotalInterest(), "%,.2f �");
		addInfo("Total payment", loan.getTotalPayment(), "%,.2f �");

		final LoanChart residualDebtChart = LoanChartFactory.createResidualDebtChart();
		residualDebtChart.add(loan);
		addChart(residualDebtChart.getChart(), 0, 0);

		final LoanChart annuityPlanChart = LoanChartFactory.createAnnuityPlanChart();
		annuityPlanChart.add(loan);
		addChart(annuityPlanChart.getChart(), 0, 1);
	}
}
