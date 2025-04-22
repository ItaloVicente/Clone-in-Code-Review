/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.presentation.baretittle;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.presentations.*;

public class BareTitleStackPresentation extends StackPresentation {

	private static final String PART_DATA = "part";
	
	private static final int TITLE_HEIGHT = 22;
	
	private boolean activeFocus = false;
	
	/**
	 * Main widget for the presentation
	 */
	private Composite presentationControl;
	private Composite titleArea;
	private Composite contentArea;
	
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
	
	private MouseListener mouseListener = new MouseAdapter() {		
		public void mouseDown(MouseEvent e) {
			if (current != null) {
				current.setFocus();
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
	
	public BareTitleStackPresentation(Composite parent, IStackPresentationSite stackSite) {
		super(stackSite);
		
		presentationControl = new Composite(parent, SWT.NONE);
		titleArea = new Composite(presentationControl, SWT.NONE);
		contentArea = new Composite(presentationControl, SWT.NONE);
		titleArea.addMouseListener(mouseListener);
		PresentationUtil.addDragListener(titleArea, dragListener);
		
		presentationControl.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				presentationDisposed();
			}
		});

		presentationControl.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Rectangle clientArea = presentationControl.getClientArea();			
				e.gc.setLineWidth(1);				
				e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_DARK_GRAY));
				e.gc.drawRectangle(clientArea.x, clientArea.y, clientArea.width-1, clientArea.height-1);
				Rectangle contentAreaBounds = contentArea.getBounds();
				int ypos = contentAreaBounds.y - 1;				
				e.gc.drawLine(clientArea.x, ypos, clientArea.x + clientArea.width - 1, ypos);
			}			
		});
		
		titleArea.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Rectangle titleRect = titleArea.getClientArea();
				GC gc = e.gc;
				e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_GRAY));
				if(activeFocus)
					e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLUE));
				else 
					e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_WHITE));
				e.gc.fillGradientRectangle(titleRect.x, titleRect.y, titleRect.x + titleRect.width, titleRect.y + titleRect.height, true);
				
				if(current != null) {
				int textWidth = titleRect.width - 1;
				if (textWidth > 0) {
					Font gcFont = gc.getFont();
					gc.setFont(presentationControl.getFont());
					String text = current.getTitle();
					
					Point extent = gc.textExtent(text, SWT.DRAW_TRANSPARENT | SWT.DRAW_MNEMONIC);	
					int textY = titleRect.y + (titleRect.height - extent.y) / 2;
					
					if(activeFocus)					
						gc.setForeground(e.display.getSystemColor(SWT.COLOR_WHITE));
					else 
						gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLACK));
					gc.setFont(JFaceResources.getBannerFont());
					gc.drawText(text, titleRect.x + 3, textY, SWT.DRAW_TRANSPARENT | SWT.DRAW_MNEMONIC);					
				}				
			}
			}
		});
		layout();
	}

	protected void presentationDisposed() {
	}
	
	protected int getBorderWidth() {
		return 1;
	}
	
	public void layout() {
		Rectangle presentationClientArea = presentationControl.getClientArea();
		presentationClientArea.x += getBorderWidth();
		presentationClientArea.width -= getBorderWidth() * 2;
		presentationClientArea.y += getBorderWidth();
		presentationClientArea.height -= getBorderWidth() * 2;
		titleArea.setBounds(presentationClientArea.x, presentationClientArea.y, presentationClientArea.width, presentationClientArea.y + TITLE_HEIGHT);
		int yy = TITLE_HEIGHT  + 2;
		contentArea.setBounds(presentationClientArea.x, presentationClientArea.y + yy, presentationClientArea.width, presentationClientArea.height - yy);
		
		titleArea.setBackground(titleArea.getDisplay().getSystemColor(SWT.COLOR_DARK_GREEN));
		contentArea.setBackground(titleArea.getDisplay().getSystemColor(SWT.COLOR_DARK_RED));
		
		if (current != null) {
			Rectangle clientRectangle = contentArea.getBounds();
			Point clientAreaStart = presentationControl.getParent().toControl(
					presentationControl.toDisplay(clientRectangle.x, clientRectangle.y));
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
		titleArea.redraw();
	}

	public void setVisible(boolean isVisible) {
	}

	public void setState(int state) {
	}

	public Control getControl() {
		return presentationControl;
	}

	public void addPart(IPresentablePart newPart, Object cookie) {
		newPart.setBounds(contentArea.getBounds());
		this.current = newPart;
		layout();
	}

	public void removePart(IPresentablePart oldPart) {
	}

	public void selectPart(IPresentablePart toSelect) {
	}

	public StackDropResult dragOver(Control currentControl, Point location) {
		return null;
	}

	public void showSystemMenu() {
	}

	public void showPaneMenu() {
	}

	public Control[] getTabList(IPresentablePart part) {
		if(current != null) {
			return new Control[] {current.getControl()};
		} else {
			return new Control[0];
		}
	}
}
