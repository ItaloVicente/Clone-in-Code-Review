
package org.eclipse.ui.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.internal.layout.IWindowTrim;

public class WindowTrimProxy implements IWindowTrim {

	private Control fTrimControl;

	private String fId;

	private String fDisplayName;

	private int fValidSides;

	private boolean fIsResizeable = false;
	
	private int fWidthHint = SWT.DEFAULT;
	
	private int fHeightHint = SWT.DEFAULT;

	public WindowTrimProxy(Control c, String id, String displayName,
			int validSides) {
		fTrimControl = c;
		fId = id;
		fDisplayName = displayName;
		fValidSides = validSides;
	}

	public WindowTrimProxy(Control c, String id, String displayName,
			int validSides, boolean resizeable) {
		this(c, id, displayName, validSides);
		fIsResizeable = resizeable;
	}

	@Override
	public Control getControl() {
		return fTrimControl;
	}

	@Override
	public int getValidSides() {
		return fValidSides;
	}

	@Override
	public void dock(int dropSide) {
	}

	@Override
	public String getId() {
		return fId;
	}

	@Override
	public String getDisplayName() {
		return fDisplayName;
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
		return fWidthHint;
	}
	
	public void setWidthHint(int w) {
		fWidthHint = w;
	}

	@Override
	public int getHeightHint() {
		return fHeightHint;
	}
	
	public void setHeightHint(int h) {
		fHeightHint = h;
	}

	@Override
	public boolean isResizeable() {
		return fIsResizeable;
	}
}
