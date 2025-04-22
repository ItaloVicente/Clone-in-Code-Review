package org.eclipse.ui.internal.layout;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.internal.IChangeListener;
import org.eclipse.ui.internal.IntModel;
import org.eclipse.ui.internal.RadioMenu;
import org.eclipse.ui.internal.WindowTrimProxy;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.dnd.DragUtil;

public class TrimCommonUIHandle extends Composite {
	private TrimLayout  layout;
    private IWindowTrim trim;
	private Control     toDrag;
	private int orientation;

	private CoolBar cb = null;
	private CoolItem ci = null;
	private static int horizontalHandleSize = -1;
	private static int verticalHandleSize = -1;
	
	private MenuManager dockMenuManager;
	private ContributionItem dockContributionItem = null;
    private Menu sidesMenu;
	private MenuItem dockCascade;
    private RadioMenu radioButtons;
    private IntModel radioVal = new IntModel(0);
	
    

    private Listener menuListener = new Listener() {
        @Override
		public void handleEvent(Event event) {
            Point loc = new Point(event.x, event.y);
            if (event.type == SWT.MenuDetect) {
                showDockTrimPopup(loc);
            }
        }
    };

    private ControlListener controlListener = new ControlListener() {
		@Override
		public void controlMoved(ControlEvent e) {
		}

		@Override
		public void controlResized(ControlEvent e) {
			if (e.widget instanceof TrimCommonUIHandle) {
				TrimCommonUIHandle ctrl = (TrimCommonUIHandle) e.widget;
		        Point size = ctrl.getSize();

		        cb.setSize(size);
		        ci.setSize(size);
		        ci.setPreferredSize(size);
		        cb.layout(true);
			}
		}
    };

    public TrimCommonUIHandle(TrimLayout layout, IWindowTrim trim, int curSide) {
    	super(trim.getControl().getParent(), SWT.NONE);
    	
    	setup(layout, trim, curSide);
		
        addControlListener(controlListener);
    }

	public void setup(TrimLayout layout, IWindowTrim trim, int curSide) {    	
    	this.layout = layout;
    	this.trim = trim;
    	this.toDrag = trim.getControl();
    	this.radioVal.set(curSide);
    	
    	orientation = (curSide == SWT.LEFT || curSide == SWT.RIGHT) ? SWT.VERTICAL  : SWT.HORIZONTAL;
    	
        insertCoolBar(orientation);
        
		createWindowTrimProxy();
       	
    	setDragCursor();
    	
    	
    	dockMenuManager = new MenuManager();
    	dockContributionItem = getDockingContribution();
        dockMenuManager.add(dockContributionItem);

        cb.addListener(SWT.MenuDetect, menuListener);
        
        setVisible(true);
    }

    private void handleShowOnChange() {
    	layout.removeTrim(trim);
    	trim.dock(radioVal.get());
    	layout.addTrim(radioVal.get(), trim, null);
    	
    	LayoutUtil.resize(trim.getControl());
	}

	private void createWindowTrimProxy() {
		WindowTrimProxy proxy = new WindowTrimProxy(this, "NONE", "NONE", //$NON-NLS-1$ //$NON-NLS-2$
				SWT.TOP | SWT.BOTTOM | SWT.LEFT | SWT.RIGHT, false);

		if (orientation == SWT.HORIZONTAL) {
			proxy.setWidthHint(getHandleSize());
			proxy.setHeightHint(0);
		}
		else {
			proxy.setWidthHint(0);
			proxy.setHeightHint(getHandleSize());
		}
		
		setLayoutData(proxy);
	}
	
	private int getHandleSize() {
		if (orientation == SWT.HORIZONTAL && horizontalHandleSize != -1) {
			return horizontalHandleSize;
		}
				
		if (orientation == SWT.VERTICAL && verticalHandleSize != -1) {
			return verticalHandleSize;
		}
				
		CoolBar bar = new CoolBar (trim.getControl().getParent(), orientation);
		
		CoolItem item = new CoolItem (bar, SWT.NONE);
		
		Label ctrl = new Label (bar, SWT.PUSH);
		ctrl.setText ("Button 1"); //$NON-NLS-1$
	    Point size = ctrl.computeSize (SWT.DEFAULT, SWT.DEFAULT);
		
	    Point ps = item.computeSize (size.x, size.y);
		item.setPreferredSize (ps);
		item.setControl (ctrl);

		bar.pack ();

		Point bl = ctrl.getLocation();
		Point cl = bar.getLocation();

		ctrl.dispose();
		item.dispose();
		bar.dispose();
	
		int length;
		if (orientation == SWT.HORIZONTAL) {
			length = bl.x - cl.x;
			horizontalHandleSize = length;
		}
		else {
			length = bl.y - cl.y;
			verticalHandleSize = length;
		}
		
		return length;
	}
	
