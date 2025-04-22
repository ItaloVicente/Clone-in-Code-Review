package org.eclipse.ui.dynamic;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.dialogs.IWorkingSetPage;

public class DynamicWorkingSetPage implements IWorkingSetPage {

	public void finish() {

	}

	public IWorkingSet getSelection() {
		return null;
	}

	public void setSelection(IWorkingSet workingSet) {

	}

	public boolean canFlipToNextPage() {
		return false;
	}

	public String getName() {
		return null;
	}

	public IWizardPage getNextPage() {
		return null;
	}

	public IWizardPage getPreviousPage() {
		return null;
	}

	public IWizard getWizard() {
		return null;
	}

	public boolean isPageComplete() {
		return false;
	}

	public void setPreviousPage(IWizardPage page) {

	}

	public void setWizard(IWizard newWizard) {

	}

	public void createControl(Composite parent) {

	}

	public void dispose() {

	}

	public Control getControl() {
		return null;
	}

	public String getDescription() {
		return null;
	}

	public String getErrorMessage() {
		return null;
	}

	public Image getImage() {
		return null;
	}

	public String getMessage() {
		return null;
	}

	public String getTitle() {
		return null;
	}

	public void performHelp() {

	}

	public void setDescription(String description) {

	}

	public void setImageDescriptor(ImageDescriptor image) {

	}

	public void setTitle(String title) {

	}

	public void setVisible(boolean visible) {

	}

}
