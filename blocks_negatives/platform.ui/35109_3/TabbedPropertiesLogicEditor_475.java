/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.views.properties.tabbed.logic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;

import org.eclipse.gef.examples.logicdesigner.model.LogicDiagram;
import org.eclipse.gef.examples.logicdesigner.model.LogicDiagramFactory;

public class LogicWizardPage1
    extends WizardNewFileCreationPage
    implements SelectionListener {

    private IWorkbench workbench;

    private static int exampleCount = 1;

    private Button model1 = null;

    private Button model2 = null;

    private int modelSelected = 1;

    public LogicWizardPage1(IWorkbench aWorkbench,
            IStructuredSelection selection) {
        super("sampleLogicPage1", selection); //$NON-NLS-1$
        this
        this.setImageDescriptor(ImageDescriptor.createFromFile(getClass(),
        this.workbench = aWorkbench;
    }

    public void createControl(Composite parent) {
        super.createControl(parent);
        this

        Composite composite = (Composite) getControl();

        Group group = new Group(composite, SWT.NONE);
        group.setLayout(new GridLayout());
        group.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
            | GridData.HORIZONTAL_ALIGN_FILL));

        model1 = new Button(group, SWT.RADIO);
        model1.addSelectionListener(this);
        model1.setSelection(true);

        model2 = new Button(group, SWT.RADIO);
        model2.addSelectionListener(this);

        new Label(composite, SWT.NONE);

        setPageComplete(validatePage());
    }

    protected InputStream getInitialContents() {
        LogicDiagram ld = new LogicDiagram();
        if (modelSelected == 2)
            ld = (LogicDiagram) LogicDiagramFactory.createLargeModel();
        ByteArrayInputStream bais = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(ld);
            oos.flush();
            oos.close();
            baos.close();
            bais = new ByteArrayInputStream(baos.toByteArray());
            bais.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bais;
    }

    public boolean finish() {
        IFile newFile = createNewFile();
        if (newFile == null)

        try {
            IWorkbenchWindow dwindow = workbench.getActiveWorkbenchWindow();
            IWorkbenchPage page = dwindow.getActivePage();
            if (page != null)
                IDE.openEditor(page, newFile, true);
        } catch (org.eclipse.ui.PartInitException e) {
            e.printStackTrace();
            return false;
        }
        exampleCount++;
        return true;
    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(SelectionEvent)
     */
    public void widgetSelected(SelectionEvent e) {
        if (e.getSource() == model1) {
            modelSelected = 1;
        } else {
            modelSelected = 2;
        }
    }

    /**
     * Empty method
     */
    public void widgetDefaultSelected(SelectionEvent e) {
    }

}
