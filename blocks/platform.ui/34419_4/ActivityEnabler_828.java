package org.eclipse.ui.internal.activities.ws;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.activities.IActivityManager;

public class ActivityContentProvider implements IStructuredContentProvider {

    public ActivityContentProvider() {
    }

    @Override
	public void dispose() {
    }

    @Override
	public Object[] getElements(Object inputElement) {
        Object[] activities = new Object[0];
        if (inputElement instanceof IActivityManager) {
            activities = ((IActivityManager) inputElement)
                    .getDefinedActivityIds().toArray();
        } else if (inputElement instanceof Collection) {
            activities = ((Collection) inputElement).toArray();
        }
        return activities;
    }

    @Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }
}
