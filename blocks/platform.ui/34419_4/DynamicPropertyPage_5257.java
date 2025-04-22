package org.eclipse.ui.dynamic;

import org.eclipse.jface.preference.IPreferencePageContainer;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class DynamicPreferencePage implements IWorkbenchPreferencePage {

	public DynamicPreferencePage() {
		super();
	}

	public void init(IWorkbench workbench) {

	}

	public Point computeSize() {
		return null;
	}

	public boolean isValid() {
		return false;
	}

	public boolean okToLeave() {
		return false;
	}

	public boolean performCancel() {
		return false;
	}

	public boolean performOk() {
		return false;
	}

	public void setContainer(IPreferencePageContainer preferencePageContainer) {
		
	}

	public void setSize(Point size) {
		
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
