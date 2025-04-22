/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.presentation.sidewinder;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.examples.presentation.wrappedtabs.ProxyControl;
import org.eclipse.ui.presentations.IPartMenu;
import org.eclipse.ui.presentations.IPresentablePart;
import org.eclipse.ui.presentations.IStackPresentationSite;
import org.eclipse.ui.presentations.PresentationUtil;
import org.eclipse.ui.presentations.StackDropResult;
import org.eclipse.ui.presentations.StackPresentation;

public class SideWinderItemViewPresentation extends StackPresentation {

	private static final String PART_DATA = "part";
	
	private static final int TITLE_HEIGHT = 22;
	
	private boolean activeFocus = false;
	
	/**
	 * Main widget for the presentation
	 */
	private Composite presentationControl;
	private Composite titleArea;
	private Composite clientArea;
	private Composite statusLineArea;
	private ViewForm contentArea;
	private ProxyControl toolbarProxy;
	
	/**
	 * Currently selected part
	 */
	private IPresentablePart current;
		
	/**
	 * close button
	 */
	private ToolItem close;
	
	/**
	 * View menu button
	 */
	private ToolItem viewMenu;
	
	/**
	 * Minimize button
	 */
	private ToolItem minView;
	
	
	private int style = SWT.VERTICAL | SWT.LEFT;
	
	private boolean titleAreaHiden = false;
	
	/**
	 * This listener responds to selection events in all tool items.
	 */
	private MouseListener mouseListener = new MouseAdapter() {		
		public void mouseDown(MouseEvent e) {
			PartItem toolItem = (PartItem) e.widget;
			IPresentablePart item = getPartForTab(toolItem);
			if (item != null) {
				if (item == current) {
					item.setFocus();
				}
				getSite().selectPart(item);
				selectPart(item);
				Point toDisplay = toolItem.toDisplay(new Point(e.x, e.y));
				if (e.button == 3) {
					showSystemMenu(toDisplay);
				} else {
					Image image = toolItem.getImage();
					if (image != null) {
						if (image.getBounds().contains(e.x, e.y)) {
							showPaneMenu(toDisplay);
						}
					}
				}
			}
		}
	};
	
	/**
	 * Listener attached to all child parts. It responds to changes in part properties
	 */
	private IPropertyListener childPropertyChangeListener = new IPropertyListener() {
		public void propertyChanged(Object source, int property) {			
			if (source instanceof IPresentablePart) {
				IPresentablePart part = (IPresentablePart) source;				
				updatePartItem(getPartItem(part), part);
			}
		}	
	};
	
	/**
	 * Listener attached to all tool items. It removes listeners from the associated
	 * part when the tool item is destroyed. This is required to prevent memory leaks.
	 */
	private DisposeListener tabDisposeListener = new DisposeListener() {
		public void widgetDisposed(DisposeEvent e) {
			if (e.widget instanceof ToolItem) {
				PartItem item = (PartItem)e.widget;
				
				IPresentablePart part = getPartForTab(item);
				
				part.removePropertyListener(childPropertyChangeListener);
			}
		}
	};
		
	/** 
	 * Drag listener for regions outside the toolbar
	 */
	Listener dragListener = new Listener() {
		public void handleEvent(Event event) {
			Point loc = new Point(event.x, event.y);
			Control ctrl = (Control)event.widget;
			
			getSite().dragStart(ctrl.toDisplay(loc), false);
		}
	};
	
	private Listener menuListener = new Listener() {
		public void handleEvent(Event event) {
			Point globalPos = new Point(event.x, event.y);
					showSystemMenu(globalPos);
					return;
		}
	};

	private boolean showText;

	private boolean showImage;
	
	private MenuManager systemMenuManager= new MenuManager();

	private Listener hideTitleListener = new Listener() {

		public void handleEvent(Event event) {
			if (!titleAreaHiden) {
				titleAreaHiden = true;
				layout();
			}
		}
	};
	private Listener showTitleListener = new Listener() {

		public void handleEvent(Event event) {
			if (titleAreaHiden) {
				titleAreaHiden = false;
				layout();
			}
		}
	};
	
