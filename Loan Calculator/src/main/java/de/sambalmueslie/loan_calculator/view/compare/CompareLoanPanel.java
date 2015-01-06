/**
 *
 */
package de.sambalmueslie.loan_calculator.view.compare;

import java.util.List;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import de.sambalmueslie.loan_calculator.model.Loan;
import de.sambalmueslie.loan_calculator.view.Constants;
import de.sambalmueslie.loan_calculator.view.chart.AnnuityPlanChart;
import de.sambalmueslie.loan_calculator.view.chart.ResidualDebtChart;

/**
 * The compare {@link Loan} panel.
 *
 * @author sambalmueslie 2015
 */
public class CompareLoanPanel extends BorderPane {

	/**
	 * Constructor.
	 */
	public CompareLoanPanel(final List<Loan> loans) {

		final ResidualDebtChart residualDebtChart = new ResidualDebtChart();
		loans.forEach(l -> residualDebtChart.add(l));

		final TilePane box = new TilePane();
		box.setHgap(Constants.DEFAULT_SPACING);
		box.setVgap(Constants.DEFAULT_SPACING);
		box.setPrefColumns(2);
		loans.stream().map(l -> new AnnuityPlanChart(l)).forEach(c -> box.getChildren().add(c));

		setCenter(residualDebtChart);
		setBottom(box);
	}

}