	public void insertCoolBar(int orientation) {
		if (cb != null) {
			ci.dispose();
			cb.dispose();
		}
		
		cb = new CoolBar(this, orientation | SWT.FLAT);
		cb.setLocation(0,0);
		ci = new CoolItem(cb, SWT.FLAT);
		
		Composite comp = new Composite(cb, SWT.NONE);
		ci.setControl(comp);
	}
	
	private void setDragCursor() {
    	Cursor dragCursor = toDrag.getDisplay().getSystemCursor(SWT.CURSOR_SIZEALL);
    	setCursor(dragCursor);
	}
	
	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		Point ctrlPrefSize = trim.getControl().computeSize(wHint, hHint);
		if (orientation == SWT.HORIZONTAL) {
			return new Point(getHandleSize(), ctrlPrefSize.y);
		}
		
		return new Point(ctrlPrefSize.x, getHandleSize());
	}
	
	public ContributionItem getDockingContribution() {
    	if (dockContributionItem == null) {
    		dockContributionItem = new ContributionItem() {
    			@Override
				public void fill(Menu menu, int index) {
    				super.fill(menu, index);
    				
    				if (trim.isCloseable()) {
	    				MenuItem closeItem = new MenuItem(menu, SWT.PUSH, index++);
	    				closeItem.setText(WorkbenchMessages.TrimCommon_Close);
	    				
	    				closeItem.addSelectionListener(new SelectionListener() {
							@Override
							public void widgetSelected(SelectionEvent e) {
								handleCloseTrim();
							}

							@Override
							public void widgetDefaultSelected(SelectionEvent e) {
							}
	    				});

	    				new MenuItem(menu, SWT.SEPARATOR, index++);
    				}
    				
    				
    				dockCascade = new MenuItem(menu, SWT.CASCADE, index++);
    				{
    					dockCascade.setText(WorkbenchMessages.TrimCommon_DockOn); 
    					
    					sidesMenu = new Menu(dockCascade);
    					radioButtons = new RadioMenu(sidesMenu, radioVal);
    					
						radioButtons.addMenuItem(WorkbenchMessages.TrimCommon_Top, new Integer(SWT.TOP));
						radioButtons.addMenuItem(WorkbenchMessages.TrimCommon_Bottom, new Integer(SWT.BOTTOM));
						radioButtons.addMenuItem(WorkbenchMessages.TrimCommon_Left, new Integer(SWT.LEFT));
						radioButtons.addMenuItem(WorkbenchMessages.TrimCommon_Right, new Integer(SWT.RIGHT));
    					
    					dockCascade.setMenu(sidesMenu);
    				}

    		    	radioVal.addChangeListener(new IChangeListener() {
    					@Override
						public void update(boolean changed) {
    						if (changed) {
								handleShowOnChange();
							}
    					}
    		    	});
    				
    			}
    		};
    	}
    	return dockContributionItem;
    }

   
	private void handleCloseTrim() {
		layout.removeTrim(trim);
		trim.handleClose();
	}
	
    @Override
	public void dispose() {
        if (radioButtons != null) {
            radioButtons.dispose();
        }

        removeControlListener(controlListener);
        removeListener(SWT.MenuDetect, menuListener);
        
        super.dispose();
    }

    protected void startDraggingTrim(Point position) {
    	Rectangle fakeBounds = new Rectangle(100000, 0,0,0);
        DragUtil.performDrag(trim, fakeBounds, position, true);
    }

    private void showDockTrimPopup(Point pt) {
        Menu menu = dockMenuManager.createContextMenu(toDrag);
        menu.setLocation(pt.x, pt.y);
        menu.setVisible(true);
    }	    
}
