package org.eclipse.ui.ide.dialogs;

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IElementFilter {

    public void filterElements(Collection elements, IProgressMonitor monitor) throws InterruptedException;

    public void filterElements(Object[] elements, IProgressMonitor monitor) throws InterruptedException;

}
