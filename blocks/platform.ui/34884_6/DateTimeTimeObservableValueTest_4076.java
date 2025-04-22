
package org.eclipse.jface.tests.internal.databinding.swt;

import org.eclipse.jface.internal.databinding.swt.DateTimeSelectionProperty;
import org.eclipse.jface.tests.databinding.AbstractSWTTestCase;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DateTime;

public class DateTimeSelectionPropertyTest extends AbstractSWTTestCase {
	DateTime dateTime;
	DateTimeSelectionProperty property;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dateTime = new DateTime(getShell(), SWT.DATE);
		property = new DateTimeSelectionProperty();
	}

	public void testSetValue_NullThrowIllegalArgumentException() {
		try {
			property.setValue(dateTime, null);
			fail("Expected IllegalArgumentException");
		} catch (IllegalArgumentException expected) {
		}
	}
}
