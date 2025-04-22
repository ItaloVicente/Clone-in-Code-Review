package org.eclipse.ui.tests.api;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.layout.IWindowTrim;

public class TrimList implements IWindowTrim {
	public static final String TRIM_LIST_ID = "org.eclipse.ui.tests.api.TrimList";

	private static final String[] INIT_LIST = { "Offline", "Online", "Proxied" };

	private Combo fCombo;

	public TrimList(Shell shell) {
		fCombo = new Combo(shell, SWT.DROP_DOWN|SWT.READ_ONLY);
		for (String value : INIT_LIST) {
			fCombo.add(value);
		}
		fCombo.select(0);
	}

	@Override
	public Control getControl() {
		return fCombo;
	}

	@Override
	public int getValidSides() {
		return SWT.TOP | SWT.BOTTOM;
	}

	@Override
	public void dock(int dropSide) {
	}

	@Override
	public String getId() {
		return TRIM_LIST_ID;
	}

	@Override
	public String getDisplayName() {
		return "Trim List";
	}

	@Override
	public boolean isCloseable() {
		return false;
	}

	@Override
	public void handleClose() {
	}

	@Override
	public int getWidthHint() {
		return SWT.DEFAULT;
	}

	@Override
	public int getHeightHint() {
		return SWT.DEFAULT;
	}

	@Override
	public boolean isResizeable() {
		return false;
	}
}
