package org.eclipse.e4.ui.workbench.swt;

import org.eclipse.core.runtime.IProgressMonitor;

public interface ISaveablePart {

	public static final int PROP_DIRTY = 0x101;

	public void doSave(IProgressMonitor monitor);

	public void doSaveAs();

	public boolean isDirty();

	public boolean isSaveAsAllowed();

	public boolean isSaveOnCloseNeeded();
}
