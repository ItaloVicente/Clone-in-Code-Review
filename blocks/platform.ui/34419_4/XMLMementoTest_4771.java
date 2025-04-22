package org.eclipse.ui.tests.api;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IStickyViewDescriptor;

public final class ViewUtils {

    public static boolean findInStack(IViewPart[] stack, IViewPart target) {
        for (IViewPart element : stack) {
            if (element == target) {
				return true;
			}
        }
        return false;
    }

	public static boolean isCloseable(IViewPart part) {
        return false;
    }

	public static boolean isMoveable(IViewPart part) {
        return false;
    }

    public static boolean isSticky(IViewPart part) {
        String id = part.getSite().getId();
        IStickyViewDescriptor[] descs = PlatformUI.getWorkbench()
                .getViewRegistry().getStickyViews();
        for (IStickyViewDescriptor desc : descs) {
            if (desc.getId().equals(id)) {
				return true;
			}
        }
        return false;
    }

    protected ViewUtils() {
    }
}
