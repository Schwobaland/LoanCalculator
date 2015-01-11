/**
 *
 */
package de.sambalmueslie.loan_calculator.view.entry_mgr.tree;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.sambalmueslie.loan_calculator.model.loan.Loan;
import de.sambalmueslie.loan_calculator.view.ViewActionListener;
import de.sambalmueslie.loan_calculator.view.entry_mgr.LoanContextMenu;

/**
 * @author sambalmueslie 2015
 */
public class EntryTreeItemLoan extends GridPane implements EntryTreeItemContent<Loan> {

	/** the {@link Logger}. */
	private static Logger logger = LogManager.getLogger(EntryTreeItemLoan.class);

	/**
	 * Constructor.
	 */
	public EntryTreeItemLoan() {
		getStyleClass().add("entry-tree-item");

		try {
			final ImageView icon = new ImageView(new Image(getClass().getResourceAsStream("page.gif")));
			add(icon, 0, 0);
		} catch (final RuntimeException e) {
			logger.warn("Cannot add icon for loan tree item: " + e.getMessage());
		}

		add(name, 1, 0);
	}

	/**
	 * @see de.sambalmueslie.loan_calculator.view.entry_mgr.list.EntryListCellContent#getContextMenu(de.sambalmueslie.loan_calculator.model.generic.GenericModelEntry)
	 */
	@Override
	public ContextMenu getContextMenu(final Loan entry) {
		contextMenu.set(entry);
		return contextMenu;
	}

	/**
	 * @see de.sambalmueslie.loan_calculator.view.entry_mgr.list.EntryListCellContent#getGrapic(de.sambalmueslie.loan_calculator.model.generic.GenericModelEntry)
	 */
	@Override
	public Node getGrapic(final Loan entry) {
		name.setText(entry.getName());
		return this;
	}

	/**
	 * @see de.sambalmueslie.loan_calculator.view.entry_mgr.list.EntryListCellContent#set(de.sambalmueslie.loan_calculator.view.ViewActionListener)
	 */
	@Override
	public void set(final ViewActionListener listener) {
		contextMenu.setListener(listener);
	}

	/** the {@link LoanContextMenu}. */
	private final LoanContextMenu contextMenu = new LoanContextMenu();
	/** the name {@link Label}. */
	private final Label name = new Label();
}
