package org.eclipse.ui.part;

import org.eclipse.jface.viewers.ISelection;

public interface ISetSelectionTarget {
    public void selectReveal(ISelection selection);
}
