package de.sambalmueslie.loan_calculator.view.chart.loan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import de.sambalmueslie.loan_calculator.model.loan.Loan;
import de.sambalmueslie.loan_calculator.model.loan.RedemptionPlanEntry;
import de.sambalmueslie.loan_calculator.view.chart.Chart;

/**
 * The chart for the residual debt.
 *
 * @author sambalmueslie 2015
 */
public class ResidualDebtChart extends LineChart<Number, Number> implements Chart<Loan> {

	/**
	 * Constructor.
	 */
	ResidualDebtChart() {
		super(new NumberAxis(), new NumberAxis());
		setTitle("Redemption plan");
		setAnimated(false);
		setLegendSide(Side.BOTTOM);
	}

	@Override
	public void add(final Loan loan) {
		if (loan == null || data.containsKey(loan)) return;
		final ObservableList<Data<Number, Number>> values = FXCollections.observableArrayList();
		final List<RedemptionPlanEntry> redemptionPlan = loan.getRedemptionPlan();
		for (int i = 0; i < redemptionPlan.size(); i++) {
			final RedemptionPlanEntry redemption = redemptionPlan.get(i);
			values.add(new Data<Number, Number>(i, redemption.getResidualDebt()));
		}
		final Series<Number, Number> series = new Series<>(values);
		final String name = loan.getName();
		series.setName(name);
		data.put(loan, series);
		getData().add(series);

	}

	@Override
	public Node getChart() {
		return this;
	}

	@Override
	public void remove(final Loan loan) {
		final Series<Number, Number> series = data.remove(loan);
		if (series != null) {
			getData().remove(series);
		}
	}

	/** the {@link Series} by {@link Loan}. */
	private final Map<Loan, Series<Number, Number>> data = new HashMap<>();

}
