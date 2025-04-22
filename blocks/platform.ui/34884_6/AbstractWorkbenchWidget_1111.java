package org.eclipse.ui.internal.layout;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.internal.IChangeListener;
import org.eclipse.ui.internal.IntModel;
import org.eclipse.ui.internal.RadioMenu;
import org.eclipse.ui.internal.TrimFrame;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchWindow;

public abstract class TrimToolBarBase implements IWindowTrim {
	protected String id;
	protected int orientation;
	protected WorkbenchWindow wbw;
	protected TrimLayout layout;
	protected ToolBarManager tbMgr = null;
	protected ToolItem contextToolItem = null;

	private TrimFrame frame = null;
	private CoolBar cb = null;
	private CoolItem ci = null;
    
	private MenuManager dockMenuManager;
	private ContributionItem dockContributionItem = null;
    private Menu sidesMenu;
	private MenuItem dockCascade;
    private RadioMenu radioButtons;
    private IntModel radioVal = new IntModel(0);
	

    private Listener tbListener = new Listener() {
        @Override
		public void handleEvent(Event event) {
            Point loc = new Point(event.x, event.y);
            if (event.type == SWT.MenuDetect) {
                showToolBarPopup(loc);
            }
        }
    };

    private Listener cbListener = new Listener() {
        @Override
		public void handleEvent(Event event) {
            Point loc = new Point(event.x, event.y);
            if (event.type == SWT.MenuDetect) {
                showDockTrimPopup(loc);
            }
        }
    };
    

    protected TrimToolBarBase(String id, int curSide, WorkbenchWindow wbw) {
    	this.id = id;
    	this.wbw = wbw;
    	this.layout = (TrimLayout) wbw.getTrimManager();
    }

	private void showToolBarPopup(Point loc) {
		Point tbLoc = tbMgr.getControl().toControl(loc);
		contextToolItem = tbMgr.getControl().getItem(tbLoc);
		MenuManager mm = tbMgr.getContextMenuManager();
		if (mm != null) {
			Menu menu = mm.createContextMenu(wbw.getShell());
	        menu.setLocation(loc.x, loc.y);
	        menu.setVisible(true);
		}
	}

    public abstract void initToolBarManager(ToolBarManager mgr);
    
    public abstract void hookControl(ToolBarManager mgr);
    
	private void createControl(int curSide) {
		dispose();
		
    	this.radioVal.set(curSide);
    	
    	orientation = (curSide == SWT.LEFT || curSide == SWT.RIGHT) ? SWT.VERTICAL  : SWT.HORIZONTAL;

    	frame = new TrimFrame(wbw.getShell());
		
		cb = new CoolBar(frame.getComposite(), orientation | SWT.FLAT);
		ci = new CoolItem(cb, SWT.FLAT);

		tbMgr = new ToolBarManager(orientation | SWT.FLAT);
		
		initToolBarManager(tbMgr);
		
		ToolBar tb = tbMgr.createControl(cb);
		ci.setControl(tb);
		
		hookControl(tbMgr);
		
		update(true);
		
    	Cursor dragCursor = getControl().getDisplay().getSystemCursor(SWT.CURSOR_SIZEALL);
    	cb.setCursor(dragCursor);
    	
    	Cursor tbCursor = getControl().getDisplay().getSystemCursor(SWT.CURSOR_ARROW);
    	tb.setCursor(tbCursor);
    	
    	
    	
    	dockMenuManager = new MenuManager();
    	dockContributionItem = getDockingContribution();
        dockMenuManager.add(dockContributionItem);

        tb.addListener(SWT.MenuDetect, tbListener);
        cb.addListener(SWT.MenuDetect, cbListener);
        
        cb.pack(true);
        cb.setVisible(true);
        
        tbMgr.getControl().setVisible(true);
        cb.setVisible(true);
        frame.getComposite().setVisible(true);
    }

    private void handleShowOnChange() {
    	if (getControl() == null)
    		return;
    	
    	layout.removeTrim(this);
    	dock(radioVal.get());
    	layout.addTrim(radioVal.get(), this, null);
    	
    	LayoutUtil.resize(getControl());
	}

	public void update(boolean changed) {
		tbMgr.update(changed);
		
		tbMgr.getControl().pack();
	    Point size = tbMgr.getControl().getSize();
	    Point ps = ci.computeSize (size.x, size.y);
		ci.setPreferredSize (ps);
		ci.setSize(ps);
		cb.pack();
		cb.update();
		LayoutUtil.resize(getControl());
	}
	
	private ContributionItem getDockingContribution() {
    	if (dockContributionItem == null) {
    		dockContributionItem = new ContributionItem() {
    			@Override
				public void fill(Menu menu, int index) {
    				super.fill(menu, index);
    				
    				if (isCloseable()) {
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

	public int getCurrentSide() {
		return radioVal.get();
	}
	
   
	private void handleCloseTrim() {
		handleClose();
	}
	
    public void dispose() {
    	if (getControl() == null || getControl().isDisposed())
    		return;
    	
        if (radioButtons != null) {
            radioButtons.dispose();
        }

        getControl().removeListener(SWT.MenuDetect, cbListener);
        
        tbMgr.dispose();
        tbMgr = null;
        
        getControl().dispose();
        frame = null;
    }


    private void showDockTrimPopup(Point pt) {
        Menu menu = dockMenuManager.createContextMenu(this.getControl());
        menu.setLocation(pt.x, pt.y);
        menu.setVisible(true);
    }

	@Override
	public void dock(int dropSide) {
		createControl(dropSide);
	}

	@Override
	public Control getControl() {
		if (frame == null)
			return null;
		
		return frame.getComposite();
	}

	@Override
	public String getDisplayName() {
		return id;
	}

	@Override
	public int getHeightHint() {
		return getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT, true).y;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public int getValidSides() {
		return SWT.BOTTOM | SWT.TOP | SWT.LEFT | SWT.RIGHT;
	}

	@Override
	public int getWidthHint() {
		return getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x;
	}

	@Override
	public void handleClose() {
	}

	@Override
	public boolean isCloseable() {
		return false;
	}

	@Override
	public boolean isResizeable() {
		return false;
	}	    
}