	public SideWinderItemViewPresentation(Composite parent, IStackPresentationSite stackSite, boolean showText, boolean showImage, int style) {
		super(stackSite);
		this.showText = showText;
		this.showImage = showImage;
		this.style = style;
		
		presentationControl = new Composite(parent, SWT.NONE);
		titleArea = new Composite(presentationControl, SWT.NONE);
		
		
		contentArea = new ViewForm(presentationControl, SWT.NONE);
		clientArea = new Composite(contentArea, SWT.NONE);
		clientArea.setVisible(false);
		
		contentArea.setContent(clientArea);
		toolbarProxy = new ProxyControl(contentArea);

		PresentationUtil.addDragListener(titleArea, dragListener);
		titleArea.addListener(SWT.MenuDetect, menuListener);
		
		RowLayout rowLayout = new RowLayout ();
		rowLayout.marginLeft = 0;
		rowLayout.marginRight = 0;
		rowLayout.marginTop = 0;
		rowLayout.marginBottom = 0;
		rowLayout.type = style;
		if((style & SWT.VERTICAL) != 0) {
			rowLayout.fill = true;
		}
		rowLayout.spacing = 0;
		titleArea.setLayout (rowLayout);
		
		presentationControl.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				presentationDisposed();
			}
		});

		presentationControl.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				int borderWidth = getBorderWidth();
				Rectangle clientArea = presentationControl.getClientArea();			
				e.gc.setLineWidth(borderWidth);			
				if(activeFocus)
					e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLUE));
				else 
					e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_DARK_GRAY));
				e.gc.drawRectangle(clientArea.x, clientArea.y, clientArea.width, clientArea.height);
				Rectangle contentAreaBounds = contentArea.getBounds();
				int ypos = contentAreaBounds.y - 1;				
			}			
		});			
		createSystemMenu();
		update();
	}

	public IPresentablePart getCurrent() {
		return current;
	}
	
	protected void presentationDisposed() {
	}
	
	protected int getBorderWidth() {
		return 2;
	}
	
	public void showSystemMenu(Point displayPos) {
		Menu aMenu = systemMenuManager.createContextMenu(titleArea);
		systemMenuManager.update(true);
		aMenu.setLocation(displayPos.x, displayPos.y);
		aMenu.setVisible(true);		
	}
	
	private final void createSystemMenu() {
		getSite().addSystemActions(systemMenuManager);
		systemMenuManager.add(new Separator());
		systemMenuManager.add(new ClosePartContributionItem(this));
		systemMenuManager.add(new Separator());
		
		
		Action topAction = new Action("Top", IAction.AS_RADIO_BUTTON) {
			public void run() {
				SideWinderItemViewPresentation.this.style = SWT.TOP;
				setChecked((SideWinderItemViewPresentation.this.style & SWT.TOP) != 0);
				update();
			}			
		};
		topAction.setChecked((style & SWT.TOP) != 0);
		systemMenuManager.add(topAction);	
		
		Action bottomAction = new Action("Bottom", IAction.AS_RADIO_BUTTON) {
			public void run() {
				SideWinderItemViewPresentation.this.style = SWT.BOTTOM;
				setChecked((SideWinderItemViewPresentation.this.style & SWT.BOTTOM) != 0);
				update();
			}			
		};
		bottomAction.setChecked((style & SWT.BOTTOM) != 0);
		systemMenuManager.add(bottomAction);	
		
		Action rightAction = new Action("Right", IAction.AS_RADIO_BUTTON) {
			public void run() {
				SideWinderItemViewPresentation.this.style = SWT.RIGHT;
				setChecked((SideWinderItemViewPresentation.this.style & SWT.RIGHT) != 0);
				update();
			}			
		};
		rightAction.setChecked((style & SWT.RIGHT) != 0);
		systemMenuManager.add(rightAction);	
		
		Action leftAction = new Action("Left", IAction.AS_RADIO_BUTTON) {
			public void run() {
				SideWinderItemViewPresentation.this.style = SWT.LEFT;
				setChecked((SideWinderItemViewPresentation.this.style & SWT.LEFT) != 0);
				update();
			}			
		};		
		leftAction.setChecked((style & SWT.VERTICAL) != 0);	
		systemMenuManager.add(leftAction);	
		
		systemMenuManager.add(new Separator());
		Action textAction = new Action("Text", IAction.AS_CHECK_BOX) {
			public void run() {
				SideWinderItemViewPresentation.this.showText = isChecked();
				setChecked(SideWinderItemViewPresentation.this.showText);
				update();
			}
		};
		textAction.setChecked(showText);
		systemMenuManager.add(textAction);	
		Action imageAction = new Action("Image", IAction.AS_CHECK_BOX) {
			public void run() {
				SideWinderItemViewPresentation.this.showImage = isChecked();
				setChecked(isChecked());
				update();
			}
		};
		imageAction.setChecked(showImage);
		systemMenuManager.add(imageAction);	
	}
	
	protected void update() {
		Control[] items = titleArea.getChildren();	
		for (int idx = 0; idx < items.length; idx++) {
			if(items[idx] instanceof PartItem) {
			PartItem item = (PartItem)items[idx];	
			item.setShowImage(showImage);
			item.setShowText(showText);
			item.setFocus(activeFocus);
			}
		}
		int type = SWT.VERTICAL;
		if ((style & SWT.TOP) != 0 || (style & SWT.BOTTOM) != 0) {
			type = SWT.HORIZONTAL;
		}		
		RowLayout rowLayout = ((RowLayout)titleArea.getLayout());
		rowLayout.type = type;
		rowLayout.fill = type == SWT.VERTICAL;
		
		layout();
		presentationControl.redraw();
	}

	public void close(IPresentablePart[] parts) {
		getSite().close(parts);
	}
	
	public void layout() {
		Rectangle presentationClientArea = presentationControl.getClientArea();
		presentationClientArea.x += getBorderWidth();
		presentationClientArea.width -= getBorderWidth() * 2;
		presentationClientArea.y += getBorderWidth();
		presentationClientArea.height -= getBorderWidth() * 2;
		
		if ((style & SWT.TOP) != 0 || (style & SWT.BOTTOM) != 0) {
			Point p = titleArea.computeSize(presentationClientArea.width, SWT.DEFAULT);
			int yy = 0;
			int x = 0;
			int y = 0;
			if((style & SWT.TOP) != 0) {
				x = presentationClientArea.x;
				y = presentationClientArea.y;
				yy = p.y + 1;
			} else {
				x = presentationClientArea.x;
				y = presentationClientArea.height - p.y;
				yy = presentationClientArea.y;
			}
			if(titleAreaHiden)
				p.y = 3;
			titleArea.setBounds(x, y, presentationClientArea.width, p.y);
			contentArea.setBounds(presentationClientArea.x, yy, presentationClientArea.width, presentationClientArea.height - p.y);
		} else {
			Point p = titleArea.computeSize(SWT.DEFAULT, presentationClientArea.height);
			int xx = 0;
			int x = 0;
			int y = 0;
			if((style & SWT.RIGHT) != 0) {
				x = presentationClientArea.width - p.x;
				y = presentationClientArea.y;
				xx = presentationClientArea.x;
			} else {
				x = presentationClientArea.x;
				y = presentationClientArea.y;
				xx = p.x +1;
			}	
			if(titleAreaHiden)
				p.x = 3;
			titleArea.setBounds(x, y, p.x, presentationClientArea.height);
			contentArea.setBounds(xx, presentationClientArea.y, presentationClientArea.width - p.x, presentationClientArea.height);
		}
				
		
		titleArea.setBackground(titleArea.getDisplay().getSystemColor(SWT.COLOR_GRAY));
				
		if (current != null) {
			Control toolbar = current.getToolBar();
			if(toolbar != null) {
				toolbarProxy.setTargetControl(current.getToolBar());
				contentArea.setTopCenter(toolbarProxy.getControl());
			} else { 
				contentArea.setTopCenter(null);
			}
			contentArea.layout();
			
			Rectangle clientRectangle = clientArea.getBounds();
			Point clientAreaStart = presentationControl.getParent().toControl(
					contentArea.toDisplay(clientRectangle.x, clientRectangle.y));
			current.setBounds(new Rectangle(clientAreaStart.x, 
					clientAreaStart.y,
					clientRectangle.width, 
					clientRectangle.height));
		}		
	}
	
	public void setBounds(Rectangle bounds) {
		presentationControl.setBounds(bounds);
		layout();
	}

	public void dispose() {
	}

	public void setActive(int newState) {
		activeFocus = (newState == AS_ACTIVE_FOCUS);
		Control[] items = titleArea.getChildren();	
		for (int idx = 0; idx < items.length; idx++) {
			if(items[idx] instanceof PartItem) {
			PartItem item = (PartItem)items[idx];	
			item.setFocus(activeFocus);
			}
		}
		presentationControl.redraw();
	}

	public void setVisible(boolean isVisible) {
		presentationControl.setVisible(isVisible);
		
		if (current != null) {
			current.setVisible(isVisible);			
		}

		if (isVisible) {
			layout();
		}
	}

	public void setState(int state) {
	}

	public Control getControl() {
		return presentationControl;
	}

	public void addPart(IPresentablePart newPart, Object cookie) {
		PartItem item = new PartItem(titleArea, newPart);
		
		item.setData(PART_DATA, newPart);
		
		newPart.addPropertyListener(childPropertyChangeListener);
		
		item.addDisposeListener(tabDisposeListener);
		
		item.addMouseListener(mouseListener);
		
		PresentationUtil.addDragListener(item, new Listener() {
			public void handleEvent(Event event) {
				Point loc = new Point(event.x, event.y);
				PartItem item = (PartItem)event.widget;
				if (item != null) {
				   IPresentablePart draggedItem = getPartForTab(item);
				   draggedItem.setFocus();
				getSite().dragStart(draggedItem, item.toDisplay(loc), false);
				}
			}
		});

		updatePartItem(item, newPart);
			
		newPart.setBounds(clientArea.getBounds());
		titleArea.layout();
		titleArea.redraw();
		update();		
	}
	
	protected void updatePartItem(PartItem item, IPresentablePart part) {
		String tabName = part.getTitle();
		if(item == null) return;
		if (!tabName.equals(item.getText())) {
			item.setText(tabName);
		}
		
		if (!(part.getTitleToolTip().equals(item.getToolTipText()))) {
			item.setToolTipText(part.getTitleToolTip());
		}

		item.setImage(part.getTitleImage());
		item.setShowImage(showImage);
		item.setShowText(showText);
		titleArea.layout(true);
		titleArea.redraw();
		item.redraw();
	}
	
	protected final PartItem getPartItem(IPresentablePart part) {
		if (!titleArea.isDisposed()) {
			Control[] items = titleArea.getChildren();
			for (int idx = 0; idx < items.length; idx++) {
				Control item = items[idx];
				if (!item.isDisposed() && getPartForTab(item) == part) {
					return (PartItem) item;
				}
			}
		}
		return null;
	}
	
	protected final IPresentablePart getPartForTab(Control item) {
		return (IPresentablePart)item.getData(PART_DATA);
	}

	public void removePart(IPresentablePart oldPart) {
		PartItem item = getPartItem(oldPart);
		if(item != null) {
			item.dispose();
			titleArea.layout();
			titleArea.redraw();
		}
	}

	public void selectPart(IPresentablePart toSelect) {
		if (toSelect == current) {
			return;
		}
		
		if(current !=null) {
			current.setVisible(false);
		}
		
		current = toSelect;
		
		
		if (current != null) {
			current.setVisible(true);
			Control[] items = titleArea.getChildren();
			for (int idx = 0; idx < items.length; idx++) {
				if(items[idx] instanceof PartItem) {
				PartItem item = (PartItem)items[idx];
				item.setSelected(getPartForTab(item) == current);
				}
			}
		}
		layout();
	}

	public StackDropResult dragOver(Control currentControl, Point location) {
		return null;
	}

	public void showSystemMenu() {
	}

	public void showPaneMenu() {
		
	}
	
	public void showPaneMenu(Point location) {
		if (current == null) {
			return;
		}
		
		IPartMenu menu = current.getMenu();
		
		if (menu == null) {
			return;
		}

		menu.showMenu(location);
	}

	public Control[] getTabList(IPresentablePart part) {
		if(current != null) {
			return new Control[] {current.getControl()};
		} else {
			return new Control[0];
		}
	}
}
