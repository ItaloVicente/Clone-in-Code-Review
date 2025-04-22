package org.eclipse.ui;

import java.util.Date;

public interface ISaveablePart3 extends ISaveablePart {

	public static final int PROP_LAST_MODIFIED = IWorkbenchPartConstants.PROP_LAST_MODIFIED;

	default public boolean getAutoSavePolicy() {
		return true;
	}

	default public Date getLastModified() {
		return null;
	}
}
