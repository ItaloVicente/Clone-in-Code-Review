/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.util.Geometry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.internal.dnd.DragBorder;
import org.eclipse.ui.internal.dnd.DragUtil;
import org.eclipse.ui.internal.dnd.IDragOverListener;
import org.eclipse.ui.internal.dnd.IDropTarget;
import org.eclipse.ui.internal.dnd.IDropTarget2;
import org.eclipse.ui.internal.layout.IWindowTrim;
import org.eclipse.ui.internal.layout.LayoutUtil;
import org.eclipse.ui.internal.layout.TrimDescriptor;
import org.eclipse.ui.internal.layout.TrimLayout;
import org.eclipse.ui.internal.layout.TrimToolBarBase;

/**
 */
/*package*/class TrimDropTarget implements IDragOverListener {

    private final class ActualTrimDropTarget implements IDropTarget2 {
        public IWindowTrim draggedTrim;

    	private DragBorder border = null;
    	private int dockedArea;

    	private int cursorAreaId;
        private int initialAreaId;
        private IWindowTrim initialInsertBefore;
		private Rectangle initialLocation;

        /**
         * Constructor
         */
        private ActualTrimDropTarget() {
            super();

            draggedTrim = null;
            dockedArea = SWT.NONE;

            initialAreaId = SWT.NONE;
            initialInsertBefore = null;
        }

        /**
         * This method is used to delineate separate trims dragging events. The -first- drag
         * event will set this and then it will remain constant until the drag gesture is done;
         * either by dropping or escaping. Once the gesture is finished the trim value is set
         * back to 'null'.
         *
         * @param trim The trim item currently being dragged.
         */
        public void startDrag(IWindowTrim trim) {
        	if (draggedTrim != trim) {
            	draggedTrim = trim;

            	initialAreaId = layout.getTrimAreaId(draggedTrim.getControl());

            	initialInsertBefore = getInsertBefore(initialAreaId, draggedTrim);

            	initialLocation = DragUtil.getDisplayBounds(draggedTrim.getControl());

            	dockedArea = initialAreaId;
        	}
        }

        /**
         * Determine the trim area from the point. To avoid clashing at the 'corners' due to extending the trim area's
         * rectangles we first ensure that the point is not actually -within- a trim area before we check the extended
         * rectangles.
         *
         * @param pos The current cursor pos
         * @return the Trim area that the cursor is in or SWT.NONE if the point is not in an area
         */
        private int getTrimArea(Point pos) {
        	int areaId = getTrimArea(pos, 0);

        	if (areaId == SWT.NONE) {
				areaId = getTrimArea(pos, TrimDragPreferences.getThreshold());
			}

        	return areaId;
        }

        /**
         * Checks the trims areas against the given position. Each trim area is 'extended' into
         * the workbench page by the value of <code>extendedBoundaryWidth</code> before the checking
         * takes place.
         *
         * @param pos The point to check against
         * @param extendedBoundaryWidth The amount to extend the trim area's 'inner' edge by
         *
         * @return The trim area or SWT.NONE if the point is not within any extended trim area's rect.
         */
        private int getTrimArea(Point pos, int extendedBoundaryWidth) {
        	int[] areaIds = layout.getAreaIds();
        	for (int i = 0; i < areaIds.length; i++) {
				Rectangle trimRect = layout.getTrimRect(windowComposite, areaIds[i]);
				trimRect = Geometry.toControl(windowComposite, trimRect);

				if ( (areaIds[i] & getValidSides()) != SWT.NONE) {
		        	switch (areaIds[i]) {
					case SWT.LEFT:
						trimRect.width += extendedBoundaryWidth;

						if (pos.y >= trimRect.y &&
							pos.y <= (trimRect.y+trimRect.height) &&
							pos.x <= (trimRect.x+trimRect.width)) {
							return areaIds[i];
						}
						break;
					case SWT.RIGHT:
						trimRect.x -= extendedBoundaryWidth;
						trimRect.width += extendedBoundaryWidth;

						if (pos.y >= trimRect.y &&
							pos.y <= (trimRect.y+trimRect.height) &&
							pos.x >= trimRect.x) {
							return areaIds[i];
						}
						break;
					case SWT.TOP:
						trimRect.height += extendedBoundaryWidth;

						if (pos.x >= trimRect.x &&
							pos.x <= (trimRect.x+trimRect.width) &&
							pos.y <= (trimRect.y+trimRect.height)) {
							return areaIds[i];
						}
						break;
					case SWT.BOTTOM:
						trimRect.y -= extendedBoundaryWidth;
						trimRect.height += extendedBoundaryWidth;

						if (pos.x >= trimRect.x &&
							pos.x <= (trimRect.x+trimRect.width) &&
							pos.y >= trimRect.y) {
							return areaIds[i];
						}
						break;
		        	}
				}
			}

        	return SWT.NONE;
        }

        /**
         * Determine the window trim that the currently dragged trim should be inserted
         * before.
         * @param areaId The area id that is being checked
         * @param pos The position used to determine the correct insertion trim
         * @return The trim to 'dock' the draggedTrim before
         */
        private IWindowTrim getInsertBefore(int areaId, Point pos) {
        	boolean isHorizontal = (areaId == SWT.TOP) || (areaId == SWT.BOTTOM);

        	List tDescs = layout.getTrimArea(areaId).getDescriptors();
        	for (Iterator iter = tDescs.iterator(); iter.hasNext();) {
				TrimDescriptor desc = (TrimDescriptor) iter.next();

				if (desc.getTrim() == draggedTrim) {
					continue;
				}

				Rectangle bb = desc.getCache().getControl().getBounds();
				Point center = Geometry.centerPoint(bb);
				if (isHorizontal) {
					if (pos.x < center.x) {
						return desc.getTrim();
					}
				}
				else {
					if (pos.y < center.y) {
						return desc.getTrim();
					}
				}
			}

        	return null;
        }

        /**
         * Returns the trim that is 'before' the given trim in the given area
         *
         * @param areaId The areaId of the trim
         * @param trim The trim to find the element after
         *
         * @return The trim that the given trim is 'before'
         */
        private IWindowTrim getInsertBefore(int areaId, IWindowTrim trim) {
        	List tDescs = layout.getTrimArea(areaId).getDescriptors();
        	for (Iterator iter = tDescs.iterator(); iter.hasNext();) {
				TrimDescriptor desc = (TrimDescriptor) iter.next();
				if (desc.getTrim() == trim) {
					if (iter.hasNext()) {
						desc = (TrimDescriptor) iter.next();
						return desc.getTrim();
					}
					return null;
				}
			}

        	return null;
        }

        /**
         * Recalculates the drop information based on the current cursor pos.
         *
         * @param pos The cursor position
         */
        public void track(Point pos) {
        	Rectangle r = new Rectangle(pos.x, pos.y, 1,1);
        	r = Geometry.toControl(windowComposite, r);
        	pos.x = r.x;
        	pos.y = r.y;

        	cursorAreaId = getTrimArea(pos);

        	if (cursorAreaId != SWT.NONE) {
				trackInsideTrimArea(pos);
			} else {
				trackOutsideTrimArea(pos);
			}
        }

        /**
         * Perform the feedback used when the cursor is 'inside' a particular trim area.
         * The current implementation will place the dragged trim into the trim area at
         * the location determined by the supplied point.
         *
         * @param pos The point to use to determine where in the trim area the dragged trim
         * should be located.
         */
        private void trackInsideTrimArea(Point pos) {
        	int newArea = getTrimArea(pos);
        	IWindowTrim newInsertBefore = getInsertBefore(newArea, pos);

        	boolean shouldDock = dockedArea == SWT.NONE;

        	if (dockedArea != SWT.NONE) {
	        	IWindowTrim curInsertBefore = getInsertBefore(dockedArea, draggedTrim);

	        	shouldDock = dockedArea != newArea || curInsertBefore != newInsertBefore;
        	}

        	if (shouldDock) {
        		dock(newArea, newInsertBefore);
        	}
        }

        /**
         * Provide the dragging feedback when the cursor is -not- explicitly inside
         * a particular trim area.
         *
         */
        private void trackOutsideTrimArea(Point pos) {
        	if (dockedArea != SWT.NONE) {
				undock();
			}

    		border.setLocation(pos, SWT.BOTTOM);
        }

        /**
         * Return the set of valid sides that a piece of trim can be docked on. We
         * arbitrarily extend this to include any areas that won't cause a change in orientation
         *
         * @return The extended drop 'side' set
         */
        private int getValidSides() {
        	int result = draggedTrim.getValidSides();
        	if (result == SWT.NONE) {
				return result;
			}

        	return SWT.TOP | SWT.BOTTOM | SWT.LEFT | SWT.RIGHT;
        }

        /**
         * The user either cancelled the drag or tried to drop the trim in an invalid
         * area...put the trim back in the last location it was in
         */
        private void redock() {
        	Rectangle startRect = DragUtil.getDisplayBounds(draggedTrim.getControl());
    		AnimationEngine.createTweakedAnimation(windowComposite.getShell(), 400, startRect, initialLocation);

            dock(initialAreaId, initialInsertBefore);
        }

        @Override
		public void drop() {
        	if (dockedArea == SWT.NONE) {
				redock();
			}
        }

        /**
         * Remove the trim frmo its current 'docked' location and attach it
         * to the cursor...
         */
        private void undock() {
        	layout.removeTrim(draggedTrim);
           	LayoutUtil.resize(draggedTrim.getControl());

        	draggedTrim.dock(initialAreaId);
        	draggedTrim.getControl().setSize(initialLocation.width, initialLocation.height);

        	boolean wantsFrame = !(draggedTrim instanceof TrimToolBarBase);
           	border = new DragBorder(windowComposite, draggedTrim.getControl(), wantsFrame);

           	dockedArea = SWT.NONE;
        }

        /**
         * Return the 'undocked' trim to its previous location in the layout
         */
        private void dock(int areaId, IWindowTrim insertBefore) {
        	if (border != null) {
				border.dispose();
				border = null;
        	}

			draggedTrim.dock(areaId);

            layout.addTrim(areaId, draggedTrim, insertBefore);
           	LayoutUtil.resize(draggedTrim.getControl());

           	dockedArea = areaId;
        }

        @Override
		public Cursor getCursor() {
        	if (cursorAreaId == SWT.NONE) {
				return windowComposite.getDisplay().getSystemCursor(SWT.CURSOR_NO);
			}

        	return windowComposite.getDisplay().getSystemCursor(SWT.CURSOR_SIZEALL);
        }

        @Override
		public Rectangle getSnapRectangle() {
        	return new Rectangle(100000, 0,0,0);
        }

		@Override
		public void dragFinished(boolean dropPerformed) {
			if (!dropPerformed && dockedArea == SWT.NONE) {
				redock();
			}

			draggedTrim = null;
		}
    }

    private ActualTrimDropTarget dropTarget;

    private TrimLayout layout;
    private Composite windowComposite;

    /**
     * Create a new drop target capable of accepting IWindowTrim items
     *
     * @param someComposite The control owning the TrimLayout
     * @param theWindow the workbenchWindow
     */
    public TrimDropTarget(Composite someComposite, WorkbenchWindow theWindow) {
        layout = (TrimLayout) someComposite.getLayout();
        windowComposite = someComposite;

        dropTarget = new ActualTrimDropTarget();
    }

    @Override
	public IDropTarget drag(Control currentControl, Object draggedObject,
            Point position, final Rectangle dragRectangle) {

    	if (!(draggedObject instanceof IWindowTrim)) {
			return null;
		}

    	IWindowTrim trim = (IWindowTrim) draggedObject;
    	if (trim.getControl().getShell() != windowComposite.getShell()) {
			return null;
		}

    	if (dropTarget.draggedTrim == null) {
    		dropTarget.startDrag(trim);
    	}

    	dropTarget.track(position);

    	windowComposite.getDisplay().update();

		return dropTarget;
    }
}
