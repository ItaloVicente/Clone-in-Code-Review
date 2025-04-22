
package org.eclipse.ui;

import org.eclipse.core.runtime.IProgressMonitor;

public interface ISaveablePart {

    public static final int PROP_DIRTY = IWorkbenchPartConstants.PROP_DIRTY;

    public void doSave(IProgressMonitor monitor);

    public void doSaveAs();

    public boolean isDirty();

    public boolean isSaveAsAllowed();

    public boolean isSaveOnCloseNeeded();
}
