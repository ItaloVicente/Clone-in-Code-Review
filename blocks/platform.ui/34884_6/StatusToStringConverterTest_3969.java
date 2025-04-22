
package org.eclipse.core.tests.internal.databinding.conversion;

import junit.framework.TestCase;

import org.eclipse.core.internal.databinding.validation.ObjectToPrimitiveValidator;
import org.eclipse.core.runtime.IStatus;

public class ObjectToPrimitiveValidatorTest extends TestCase {

	private ObjectToPrimitiveValidator objectToPrimitiveValidator;

	@Override
	protected void setUp() throws Exception {
		this.objectToPrimitiveValidator = new ObjectToPrimitiveValidator(
				Integer.TYPE);
	}

	public void testIsValid() {
		IStatus result = this.objectToPrimitiveValidator.validate(null);
		assertEquals("The wrong validation error was found.", result
				.getMessage(), this.objectToPrimitiveValidator.getNullHint());

		result = this.objectToPrimitiveValidator.validate(new Integer(1));
		assertTrue("No validation error should be found.", result.isOK());

		result = this.objectToPrimitiveValidator.validate(new Object());
		assertEquals("The wrong validation error was found.", result
				.getMessage(), this.objectToPrimitiveValidator.getClassHint());
	}

}
