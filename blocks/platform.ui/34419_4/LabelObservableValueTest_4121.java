
package org.eclipse.jface.tests.internal.databinding.swt;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.tests.databinding.AbstractSWTTestCase;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DateTime;

public class DateTimeTimeObservableValueTest extends AbstractSWTTestCase {
	private DateTime dateTime;
	private IObservableValue dateObservable;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dateTime = new DateTime(getShell(), SWT.TIME);
		dateObservable = WidgetProperties.selection().observe(dateTime);
	}

	public void testGetValue_ExcludesDateComponent() {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		int epochYear = calendar.get(Calendar.YEAR);
		int epochMonth = calendar.get(Calendar.MONTH);
		int epochDay = calendar.get(Calendar.DAY_OF_MONTH);

		calendar.set(2009, 3, 3, 12, 7, 21); // time of writing
		dateObservable.setValue(calendar.getTime());

		calendar.setTime((Date) dateObservable.getValue());

		assertEquals(epochYear, calendar.get(Calendar.YEAR));
		assertEquals(epochMonth, calendar.get(Calendar.MONTH));
		assertEquals(epochDay, calendar.get(Calendar.DAY_OF_MONTH));

		assertEquals(12, calendar.get(Calendar.HOUR_OF_DAY));
		assertEquals(7, calendar.get(Calendar.MINUTE));
		assertEquals(21, calendar.get(Calendar.SECOND));
		assertEquals(0, calendar.get(Calendar.MILLISECOND));
	}
}
