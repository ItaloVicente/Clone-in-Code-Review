/*******************************************************************************
 * Copyright (c) 2020 Jens Lidestrom and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Jens Lidestrom - initial API and implementation
 ******************************************************************************/

package org.eclipse.jface.internal.databinding.swt;

import java.time.LocalTime;

import org.eclipse.jface.databinding.swt.WidgetValueProperty;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DateTime;

/**
 */
public class LocalTimeSelectionProperty extends WidgetValueProperty<DateTime, LocalTime> {
	/**
	 */
	public LocalTimeSelectionProperty() {
		super(SWT.Selection);
	}

	@Override
	public Object getValueType() {
		return LocalTime.class;
	}

	@Override
	protected LocalTime doGetValue(DateTime source) {
		if ((source.getStyle() & SWT.TIME) == 0) {
			throw new IllegalStateException();
		}
		return LocalTime.of(source.getHours(), source.getMinutes(), source.getSeconds());
	}

	@Override
	protected void doSetValue(DateTime source, LocalTime value) {
		if (value == null) {
			return;
		}

		if ((source.getStyle() & SWT.TIME) == 0) {
			throw new IllegalStateException();
		}

		source.setTime(value.getHour(), value.getMinute(), value.getSecond());
	}
}
