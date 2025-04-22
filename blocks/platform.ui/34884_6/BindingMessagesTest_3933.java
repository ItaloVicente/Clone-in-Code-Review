
package org.eclipse.core.tests.databinding.validation;

import junit.framework.TestCase;

import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;

public class ValidationStatusTest extends TestCase {
	public void testEqualsAndHashCode() throws Exception {
		String message = "error";
		Exception e = new IllegalArgumentException();
		IStatus status1 = ValidationStatus.error(message, e);
		IStatus status2 = ValidationStatus.error(message, e);
		
		assertEquals(status1, status2);
		assertEquals(status1.hashCode(), status2.hashCode());
	}
}
