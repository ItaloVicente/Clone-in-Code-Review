package org.eclipse.ui.dynamic;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class DynamicWizard implements INewWizard {

	public DynamicWizard() {
		super();
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	public void addPages() {
	}

	public boolean canFinish() {
		return false;
	}

	public void createPageControls(Composite pageContainer) {
	}

	public void dispose() {
	}

	public IWizardContainer getContainer() {
		return null;
	}

	public Image getDefaultPageImage() {
		return null;
	}

	public IDialogSettings getDialogSettings() {
		return null;
	}

	public IWizardPage getNextPage(IWizardPage page) {
		return null;
	}

	public IWizardPage getPage(String pageName) {
		return null;
	}

	public int getPageCount() {
		return 0;
	}

	public IWizardPage[] getPages() {
		return null;
	}

	public IWizardPage getPreviousPage(IWizardPage page) {
		return null;
	}

	public IWizardPage getStartingPage() {
		return null;
	}

	public RGB getTitleBarColor() {
		return null;
	}

	public String getWindowTitle() {
		return null;
	}

	public boolean isHelpAvailable() {
		return false;
	}

	public boolean needsPreviousAndNextButtons() {
		return false;
	}

	public boolean needsProgressMonitor() {
		return false;
	}

	public boolean performCancel() {
		return false;
	}

	public boolean performFinish() {
		return false;
	}

	public void setContainer(IWizardContainer wizardContainer) {
	}

}
