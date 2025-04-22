
package org.eclipse.core.tests.internal.databinding.conversion;

import java.util.Date;

import junit.framework.TestCase;

import org.eclipse.core.internal.databinding.BindingMessages;
import org.eclipse.core.internal.databinding.conversion.DateConversionSupport;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

public class DateConversionSupportTest extends TestCase {
	public void testDatePatternIsExternalized() throws Exception {
		StubConverter stub = new StubConverter();
		String key = "DateFormat_DateTime";
		String format = BindingMessages.getString(key);

		assertFalse("format is defined", key.equals(format));
		SimpleDateFormat dateFormat = (SimpleDateFormat) stub.getDateFormat(0);
		assertEquals(format, dateFormat.toPattern());
	}

	public void testTimePatternIsExternalized() throws Exception {
		StubConverter stub = new StubConverter();
		String key = "DateFormat_Time";
		String format = BindingMessages.getString(key);

		assertFalse("format is defined", key.equals(format));
		SimpleDateFormat dateFormat = (SimpleDateFormat) stub.getDateFormat(1);
		assertEquals(format, dateFormat.toPattern());
	}

	public void testFormat_NullDate() {
		StubConverter stub = new StubConverter();
		assertNull(stub.format(null));
	}

	static class StubConverter extends DateConversionSupport {
		@Override
		protected DateFormat getDateFormat(int index) {
			return super.getDateFormat(index);
		}

		@Override
		protected String format(Date date) {
			return super.format(date);
		}
	}
}
