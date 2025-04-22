
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

public class ReadmeCreationPage extends WizardNewFileCreationPage {
    private IWorkbench workbench;

    private Button sectionCheckbox;

    private Button subsectionCheckbox;

    private Button openFileCheckbox;

    private static int nameCounter = 1;

    public ReadmeCreationPage(IWorkbench workbench,
            IStructuredSelection selection) {
        super("sampleCreateReadmePage1", selection); //$NON-NLS-1$
        this.setTitle(MessageUtil.getString("Create_Readme_File")); //$NON-NLS-1$
        this.setDescription(MessageUtil
                .getString("Create_a_new_Readme_file_resource")); //$NON-NLS-1$
        this.workbench = workbench;
    }

    @Override
	public void createControl(Composite parent) {
        super.createControl(parent);
        Composite composite = (Composite) getControl();

        PlatformUI.getWorkbench().getHelpSystem().setHelp(composite,
				IReadmeConstants.CREATION_WIZARD_PAGE_CONTEXT);

        this.setFileName("sample" + nameCounter + ".readme"); //$NON-NLS-1$ //$NON-NLS-2$

        Group group = new Group(composite, SWT.NONE);
        group.setLayout(new GridLayout());
        group.setText(MessageUtil
                .getString("Automatic_sample_section_generation")); //$NON-NLS-1$
        group.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
                | GridData.HORIZONTAL_ALIGN_FILL));

        sectionCheckbox = new Button(group, SWT.CHECK);
        sectionCheckbox.setText(MessageUtil
                .getString("Generate_sample_section_titles")); //$NON-NLS-1$
        sectionCheckbox.setSelection(true);
        sectionCheckbox.addListener(SWT.Selection, this);

        subsectionCheckbox = new Button(group, SWT.CHECK);
        subsectionCheckbox.setText(MessageUtil
                .getString("Generate_sample_subsection_titles")); //$NON-NLS-1$
        subsectionCheckbox.setSelection(true);
        subsectionCheckbox.addListener(SWT.Selection, this);

        openFileCheckbox = new Button(composite, SWT.CHECK);
        openFileCheckbox.setText(MessageUtil
                .getString("Open_file_for_editing_when_done")); //$NON-NLS-1$
        openFileCheckbox.setSelection(true);

        setPageComplete(validatePage());

    }

    public boolean finish() {
        IFile newFile = createNewFile();
        if (newFile == null)
            return false; // ie.- creation was unsuccessful

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

    @Override
	protected InputStream getInitialContents() {
        if (!sectionCheckbox.getSelection())
            return null;

        StringBuffer sb = new StringBuffer();
        sb.append(MessageUtil.getString("SAMPLE_README_FILE")); //$NON-NLS-1$
        sb.append(MessageUtil.getString("SECTION_1")); //$NON-NLS-1$
        sb.append(MessageUtil.getString("SECTION_1_BODY_1")); //$NON-NLS-1$

        if (subsectionCheckbox.getSelection()) {
            sb.append(MessageUtil.getString("Subsection_1_1")); //$NON-NLS-1$
            sb.append(MessageUtil.getString("Subsection_1_1_Body_1")); //$NON-NLS-1$
        }

        sb.append(MessageUtil.getString("SECTION_2")); //$NON-NLS-1$
        sb.append(MessageUtil.getString("SECTION_2_BODY_1")); //$NON-NLS-1$
        sb.append(MessageUtil.getString("SECTION_2_BODY_2")); //$NON-NLS-1$

        if (subsectionCheckbox.getSelection()) {
            sb.append(MessageUtil.getString("Subsection_2_1")); //$NON-NLS-1$
            sb.append(MessageUtil.getString("Subsection_2_1_BODY_1")); //$NON-NLS-1$
            sb.append(MessageUtil.getString("Subsection_2_2")); //$NON-NLS-1$
            sb.append(MessageUtil.getString("Subsection_2_2_BODY_1")); //$NON-NLS-1$
        }

        return new ByteArrayInputStream(sb.toString().getBytes());
    }

    @Override
	protected String getNewFileLabel() {
        return MessageUtil.getString("Readme_file_name"); //$NON-NLS-1$
    }

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
