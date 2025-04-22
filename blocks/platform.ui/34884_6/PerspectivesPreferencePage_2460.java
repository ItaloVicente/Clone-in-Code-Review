package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IPerspectiveRegistry;

public class PerspContentProvider implements IStructuredContentProvider {

    public PerspContentProvider() {
    }

    @Override
	public void dispose() {
    }

    @Override
	public Object[] getElements(Object element) {
        if (element instanceof IPerspectiveRegistry) {
            return ((IPerspectiveRegistry) element).getPerspectives();
        }
        return null;
    }

    @Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }
}
