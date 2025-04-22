/*******************************************************************************
 * Copyright (c) 2007, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
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

/**
 * This control provides common UI functionality for trim elements
 * that wish to use a ToolBarManager-based implementation.
 * <p>
 * It provides the following features:
 * <p>
 * Drag affordance and handling:
 * <ol>
 * <li>The ToolBar is contained within a CoolBar/Item to provide the same
 * drag handle affordance as the rest of the trim
 * <li>Drag handling is provided to allow rearrangement within a trim side or
 * to other sides, depending on the values returned by <code>IWindowTrim.getValidSides</code></li>
 * </ol>
 * </p>
 * <p>
 * Context Menu:
 * <ol>
 * <li>A "Dock on" menu item is provided to allow changing the side, depending on the values returned by
 * <code>IWindowTrim.getValidSides</code></li>
 * <li>A "Close" menu item is provided to allow the User to close (hide) the trim element,
 * based on the value returned by <code>IWindowTrim.isCloseable</code>
 * </ol>
 * </p>
 * <p>
 * @since 3.3
 * </p>
 */
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

	/*
	 * Listeners...
	 */

    private Listener tbListener = new Listener() {
        @Override
		public void handleEvent(Event event) {
            Point loc = new Point(event.x, event.y);
            if (event.type == SWT.MenuDetect) {
                showToolBarPopup(loc);
            }
        }
    };

    /**
     * This listener brings up the context menu
     */
    private Listener cbListener = new Listener() {
        @Override
		public void handleEvent(Event event) {
            Point loc = new Point(event.x, event.y);
            if (event.type == SWT.MenuDetect) {
                showDockTrimPopup(loc);
            }
        }
    };


    /**
     * Create a new trim UI handle for a particular IWindowTrim item
     *
     * @param layout the TrimLayout we're being used in
     * @param trim the IWindowTrim we're acting on behalf of
     * @param curSide  the SWT side that the trim is currently on
     */
    protected TrimToolBarBase(String id, int curSide, WorkbenchWindow wbw) {
    	this.id = id;
    	this.wbw = wbw;
    	this.layout = (TrimLayout) wbw.getTrimManager();
    }

    /**
	 * @param loc
	 */
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

	/**
     * Initialize the ToolBarManger for this instance. We create a
     * new ToolBarManager whenever we need to and this gives the
     * derived class a chance to define the ICI's and context
     * menu...
     *
     * @param mgr The manager to initialize
     */
    public abstract void initToolBarManager(ToolBarManager mgr);

    /**
     * Hook any necessary listeners to the new ToolBar instance.
     * <p>
     * NOTE: Clients should add a dispose listener if necessary to
     * unhook listeners added through this call.
     * </p>
     * @param mgr The ToolBarManager whose control is to be hooked
     */
    public abstract void hookControl(ToolBarManager mgr);

	/**
	 * Set up the trim with its cursor, drag listener, context menu and menu listener.
	 * This method can also be used to 'recycle' a trim handle as long as the new handle
	 * is for trim under the same parent as it was originally used for.
	 */
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

    /**
     * Handle the event generated when a User selects a new side to
     * dock this trim on using the context menu
     */
    private void handleShowOnChange() {
    	if (getControl() == null)
    		return;

    	layout.removeTrim(this);
    	dock(radioVal.get());
    	layout.addTrim(radioVal.get(), this, null);

    	LayoutUtil.resize(getControl());
	}

	/**
	 * Force the toobar to re-synch to the model
	 * @param changed true if changes have occurred in the structure
	 */
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

	/**
	 * Construct (if necessary) a context menu contribution item and return it. This
	 * is explicitly <code>public</code> so that trim elements can retrieve the item
	 * and add it into their own context menus if desired.
	 *
	 * @return The contribution item for the handle's context menu.
	 */
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

	/**
	 * @return The side the trm is currently on
	 */
	public int getCurrentSide() {
		return radioVal.get();
	}

	/**
	 * Test Hook: Bring up a dialog that allows the user to
	 * modify the trimdragging GUI preferences.
	 */

	/**
	 * Handle the event generated when the "Close" item is
	 * selected on the context menu. This removes the associated
	 * trim and calls back to the IWidnowTrim to inform it that
	 * the User has closed the trim.
	 */
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


    /**
     * Shows the popup menu for an item in the fast view bar.
     */
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
