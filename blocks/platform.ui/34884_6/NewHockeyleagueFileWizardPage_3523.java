package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.wizards;

import java.text.MessageFormat;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

public class NewHockeyleagueFileWizardPage
	extends WizardNewFileCreationPage {

	protected IFile hockeyleagueFile;

	public NewHockeyleagueFileWizardPage(String pageName,
			IStructuredSelection selection) {
		super(pageName, selection);
	}

	protected boolean validatePage() {
		if (super.validatePage()) {
			String requiredExtStatic = "hockeyleague"; //$NON-NLS-1$
			String enteredExt = new Path(getFileName()).getFileExtension();
			if (enteredExt == null
				|| !(enteredExt.equals(requiredExtStatic))) {
				setErrorMessage(MessageFormat.format(
					"The filename must end in \".{0}\"",//$NON-NLS-1$
					new Object[] {requiredExtStatic}));
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public boolean performFinish() {
		hockeyleagueFile = getHockeyleagueFile();
		return true;
	}

	public IFile getHockeyleagueFile() {
		return hockeyleagueFile == null ? ResourcesPlugin.getWorkspace()
			.getRoot().getFile(getContainerFullPath().append(getFileName()))
			: hockeyleagueFile;
	}
}
