package org.eclipse.ui.internal.activities.ws;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.activities.IActivity;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.NotDefinedException;

public class ActivityLabelProvider extends LabelProvider {

    private IActivityManager activityManager;

    public ActivityLabelProvider(IActivityManager activityManager) {
        this.activityManager = activityManager;
    }

    private String getActivityText(IActivity activity) {
        try {
            return activity.getName();
        } catch (NotDefinedException e) {
            return activity.getId();
        }
    }

    @Override
	public Image getImage(Object element) {
        return null;
    }

    @Override
	public String getText(Object element) {
        if (element instanceof String) {
            return getActivityText(activityManager
                    .getActivity((String) element));
        } else if (element instanceof IActivity) {
            return getActivityText((IActivity) element);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
