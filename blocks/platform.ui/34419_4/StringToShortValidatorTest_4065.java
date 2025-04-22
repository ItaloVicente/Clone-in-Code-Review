
package org.eclipse.core.tests.internal.databinding.validation;

import junit.framework.TestCase;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.runtime.IStatus;

import com.ibm.icu.text.NumberFormat;

public abstract class StringToNumberValidatorTestHarness extends TestCase {
	private NumberFormat numberFormat;
	private IValidator validator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		numberFormat = setupNumberFormat();
		validator = setupValidator(numberFormat);
	}

	protected abstract NumberFormat setupNumberFormat();

	protected abstract IValidator setupValidator(NumberFormat numberFormat);

	protected abstract String getInvalidString();

	protected abstract Number getOutOfRangeNumber();

	protected abstract Number getInRangeNumber();

	public void testInvalidValueReturnsError() throws Exception {
		IStatus status = validator.validate(getInvalidString());
		assertEquals("error severify", IStatus.ERROR, status.getSeverity());
		assertNotNull("message not null", status.getMessage());
	}

	public void testOutOfRangeValueReturnsError() throws Exception {
		String string = numberFormat.format(getOutOfRangeNumber());
		IStatus status = validator.validate(string);
		assertEquals(IStatus.ERROR, status.getSeverity());
		assertNotNull(status.getMessage());
	}

	public void testValidateValidValue() throws Exception {
		String string = numberFormat.format(getInRangeNumber());
		assertTrue(validator.validate(string).isOK());
	}
}
