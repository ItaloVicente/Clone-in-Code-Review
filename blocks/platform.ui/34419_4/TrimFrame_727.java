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

	
    private final class ActualTrimDropTarget implements IDropTarget2 {
        public IWindowTrim draggedTrim;
        
    	private DragBorder border = null;
    	private int dockedArea;
        
    	private int cursorAreaId;
        private int initialAreaId;
        private IWindowTrim initialInsertBefore;        
		private Rectangle initialLocation;

        private ActualTrimDropTarget() {
            super();

            draggedTrim = null;
            dockedArea = SWT.NONE;
            
            initialAreaId = SWT.NONE;
            initialInsertBefore = null;
        }
        
        public void startDrag(IWindowTrim trim) {
        	if (draggedTrim != trim) {
            	draggedTrim = trim;
            	
            	initialAreaId = layout.getTrimAreaId(draggedTrim.getControl());
            	
            	initialInsertBefore = getInsertBefore(initialAreaId, draggedTrim);
            	
            	initialLocation = DragUtil.getDisplayBounds(draggedTrim.getControl());
            	            	
            	dockedArea = initialAreaId;
        	}
        }
                
        private int getTrimArea(Point pos) {
        	int areaId = getTrimArea(pos, 0);
        	
        	if (areaId == SWT.NONE) {
				areaId = getTrimArea(pos, TrimDragPreferences.getThreshold());
			}
        	
        	return areaId;
        }
        
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
        
        private void trackOutsideTrimArea(Point pos) {
        	if (dockedArea != SWT.NONE) {
				undock();
			}
    		
    		border.setLocation(pos, SWT.BOTTOM);
        }
                
        private int getValidSides() {
        	int result = draggedTrim.getValidSides();
        	if (result == SWT.NONE) {
				return result;
			}

        	return SWT.TOP | SWT.BOTTOM | SWT.LEFT | SWT.RIGHT;
        }

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

        private void undock() {
        	layout.removeTrim(draggedTrim);
           	LayoutUtil.resize(draggedTrim.getControl());
           	
        	draggedTrim.dock(initialAreaId);
        	draggedTrim.getControl().setSize(initialLocation.width, initialLocation.height);
           	
        	boolean wantsFrame = !(draggedTrim instanceof TrimToolBarBase);
           	border = new DragBorder(windowComposite, draggedTrim.getControl(), wantsFrame);

           	dockedArea = SWT.NONE;
        }
        
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
