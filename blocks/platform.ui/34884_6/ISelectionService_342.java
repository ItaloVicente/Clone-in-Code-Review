package org.eclipse.ui;

import java.util.EventListener;

import org.eclipse.jface.viewers.ISelection;

public interface ISelectionListener extends EventListener {
    public void selectionChanged(IWorkbenchPart part, ISelection selection);
}
