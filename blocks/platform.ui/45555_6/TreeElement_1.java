package org.eclipse.e4.ui.css.swt.dom;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.internal.css.swt.dom.AbstractControlSelectionEraseListener;
import org.eclipse.e4.ui.internal.css.swt.dom.ControlSelectedColorCustomization;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Table;

public class TableElement extends ControlElement implements ISelectionBackgroundCustomizationElement {

	private final ControlSelectedColorCustomization fControlSelectedColorCustomization;

	private static class TableControlSelectionEraseListener extends AbstractControlSelectionEraseListener {

		@Override
		protected void fixEventDetail(Control control, Event event) {
			if ((event.detail & SWT.FOCUSED) != 0) {
				event.detail &= ~SWT.SELECTED;
			} else {
			}
		}

		@Override
		protected int getNumberOfColumns(Control control) {
			return ((Table) control).getColumnCount();
		}

	}

	public TableElement(Table table, CSSEngine engine) {
		super(table, engine);
		fControlSelectedColorCustomization = new ControlSelectedColorCustomization(table,
				new TableControlSelectionEraseListener());
	}

	@Override
	public void setSelectionBackgroundColor(Color color) {
		this.fControlSelectedColorCustomization.setSelectionBackgroundColor(color);
	}

	@Override
	public Color getSelectionBackgroundColor() {
		return this.fControlSelectedColorCustomization.getSelectionBackgroundColor();
	}

	@Override
	public void setSelectionBorderColor(Color color) {
		this.fControlSelectedColorCustomization.setSelectionBorderColor(color);

	}

	@Override
	public Color getSelectionBorderColor() {
		return this.fControlSelectedColorCustomization.getSelectionBorderColor();
	}

	@Override
	public void setHotBackgroundColor(Color color) {
		this.fControlSelectedColorCustomization.setHotBackgroundColor(color);

	}

	@Override
	public Color getHotBackgroundColor() {
		return this.fControlSelectedColorCustomization.getHotBackgroundColor();
	}

	@Override
	public void setHotBorderColor(Color color) {
		this.fControlSelectedColorCustomization.setHotBorderColor(color);
	}

	@Override
	public Color getHotBorderColor() {
		return this.fControlSelectedColorCustomization.getHotBorderColor();
	}

	@Override
	public Color getSelectionForegroundColor() {
		return this.fControlSelectedColorCustomization.getSelectionForegroundColor();
	}

	@Override
	public void setSelectionForegroundColor(Color color) {
		this.fControlSelectedColorCustomization.setSelectionForegroundColor(color);
	}

}
