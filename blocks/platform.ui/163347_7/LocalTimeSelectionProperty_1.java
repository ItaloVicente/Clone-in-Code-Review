
package org.eclipse.jface.internal.databinding.swt;

import java.time.LocalDate;

import org.eclipse.jface.databinding.swt.WidgetValueProperty;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DateTime;

public class LocalDateSelectionProperty extends WidgetValueProperty<DateTime, LocalDate> {
	public LocalDateSelectionProperty() {
		super(SWT.Selection);
	}

	@Override
	public Object getValueType() {
		return LocalDate.class;
	}

	@Override
	protected LocalDate doGetValue(DateTime source) {
		if ((source.getStyle() & SWT.TIME) != 0) {
			throw new IllegalStateException();
		}

		return LocalDate.of(source.getYear(), source.getMonth() + 1, source.getDay());
	}

	@Override
	protected void doSetValue(DateTime source, LocalDate value) {
		if (value == null) {
			return;
		}

		if ((source.getStyle() & SWT.TIME) != 0) {
			throw new IllegalStateException();
		}

		source.setDate(value.getYear(), value.getMonthValue() - 1, value.getDayOfMonth());
	}
}
