/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.examples.readmetool;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;

/**
 * This class is the only page of the Readme file resource creation wizard.
 * It subclasses the standard file resource creation page class,
 * and consequently inherits the file resource creation functionality.
 *
 * This page provides users with the choice of creating sample headings for
 * sections and subsections.  Additionally, the option is presented to open
 * the file immediately for editing after creation.
 */
public class ReadmeCreationPage extends WizardNewFileCreationPage {
    private IWorkbench workbench;

    private Button sectionCheckbox;

    private Button subsectionCheckbox;

    private Button openFileCheckbox;

    private static int nameCounter = 1;

    /**
     * Creates the page for the readme creation wizard.
     *
     * @param workbench  the workbench on which the page should be created
     * @param selection  the current selection
     */
    public ReadmeCreationPage(IWorkbench workbench,
            IStructuredSelection selection) {
        super("sampleCreateReadmePage1", selection); //$NON-NLS-1$
        this.setDescription(MessageUtil
        this.workbench = workbench;
    }

    @Override
	public void createControl(Composite parent) {
        super.createControl(parent);
        Composite composite = (Composite) getControl();

        PlatformUI.getWorkbench().getHelpSystem().setHelp(composite,
				IReadmeConstants.CREATION_WIZARD_PAGE_CONTEXT);


        Group group = new Group(composite, SWT.NONE);
        group.setLayout(new GridLayout());
        group.setText(MessageUtil
        group.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
                | GridData.HORIZONTAL_ALIGN_FILL));

        sectionCheckbox = new Button(group, SWT.CHECK);
        sectionCheckbox.setText(MessageUtil
        sectionCheckbox.setSelection(true);
        sectionCheckbox.addListener(SWT.Selection, this);

        subsectionCheckbox = new Button(group, SWT.CHECK);
        subsectionCheckbox.setText(MessageUtil
        subsectionCheckbox.setSelection(true);
        subsectionCheckbox.addListener(SWT.Selection, this);

        openFileCheckbox = new Button(composite, SWT.CHECK);
        openFileCheckbox.setText(MessageUtil
        openFileCheckbox.setSelection(true);

        setPageComplete(validatePage());

    }

    /**
     * Creates a new file resource as requested by the user. If everything
     * is OK then answer true. If not, false will cause the dialog
     * to stay open.
     *
     * @return whether creation was successful
     * @see ReadmeCreationWizard#performFinish()
     */
    public boolean finish() {
        IFile newFile = createNewFile();
        if (newFile == null)

        try {
            if (openFileCheckbox.getSelection()) {
                IWorkbenchWindow dwindow = workbench.getActiveWorkbenchWindow();
                IWorkbenchPage page = dwindow.getActivePage();
                if (page != null) {
                    IDE.openEditor(page, newFile, true);
                }
            }
        } catch (PartInitException e) {
            e.printStackTrace();
            return false;
        }
        nameCounter++;
        return true;
    }

    /**
     * The <code>ReadmeCreationPage</code> implementation of this
     * <code>WizardNewFileCreationPage</code> method
     * generates sample headings for sections and subsections in the
     * newly-created Readme file according to the selections of self's
     * checkbox widgets
     */
    @Override
	protected InputStream getInitialContents() {
        if (!sectionCheckbox.getSelection())
            return null;

        StringBuffer sb = new StringBuffer();

        if (subsectionCheckbox.getSelection()) {
        }


        if (subsectionCheckbox.getSelection()) {
        }

        return new ByteArrayInputStream(sb.toString().getBytes());
    }

    /** (non-Javadoc)
     * Method declared on WizardNewFileCreationPage.
     */
    @Override
	protected String getNewFileLabel() {
    }

    /** (non-Javadoc)
     * Method declared on WizardNewFileCreationPage.
     */
    @Override
	public void handleEvent(Event e) {
        Widget source = e.widget;

        if (source == sectionCheckbox) {
            if (!sectionCheckbox.getSelection())
                subsectionCheckbox.setSelection(false);
            subsectionCheckbox.setEnabled(sectionCheckbox.getSelection());
        }

        super.handleEvent(e);
    }
}
