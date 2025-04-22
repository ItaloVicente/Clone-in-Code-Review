
package org.eclipse.jface.tests.internal.databinding.swt;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.tests.databinding.AbstractSWTTestCase;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DateTime;

public class DateTimeDateObservableValueTest extends AbstractSWTTestCase {
	private DateTime dateTime;
	private IObservableValue dateObservable;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dateTime = new DateTime(getShell(), SWT.DATE);
		dateObservable = WidgetProperties.selection().observe(dateTime);
	}

	public void testGetValue_ExcludesTimeComponent() {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		int epochHour = calendar.get(Calendar.HOUR_OF_DAY);
		int epochMinute = calendar.get(Calendar.MINUTE);
		int epochSecond = calendar.get(Calendar.SECOND);
		int epochMillisecond = calendar.get(Calendar.MILLISECOND);

		calendar.set(2009, 3, 3, 12, 7, 21); // time of writing
		dateObservable.setValue(calendar.getTime());

		calendar.setTime((Date) dateObservable.getValue());

		assertEquals(2009, calendar.get(Calendar.YEAR));
		assertEquals(3, calendar.get(Calendar.MONTH));
		assertEquals(3, calendar.get(Calendar.DAY_OF_MONTH));

		assertEquals(epochHour, calendar.get(Calendar.HOUR_OF_DAY));
		assertEquals(epochMinute, calendar.get(Calendar.MINUTE));
		assertEquals(epochSecond, calendar.get(Calendar.SECOND));
		assertEquals(epochMillisecond, calendar.get(Calendar.MILLISECOND));
	}
}
