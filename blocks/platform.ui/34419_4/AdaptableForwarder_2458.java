package org.eclipse.ui.internal.dialogs;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.ui.internal.registry.IActionSetDescriptor;

public class ActionSetComparator extends ViewerComparator {

    public ActionSetComparator() {
    }

    @Override
	public int compare(Viewer viewer, Object e1, Object e2) {
        if (e1 instanceof IActionSetDescriptor) {
            String str1 = DialogUtil.removeAccel(((IActionSetDescriptor) e1)
                    .getLabel());
            String str2 = DialogUtil.removeAccel(((IActionSetDescriptor) e2)
                    .getLabel());
            return getComparator().compare(str1, str2);
        }
        return 0;
    }
}
