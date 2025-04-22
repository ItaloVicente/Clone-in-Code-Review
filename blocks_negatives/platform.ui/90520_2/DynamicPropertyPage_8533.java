/*******************************************************************************
 * Copyright (c) 2004, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.dynamic;

import org.eclipse.jface.preference.IPreferencePageContainer;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * @since 3.1
 */
public class DynamicPreferencePage implements IWorkbenchPreferencePage {

	/**
	 *
	 */
	public DynamicPreferencePage() {
		super();
	}

	@Override
	public void init(IWorkbench workbench) {

	}

	@Override
	public Point computeSize() {
		return null;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public boolean okToLeave() {
		return false;
	}

	@Override
	public boolean performCancel() {
		return false;
	}

	@Override
	public boolean performOk() {
		return false;
	}

	@Override
	public void setContainer(IPreferencePageContainer preferencePageContainer) {

	}

	@Override
	public void setSize(Point size) {

	}

	@Override
	public void createControl(Composite parent) {

	}

	@Override
	public void dispose() {

	}

	@Override
	public Control getControl() {
		return null;
	}

	@Override
	public String getDescription() {
		return null;
	}

	@Override
	public String getErrorMessage() {
		return null;
	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public String getMessage() {
		return null;
	}

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public void performHelp() {

	}

	@Override
	public void setDescription(String description) {

	}

	@Override
	public void setImageDescriptor(ImageDescriptor image) {

	}

	@Override
	public void setTitle(String title) {

	}

	@Override
	public void setVisible(boolean visible) {

	}

}
