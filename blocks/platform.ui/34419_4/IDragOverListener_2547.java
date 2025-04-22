package org.eclipse.ui.internal.dnd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.util.Geometry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tracker;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.DragCursors;

public class DragUtil {
    private static final String DROP_TARGET_ID = "org.eclipse.ui.internal.dnd.dropTarget"; //$NON-NLS-1$

    private static TestDropLocation forcedDropTarget = null;
    
    private static List defaultTargets = new ArrayList();

    public static void addDragTarget(Control control, IDragOverListener target) {
        if (control == null) {
            defaultTargets.add(target);
        } else {
            List targetList = getTargetList(control);

            if (targetList == null) {
                targetList = new ArrayList(1);
            }
            targetList.add(target);
            control.setData(DROP_TARGET_ID, targetList);
        }
    }

    private static List getTargetList(Control control) {
        List result = (List) control.getData(DROP_TARGET_ID);
        return result;
    }

    public static void removeDragTarget(Control control,
            IDragOverListener target) {
        if (control == null) {
            defaultTargets.remove(target);
        } else {
            List targetList = getTargetList(control);
            if (targetList != null) {
                targetList.remove(target);
                if (targetList.isEmpty()) {
                    control.setData(DROP_TARGET_ID, null);
                }
            }
        }
    }

    public static Rectangle getDisplayBounds(Control boundsControl) {
        Control parent = boundsControl.getParent();
        if (parent == null || boundsControl instanceof Shell) {
            return boundsControl.getBounds();
        }

        return Geometry.toDisplay(parent, boundsControl.getBounds());
    }

    public static boolean performDrag(final Object draggedItem,
            Rectangle sourceBounds, Point initialLocation, boolean allowSnapping) {

        IDropTarget target = dragToTarget(draggedItem, sourceBounds,
                initialLocation, allowSnapping);

        if (target == null) {
            return false;
        }

        target.drop();

        if (target!= null && target instanceof IDropTarget2) {
        	((IDropTarget2)target).dragFinished(true);
        }

        return true;
    }

    public static boolean dragTo(Display display, Object draggedItem,
            Point finalLocation, Rectangle dragRectangle) {
        Control currentControl = SwtUtil.findControl(display, finalLocation);

        IDropTarget target = getDropTarget(currentControl, draggedItem,
                finalLocation, dragRectangle);

        if (target == null) {
            return false;
        }

        target.drop();

        return true;
    }

    public static void forceDropLocation(TestDropLocation forcedLocation) {
        forcedDropTarget = forcedLocation;
    }
    
    static IDropTarget dragToTarget(final Object draggedItem,
            final Rectangle sourceBounds, final Point initialLocation,
            final boolean allowSnapping) {
        final Display display = Display.getCurrent();

        if (forcedDropTarget != null) {
            Point location = forcedDropTarget.getLocation();

            Control currentControl = SwtUtil.findControl(forcedDropTarget.getShells(), location);
            return getDropTarget(currentControl, draggedItem, location,
                    sourceBounds);
        }

        final Tracker tracker = new Tracker(display, SWT.NULL);
        tracker.setStippled(true);

        tracker.addListener(SWT.Move, new Listener() {
            @Override
			public void handleEvent(final Event event) {
                display.syncExec(new Runnable() {
                    @Override
					public void run() {
                        Point location = new Point(event.x, event.y);

                    	IDropTarget target = null;
                    		
                        Control targetControl = display.getCursorControl();

                        target = getDropTarget(targetControl,
                                draggedItem, location,
                                tracker.getRectangles()[0]);

                        Rectangle snapTarget = null;
                        if (target != null) {
                            snapTarget = target.getSnapRectangle();

                            tracker.setCursor(target.getCursor());
                        } else {
                            tracker.setCursor(DragCursors
                                    .getCursor(DragCursors.INVALID));
                        }

                        if (allowSnapping) {
                            if (snapTarget == null) {
                                snapTarget = new Rectangle(sourceBounds.x
                                        + location.x - initialLocation.x,
                                        sourceBounds.y + location.y
                                                - initialLocation.y,
                                        sourceBounds.width, sourceBounds.height);
                            }

                            Rectangle[] currentRectangles = tracker.getRectangles();

                            if (!(currentRectangles.length == 1 && currentRectangles[0]
                                    .equals(snapTarget))) {
                                tracker.setRectangles(new Rectangle[] { snapTarget });
                            }
                        }
                    }
                });
            }
        });

        IDropTarget target = null;
        Control startControl = display.getCursorControl();
        
        if (startControl != null && allowSnapping) {
            target = getDropTarget(startControl,
                draggedItem, initialLocation,
                sourceBounds);
        }

        Rectangle startRect = sourceBounds;
        if (target != null) {
            Rectangle rect = target.getSnapRectangle();
            
            if (rect != null) {
                startRect = rect;
            }

            tracker.setCursor(target.getCursor());
        } 
        
        if (startRect != null) {
            tracker.setRectangles(new Rectangle[] { Geometry.copy(startRect)});
        }

        
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        if (shell != null) {
            shell.setCapture(true);
        }

        boolean trackingOk = tracker.open();

        if (shell != null) {
            shell.setCapture(false);
        }

        
        IDropTarget dropTarget = null;
        Point finalLocation = display.getCursorLocation();
        Control targetControl = display.getCursorControl();
        dropTarget = getDropTarget(targetControl, draggedItem,
                finalLocation, tracker.getRectangles()[0]);

        tracker.dispose();
        
        if (trackingOk) {
        	return dropTarget;
        }
        else if (dropTarget!= null && dropTarget instanceof IDropTarget2) {
        	((IDropTarget2)dropTarget).dragFinished(false);
        }
        
        return null;
    }
    
    private static IDropTarget getDropTarget(List toSearch,
            Control mostSpecificControl, Object draggedObject, Point position,
            Rectangle dragRectangle) {
        if (toSearch == null) {
            return null;
        }

        Iterator iter = toSearch.iterator();
        while (iter.hasNext()) {
            IDragOverListener next = (IDragOverListener) iter.next();

            IDropTarget dropTarget = next.drag(mostSpecificControl,
                    draggedObject, position, dragRectangle);

            if (dropTarget != null) {
                return dropTarget;
            }
        }

        return null;
    }

    public static IDropTarget getDropTarget(Control toSearch,
            Object draggedObject, Point position, Rectangle dragRectangle) {
        for (Control current = toSearch; current != null; current = current
                .getParent()) {
            IDropTarget dropTarget = getDropTarget(getTargetList(current),
                    toSearch, draggedObject, position, dragRectangle);

            if (dropTarget != null) {
                return dropTarget;
            }

            if (current instanceof Shell) {
                break;
            }
        }

        return getDropTarget(defaultTargets, toSearch, draggedObject, position,
                dragRectangle);
    }

    public static Point getEventLoc(Event event) {
        Control ctrl = (Control) event.widget;
        return ctrl.toDisplay(new Point(event.x, event.y));
    }

}
