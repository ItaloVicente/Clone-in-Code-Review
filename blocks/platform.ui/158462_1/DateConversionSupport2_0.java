package org.eclipse.core.internal.databinding.conversion;

import java.text.ParsePosition;
import java.util.Date;

import org.eclipse.core.internal.databinding.BindingMessages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class DateConversionSupport2 {
	private static final int DATE_FORMAT=DateFormat.SHORT;
	private static final int DEFAULT_FORMATTER_INDEX=0;

	private static final int NUM_VIRTUAL_FORMATTERS=1;

	private DateFormat[] formatters = {
			new SimpleDateFormat(BindingMessages.getString(BindingMessages.DATE_FORMAT_DATE_TIME)),
			new SimpleDateFormat(BindingMessages.getString(BindingMessages.DATEFORMAT_TIME)),
			DateFormat.getDateTimeInstance(DATE_FORMAT, DateFormat.SHORT),
			DateFormat.getDateInstance(DATE_FORMAT),
			DateFormat.getTimeInstance(DateFormat.SHORT),
			DateFormat.getDateTimeInstance(DATE_FORMAT,DateFormat.MEDIUM),
			DateFormat.getTimeInstance(DateFormat.MEDIUM)
	};

	protected Date parse(String str) {
		for (int formatterIdx = 0; formatterIdx < formatters.length; formatterIdx++) {
			Date parsed=parse(str,formatterIdx);
			if(parsed!=null) {
				return parsed;
			}
		}
		return null;
	}

	protected Date parse(String str,int formatterIdx) {
		if(formatterIdx>=0) {
				ParsePosition pos=new ParsePosition(0);
				if (str == null) {
					return null;
				}
				Date date=formatters[formatterIdx].parse(str,pos);
				if(pos.getErrorIndex()!=-1||pos.getIndex()!=str.length()) {
					return null;
				}
				return date;
		}
		try {
			long millisecs=Long.parseLong(str);
			return new Date(millisecs);
		}
		catch(NumberFormatException exc) {
		}
		return null;
	}

	protected String format(Date date) {
		return format(date,DEFAULT_FORMATTER_INDEX);
	}

	protected String format(Date date,int formatterIdx) {
		if (date == null)
			return null;
		if(formatterIdx>=0) {
			return formatters[formatterIdx].format(date);
		}
		return String.valueOf(date.getTime());
	}

	protected int numFormatters() {
		return formatters.length+NUM_VIRTUAL_FORMATTERS;
	}

	protected DateFormat getDateFormat(int index) {
		if (index < 0 || index >= formatters.length) {
			throw new IllegalArgumentException("'index' [" + index + "] is out of bounds.");  //$NON-NLS-1$//$NON-NLS-2$
		}

		return formatters[index];
	}
}
