package org.eclipse.ui.internal.layout;

import org.eclipse.swt.widgets.Control;

public interface IWindowTrim {
	Control getControl();

	int getValidSides();

	void dock(int dropSide);

	public String getId();

	public String getDisplayName();

	public boolean isCloseable();

	public void handleClose();

	public int getWidthHint();

	public int getHeightHint();

	public boolean isResizeable();
}
