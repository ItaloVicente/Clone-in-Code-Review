package org.eclipse.egit.ui.internal.clone;

import java.io.File;
import java.io.FilenameFilter;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class GitCreateGeneralProjectPage extends WizardPage {

	private final File myDirectory;

	private Text projectText;

	private Text directoryText;

	private IProject[] wsProjects;

	public GitCreateGeneralProjectPage(String path) {
		super("gitWizardExternalProjectsPage"); //$NON-NLS-1$
		myDirectory = new File(path);
		setPageComplete(false);
		setTitle(UIText.WizardProjectsImportPage_ImportProjectsTitle);
		setDescription(UIText.WizardProjectsImportPage_ImportProjectsDescription);
	}

	public void createControl(Composite parent) {

		initializeDialogUnits(parent);

		Composite workArea = new Composite(parent, SWT.NONE);
		setControl(workArea);

		workArea.setLayout(new GridLayout(2, false));
		workArea.setLayoutData(new GridData(GridData.FILL_BOTH
				| GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

		new Label(workArea, SWT.NONE).setText("Project name");
		projectText = new Text(workArea, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(projectText);
		projectText.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				checkPage();
			}
		});

		new Label(workArea, SWT.NONE).setText("Directory");
		directoryText = new Text(workArea, SWT.BORDER);
		directoryText.setEnabled(false);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(directoryText);

		Dialog.applyDialogFont(workArea);

	}

	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			projectText.setText(myDirectory.getName());
			directoryText.setText(myDirectory.getPath());
			checkPage();
		}
		super.setVisible(visible);
	}

	public String getProjectName() {
		return projectText.getText();
	}

	private void checkPage() {
		String projectName = projectText.getText();
		setErrorMessage(null);
		try {
			if (!myDirectory.exists()) {
				setErrorMessage(NLS.bind("Directory {0} does not exist",
						myDirectory.getPath()));
				return;
			}
			if (!myDirectory.isDirectory()) {
				setErrorMessage(NLS.bind("File {0} is not a directory",
						myDirectory.getPath()));
				return;
			}
			if (myDirectory.list(new FilenameFilter() {

				public boolean accept(File dir, String name) {
					if (name.equals(".project")) //$NON-NLS-1$
						return true;
					return false;
				}
			}).length > 0) {
				setErrorMessage(NLS.bind(
						"A {0} file already exists in directoy {1}",
						".project", myDirectory.getPath()));
				return;
			}
			if (projectName.length() == 0) {
				setErrorMessage("Please provide a project name");
				return;
			}
			IStatus result = ResourcesPlugin.getWorkspace().validateName(
					projectName, IResource.PROJECT);
			if (!result.isOK()) {
				setErrorMessage(result.getMessage());
				return;
			}
			if (isProjectInWorkspace(projectName)) {
				setErrorMessage(NLS.bind("Project {0} already exists",
						projectName));
				return;
			}
		} finally {
			setPageComplete(getErrorMessage() == null);
		}

	}

	private IProject[] getProjectsInWorkspace() {
		if (wsProjects == null) {
			wsProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		}
		return wsProjects;
	}

	private boolean isProjectInWorkspace(String projectName) {
		if (projectName == null) {
			return false;
		}
		IProject[] workspaceProjects = getProjectsInWorkspace();
		for (int i = 0; i < workspaceProjects.length; i++) {
			if (projectName.equals(workspaceProjects[i].getName())) {
				return true;
			}
		}
		return false;
	}

}
