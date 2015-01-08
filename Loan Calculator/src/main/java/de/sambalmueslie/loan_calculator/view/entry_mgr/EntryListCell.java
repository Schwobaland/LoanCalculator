/**
 *
 */
package de.sambalmueslie.loan_calculator.view.entry_mgr;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListCell;
import de.sambalmueslie.loan_calculator.model.generic.GenericModelEntry;

/**
 * A abstract entry {@link ListCell}.
 *
 * @author sambalmueslie 2015
 */
public final class EntryListCell extends ListCell<GenericModelEntry<?>> {

	/**
	 * Constructor.
	 *
	 * @param factories
	 *            the {@link EntryListCellContentFactory}s.
	 */
	public EntryListCell(final EntryListCellContentFactory<?>... factories) {
		this.factories = new HashSet<>(Arrays.asList(factories));
	}

	/**
	 * @see javafx.scene.control.Cell#updateItem(java.lang.Object, boolean)
	 */
	@Override
	public final void updateItem(final GenericModelEntry<?> entry, final boolean empty) {
		super.updateItem(entry, empty);
		if (empty) {
			clearContent();
		} else {
			addContent(entry);
		}
	}

	/**
	 * @param entry
	 */
	@SuppressWarnings("unchecked")
	private <T extends GenericModelEntry<T>> void addContent(final GenericModelEntry<?> entry) {
		setText(null);
		final T e = (T) entry;
		final EntryListCellContent<T> content = getContent(e);
		setGraphic(content.getGrapic(e));
		setContextMenu(content.getContextMenu(e));
	}

	/**
	 * Clear content.
	 */
	private void clearContent() {
		setText(null);
		setGraphic(null);
	}

	/**
	 * Is {@link EntryListCellContentFactory} type and {@link GenericModelEntry} type equals.
	 *
	 * @param factory
	 *            the factory
	 * @param entry
	 *            the entry
	 * @return <code>true</code> if so, otherwise <code>false</code>
	 */
	private boolean equals(final EntryListCellContentFactory<?> factory, final GenericModelEntry<?> entry) {
		final Class<?> factoryType = factory.getType();
		final Class<?> entryType = entry.getClass();
		return factoryType.isAssignableFrom(entryType);
	}

	/**
	 * Get the content for a {@link GenericModelEntry}.
	 *
	 * @param entry
	 *            the entry
	 * @return the content {@link Node}
	 */
	@SuppressWarnings("unchecked")
	private <T extends GenericModelEntry<T>> EntryListCellContent<T> getContent(final T entry) {
		final Optional<EntryListCellContentFactory<?>> optional = factories.stream().filter(f -> equals(f, entry)).findFirst();
		if (!optional.isPresent()) return null;
		final EntryListCellContentFactory<T> factory = (EntryListCellContentFactory<T>) optional.get();
		final EntryListCellContent<T> content = factory.create();
		return content;
	}

	/** the {@link EntryListCellContentFactory}s. */
	private final Set<EntryListCellContentFactory<?>> factories;

}
