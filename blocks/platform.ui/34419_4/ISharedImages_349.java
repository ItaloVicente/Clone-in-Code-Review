package org.eclipse.ui;

import org.eclipse.jface.viewers.ISelection;

public interface ISelectionService {
    public void addSelectionListener(ISelectionListener listener);

    public void addSelectionListener(String partId, ISelectionListener listener);

    public void addPostSelectionListener(ISelectionListener listener);

    public void addPostSelectionListener(String partId,
            ISelectionListener listener);

    public ISelection getSelection();

    public ISelection getSelection(String partId);

    public void removeSelectionListener(ISelectionListener listener);

    public void removeSelectionListener(String partId,
            ISelectionListener listener);

    public void removePostSelectionListener(ISelectionListener listener);

    public void removePostSelectionListener(String partId,
            ISelectionListener listener);
}
