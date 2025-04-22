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

package org.eclipse.jface.tests.internal.databinding.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.conformance.util.ValueChangeEventTracker;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.tests.databinding.AbstractSWTTestCase;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DateTime;
import org.junit.Test;

/**
 */
public class LocalDateSelectionPropertyTest extends AbstractSWTTestCase {
	@Test
	public void testSetInObservable() {
		DateTime control = new DateTime(getShell(), SWT.DATE);
		IObservableValue<LocalDate> time = WidgetProperties.localDateSelection().observe(control);
		ValueChangeEventTracker<LocalDate> tracker = new ValueChangeEventTracker<>();

		time.addValueChangeListener(tracker);
		time.setValue(LocalDate.of(1999, 11, 22));

		assertEquals(1, tracker.count);

		assertEquals(1999, control.getYear());
		assertEquals(10, control.getMonth()); // 0 based month
		assertEquals(22, control.getDay());
	}

	@Test
	public void testSetInControl() {
		DateTime control = new DateTime(getShell(), SWT.DATE);
		IObservableValue<LocalDate> time = WidgetProperties.localDateSelection().observe(control);
		ValueChangeEventTracker<LocalDate> tracker = new ValueChangeEventTracker<>();
		time.addValueChangeListener(tracker);

		control.setYear(1999);
		control.setDay(22);

		control.notifyListeners(SWT.Selection, null);

		assertEquals(1, tracker.count);
		assertEquals(LocalDate.of(1999, 11, 22), time.getValue());
	}

	@Test
	public void testWrongKind() {
		DateTime control = new DateTime(getShell(), SWT.TIME);
		IObservableValue<LocalDate> time = WidgetProperties.localDateSelection().observe(control);
		try {
			time.setValue(LocalDate.of(1999, 11, 22));
			fail();
		} catch (IllegalStateException exc) {
		}
	}

	@Test
	public void testNullNotThrowingNullPointerException() {
		DateTime control = new DateTime(getShell(), SWT.DATE);

		try {
			WidgetProperties.localDateSelection().setValue(control, null);
		} catch (NullPointerException notExpected) {
			fail("No NPE should be thrown, because a null value should cause the method to return silently");
		}
	}

	@Test
	public void testType() {
		assertEquals(LocalDate.class, WidgetProperties.localDateSelection().getValueType());
	}
}
