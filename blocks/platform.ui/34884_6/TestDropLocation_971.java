package org.eclipse.ui.internal.dnd;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

public class SwtUtil {

    private SwtUtil() {

    }

    public static boolean isDisposed(Control toTest) {
        return toTest == null || toTest.isDisposed();
    }

    public static Control controlThatCovers(Control toTest) {
        return controlThatCovers(toTest, DragUtil.getDisplayBounds(toTest));
    }
    
    private static Control controlThatCovers(Control toTest, Rectangle testRegion) {
        Composite parent = toTest.getParent();
        
        if (parent == null || toTest instanceof Shell) {
            return null;
        }
       
        Control[] children = parent.getChildren();
        for (int i = 0; i < children.length; i++) {
            Control control = children[i];
            
            if (control == toTest) {
                break;
            }
            
            if (!control.isVisible()) {
                continue;
            }
            
            Rectangle nextBounds = DragUtil.getDisplayBounds(control);
            
            if (nextBounds.intersects(testRegion)) {
                return control;
            }
        }
        
        return controlThatCovers(parent, testRegion);
    }
    
    public static boolean isChild(Control potentialParent, Control childToTest) {
        if (childToTest == null) {
            return false;
        }

        if (childToTest == potentialParent) {
            return true;
        }

        return isChild(potentialParent, childToTest.getParent());
    }
    
    public static boolean isFocusAncestor(Control potentialParent) {
		if (potentialParent == null)
			return false;
        Control focusControl = Display.getCurrent().getFocusControl();
        if (focusControl == null) {
            return false;
        }
        return isChild(potentialParent, focusControl);
    }

    public static Control findControl(Display displayToSearch,
            Point locationToFind) {
        Shell[] shells = displayToSearch.getShells();

        return findControl(shells, locationToFind);
    }

    public static Control findControl(Control[] toSearch, Point locationToFind) {
        for (int idx = toSearch.length - 1; idx >= 0; idx--) {
            Control next = toSearch[idx];

            if (!next.isDisposed() && next.isVisible()) {

                Rectangle bounds = DragUtil.getDisplayBounds(next);

                if (bounds.contains(locationToFind)) {
                    if (next instanceof Composite) {
                        Control result = findControl((Composite) next,
                                locationToFind);

                        if (result != null) {
                            return result;
                        }
                    }

                    return next;
                }
            }
        }

        return null;
    }

    public static Control[] getAncestors(Control theControl) {
        return getAncestors(theControl, 1);
    }
    
    private static Control[] getAncestors(Control theControl, int children) {
        Control[] result;
        
        if (theControl.getParent() == null) {
            result = new Control[children]; 
        } else {
            result = getAncestors(theControl.getParent(), children + 1);
        }
        
        result[result.length - children] = theControl;
        
        return result;
    }
    
    public static Control findCommonAncestor(Control control1, Control control2) {
        Control[] control1Ancestors = getAncestors(control1);
        Control[] control2Ancestors = getAncestors(control2);
        
        Control mostSpecific = null;
        
        for (int idx = 0; idx < Math.min(control1Ancestors.length, control2Ancestors.length); idx++) {
            Control control1Ancestor = control1Ancestors[idx];
            if (control1Ancestor == control2Ancestors[idx]) {
                mostSpecific = control1Ancestor;
            } else {
                break;
            }
        }
        
        return mostSpecific;
    }
    
    public static Control findControl(Composite toSearch, Point locationToFind) {
        Control[] children = toSearch.getChildren();

        return findControl(children, locationToFind);
    }

    public static boolean intersectsAnyMonitor(Display display,
            Rectangle someRectangle) {
        Monitor[] monitors = display.getMonitors();
    
        for (int idx = 0; idx < monitors.length; idx++) {
            Monitor mon = monitors[idx];
    
            if (mon.getClientArea().intersects(someRectangle)) {
                return true;
            }
        }
    
        return false;
    }

}
