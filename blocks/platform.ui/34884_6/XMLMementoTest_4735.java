package org.eclipse.ui.tests.api;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.IStickyViewDescriptor;

public final class ViewUtils {

    public static boolean findInStack(IViewPart[] stack, IViewPart target) {
        for (int i = 0; i < stack.length; i++) {
            if (stack[i] == target)
                return true;
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
        for (int i = 0; i < descs.length; i++) {
            if (descs[i].getId().equals(id))
                return true;
        }
        return false;
    }

    protected ViewUtils() {
    }
}
