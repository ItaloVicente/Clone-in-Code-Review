/*******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.presentation.barebones;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.presentations.IPresentablePart;
import org.eclipse.ui.presentations.IStackPresentationSite;
import org.eclipse.ui.presentations.StackDropResult;
import org.eclipse.ui.presentations.StackPresentation;

/**
 * Bare-bones stack presentation. The currently selected part fills the entire
 * presentation area, and all other parts are invisible. Does not provide a 
 * system menu, pane menu, trim, drag/drop, toolbars, or any way to switch 
 * parts.
 */
public class BareBonesPartPresentation extends StackPresentation {

/**
 * Main widget for the presentation
 */
private Composite presentationControl;

/**
 * Currently selected part
 */
private IPresentablePart current;

/**
 * Creates a new bare-bones part presentation, given the parent composite and 
 * an IStackPresentationSite interface that will be used to communicate with 
 * the workbench.
 * 
 * @param stackSite interface to the workbench
 */
public BareBonesPartPresentation(Composite parent, 
		IStackPresentationSite stackSite) {
	super(stackSite);		
	
	presentationControl = new Composite(parent, SWT.NONE);
	
	presentationControl.addDisposeListener(new DisposeListener() {
		public void widgetDisposed(DisposeEvent e) {
			presentationDisposed();
		}
	});
}

public void dispose() {
	presentationControl.dispose();
}

/**
 * Perform any cleanup. This method should remove any listeners that were
 * attached to other objects. This gets called when the presentation
 * widget is disposed. This is safer than cleaning up in the dispose() 
 * method, since this code will run even if some unusual circumstance 
 * destroys the Shell without first calling dispose().
 */
protected void presentationDisposed() {
}

public void setBounds(Rectangle bounds) {
	presentationControl.setBounds(bounds);
	
	updatePartBounds();
}

private void updatePartBounds() {
	if (current != null) {
		current.setBounds(presentationControl.getBounds());
	}
}

public Point computeMinimumSize() {
	return new Point(16,16);
}

public void setVisible(boolean isVisible) {
	
	presentationControl.setVisible(isVisible);
	
	if (current != null) {
		current.setVisible(isVisible);
	}

	if (isVisible) {
		updatePartBounds();
	}
}

public void selectPart(IPresentablePart toSelect) {
	if (toSelect == current) {
		return;
	}
	
	if (current != null) {
		current.setVisible(false);
	}
	
	current = toSelect;
	
	
	if (current != null) {
		current.setVisible(true);
	}
	
	updatePartBounds();
}

public Control[] getTabList(IPresentablePart part) {
	return new Control[] {part.getControl()};
}

public Control getControl() {
	return presentationControl;
}

public void setActive(int newState) {}
public void setState(int state) {}
public void addPart(IPresentablePart newPart, Object cookie) {}
public void removePart(IPresentablePart oldPart) {}
public StackDropResult dragOver(Control currentControl, Point location) {return null;}
public void showSystemMenu() {}
public void showPaneMenu() {}
}
