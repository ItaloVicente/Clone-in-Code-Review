
package org.eclipse.core.tests.internal.databinding.conversion;

import junit.framework.TestCase;

import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.internal.databinding.conversion.StatusToStringConverter;
import org.eclipse.core.runtime.IStatus;

public class StatusToStringConverterTest extends TestCase {
	private StatusToStringConverter converter;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		converter = new StatusToStringConverter();
	}

	public void testConvertedValueIsMessageOfStatus() throws Exception {
		String message = "this is my message";
		IStatus status = ValidationStatus.error(message);
		assertEquals(message, converter.convert(status));
	}

	public void testFromTypeIsIStatus() throws Exception {
		assertEquals(IStatus.class, converter.getFromType());
	}

	public void testToTypeIsString() throws Exception {
		assertEquals(String.class, converter.getToType());
	}

	public void testIllegalArgumentExceptionIsThrownWithNullInput() throws Exception {
		try {
			converter.convert(null);
			fail();
		} catch (IllegalArgumentException e) {
		}
	}
}
