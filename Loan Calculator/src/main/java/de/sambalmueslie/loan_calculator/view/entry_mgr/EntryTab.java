/**
 *
 */
package de.sambalmueslie.loan_calculator.view.entry_mgr;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import de.sambalmueslie.loan_calculator.model.compare.Comparison;
import de.sambalmueslie.loan_calculator.model.founding.Founding;
import de.sambalmueslie.loan_calculator.model.generic.GenericModelEntry;
import de.sambalmueslie.loan_calculator.model.loan.AnnuityLoan;
import de.sambalmueslie.loan_calculator.view.panel.AnnuityLoanPanel;
import de.sambalmueslie.loan_calculator.view.panel.ComparisonPanel;
import de.sambalmueslie.loan_calculator.view.panel.FoundingPanel;

/**
 * A entry {@link Tab}.
 *
 * @author sambalmueslie 2015
 */
public class EntryTab extends Tab {

	/**
	 * Constructor.
	 */
	public EntryTab(final GenericModelEntry<?> entry) {
		this.entry = entry;
		getStyleClass().add("entry-tab");
		setClosable(false);
		final Node content = getContent(entry);
		final ScrollPane scrollPane = new ScrollPane(content);
		setContent(scrollPane);
		final String name = entry.getName();
		setText(name);
	}

	/**
	 * @return the {@link #entry}
	 */
	GenericModelEntry<?> getEntry() {
		return entry;
	}

	/**
	 * Get the content {@link Node} for a {@link GenericModelEntry}.
	 *
	 * @param entry
	 *            the entry
	 * @return the content
	 */
	private Node getContent(final GenericModelEntry<?> entry) {
		if (entry instanceof AnnuityLoan) {
			final AnnuityLoan annuityLoan = (AnnuityLoan) entry;
			return new AnnuityLoanPanel(annuityLoan);
		}
		if (entry instanceof Founding) {
			final Founding founding = (Founding) entry;
			return new FoundingPanel(founding);
		}
		if (entry instanceof Comparison) {
			final Comparison<?> comparison = (Comparison<?>) entry;
			return new ComparisonPanel(comparison);
		}
		return null;
	}

	/** the {@link GenericModelEntry}. */
	private final GenericModelEntry<?> entry;

}
