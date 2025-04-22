package org.eclipse.e4.ui.progress;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IElementCollector {
    public void add(Object element, IProgressMonitor monitor);

    public void add(Object[] elements, IProgressMonitor monitor);

    public void done();
}
