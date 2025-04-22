/*******************************************************************************
 * Copyright (c) 2008, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.demo.e4photo;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer;
import org.eclipse.e4.ui.model.application.ui.basic.impl.BasicFactoryImpl;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class ImageDialogHandler {

	private class ImageDialog extends Dialog {
		private IPresentationEngine theRenderer;

		public ImageDialog(Shell shell, MApplication app, IPresentationEngine renderer) {
			super(shell);
			theRenderer = renderer;
		}
		
		protected Control createDialogArea(Composite parent) {
			Composite comp = new Composite(parent, SWT.NULL);
			comp.setLayout(new FillLayout());
			comp.setLayoutData(new GridData(GridData.FILL_BOTH));
			
			MUIElement dlgModel = createDlgModel();
			theRenderer.createGui(dlgModel, comp, null);

			
			
			return comp;
		}
		
		private MUIElement createDlgModel() {
			MPartSashContainer sash = BasicFactoryImpl.eINSTANCE.createPartSashContainer();
			sash.setHorizontal(true);
			
			MPart library = BasicFactoryImpl.eINSTANCE.createPart();
			library.setLabel("Library");
			
			MPart preview = BasicFactoryImpl.eINSTANCE.createPart();
			preview.setLabel("Preview");
			
			sash.getChildren().add(library);
			sash.getChildren().add(preview);
			
			return sash;
		}

		protected void configureShell(Shell shell) {
			super.configureShell(shell);
			shell.setText("Image Dialog");
			shell.setSize(600, 400);
		}
		public void create() {
			super.create();
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
		protected boolean isResizable() {
			return true;
		}
	}
	
	@Execute
	public void execute(Shell shell, MApplication app, IPresentationEngine renderer) {
		ImageDialog dlg = new ImageDialog(shell, app, renderer);
		dlg.open();
	}

}
