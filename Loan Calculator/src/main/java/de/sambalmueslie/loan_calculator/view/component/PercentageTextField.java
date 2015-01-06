/**
 *
 */
package de.sambalmueslie.loan_calculator.view.component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The percentage text field.
 * 
 * @author sambalmueslie 2015
 */
public class PercentageTextField extends BaseTextField<Double> {

	/** the format string. */
	private static final String FORMAT = "%,.2f %%";

	/** the {@link Pattern}. */
	private static Pattern pattern;

	/** the regex. */
	private static final String REGEX = "([\\d\\.,]*).*";

	static {
		pattern = Pattern.compile(REGEX);
	}

	/**
	 * @see de.sambalmueslie.loan_calculator.view.component.BaseTextField#getType()
	 */
	@Override
	public TextFieldType getType() {
		return TextFieldType.PERCENTAGE;
	}

	/**
	 * @see de.sambalmueslie.loan_calculator.view.component.BaseTextField#getValue()
	 */
	@Override
	public Double getValue() {
		final Matcher m = pattern.matcher(getText());
		if (!m.find()) return new Double(-1);
		final String rawValue = m.group(1);
		final String text = rawValue.replace(".", "").replace(",", ".");
		return Double.parseDouble(text);
	}

	/**
	 * @see de.sambalmueslie.loan_calculator.view.component.BaseTextField#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(final Double value) {
		final String text = String.format(FORMAT, value);
		setText(text);
	}

}
