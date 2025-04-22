
package org.eclipse.core.tests.internal.databinding.validation;

import junit.framework.TestCase;

import org.eclipse.core.databinding.conversion.StringToNumberConverter;
import org.eclipse.core.internal.databinding.validation.AbstractStringToNumberValidator;
import org.eclipse.core.internal.databinding.validation.NumberFormatConverter;
import org.eclipse.core.runtime.IStatus;

public class AbstractStringToNumberValidatorTest extends TestCase {
	public void testErrorMessagesAreNotCached() throws Exception {
		NumberFormatConverter c = StringToNumberConverter.toInteger(false);
		ValidatorStub v = new ValidatorStub(c);

		IStatus status1 = v.validate("1a");
		assertEquals(IStatus.ERROR, status1.getSeverity());

		IStatus status2 = v.validate("2b");
		assertEquals(IStatus.ERROR, status2.getSeverity());

		assertFalse("messages should not be equal", status1.getMessage().equals(status2.getMessage()));
	}
	
	static class ValidatorStub extends AbstractStringToNumberValidator {
		ValidatorStub(NumberFormatConverter c) {
			super(c, new Integer(Integer.MIN_VALUE), new Integer(Integer.MAX_VALUE));
		}
		
		@Override
		protected boolean isInRange(Number number) {
			return true;
		}
	}
}
