/*******************************************************************************
 * Copyright (c) 2015 Fabio Zadrozny and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Fabio Zadrozny <fabiofz@gmail.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.ui.internal.css.swt.dom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scrollable;

/**
 * Abstract class for dealing with an SWT.EraseItem event which customizes the
 * selection color.
 */
public abstract class AbstractControlSelectionEraseListener implements Listener {

	@Override
	public void handleEvent(Event event) {

		Scrollable control = (Scrollable) event.widget;
		int columnCount = getNumberOfColumns(control);

		boolean selected = (event.detail & SWT.SELECTED) != 0;
		boolean hot = (event.detail & SWT.HOT) != 0;

		event.detail &= ~SWT.HOT;

		if (selected || hot) {

			GC gc = event.gc;
			Rectangle area = control.getClientArea();

			boolean handlingOnlyHot = !selected && hot;
			if (handlingOnlyHot) {
				if (columnCount > 1) {
					return;
				}
			}


			String dataBackgroundKey;
			String dataBorderKey;
			if (handlingOnlyHot) {
				dataBackgroundKey = ControlSelectedColorCustomization.HOT_BACKGROUND_COLOR;
				dataBorderKey = ControlSelectedColorCustomization.HOT_BORDER_COLOR;
			} else {
				dataBackgroundKey = ControlSelectedColorCustomization.SELECTION_BACKGROUND_COLOR;
				dataBorderKey = ControlSelectedColorCustomization.SELECTION_BORDER_COLOR;
			}

			Object dataBackground = control.getData(dataBackgroundKey);
			Object dataBorder = control.getData(dataBorderKey);
			Object dataSelectionForeground = control
					.getData(ControlSelectedColorCustomization.SELECTION_FOREGROUND_COLOR);


			Color background = null;
			if ((dataBackground instanceof Color)) {
				background = (Color) dataBackground;
				if (background.isDisposed()) {
					return;
				}
			}

			Color border = null;
			if ((dataBorder instanceof Color)) {
				border = (Color) dataBorder;
				if (border.isDisposed()) {
					return;
				}
			}

			Color selectionForeground = null;

			if ((dataSelectionForeground instanceof Color)) {
				selectionForeground = (Color) dataSelectionForeground;
			} else {
				selectionForeground = control.getForeground();
			}
			if (selectionForeground.isDisposed()) {
				return;
			}

			if (background == null && border == null) {
				return;
			}

			int width = area.width;
			if (event.index == columnCount - 1 || columnCount == 0) {
				if (width > 0) {
					Region region = new Region();
					gc.getClipping(region);
					region.add(event.x, event.y, width, event.height);
					gc.setClipping(region);
					region.dispose();
				}
			}

			if (background != null) {
				Color oldbackground = gc.getBackground();
				gc.setBackground(background);
				try {
					gc.fillRectangle(0, area.y, area.width + 2, area.height);
				} finally {
					gc.setBackground(oldbackground);
				}
			}
			if (border != null) {
				gc.setForeground(border);
				gc.drawRectangle(0, event.y, width - 1, event.height - 1);
			}

			gc.setForeground(selectionForeground);


			event.detail &= ~SWT.BACKGROUND;

			fixEventDetail(control, event);
		}
	}

	/**
	 * Subclasses should override to fix the event.detail after the selection
	 * event was handled.
	 *
	 * @param control
	 *            the control which had the erase event.
	 * @param event
	 *            the event to have the details fixed.
	 */
	protected abstract void fixEventDetail(Control control, Event event);

	/**
	 * @param control
	 * @return the number of columns from the control
	 */
	protected abstract int getNumberOfColumns(Control control);

}
