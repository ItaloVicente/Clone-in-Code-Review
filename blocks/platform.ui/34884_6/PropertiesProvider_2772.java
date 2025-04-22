
package org.eclipse.ui.internal.quickaccess;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.dialogs.PropertyDialog;

public class PropertiesElement extends QuickAccessElement {

	private Object selectedElement;
	private IPreferenceNode preferenceNode;
	
		super(propertiesProvider);
		this.selectedElement = selectedElement;
		this.preferenceNode = preferenceNode;
	}
	
	@Override
	public void execute() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		if (window != null) {
			PropertyDialog dialog = PropertyDialog.createDialogOn(window
					.getShell(), preferenceNode.getId(), selectedElement);
			dialog.open();
		}
	}

	@Override
	public String getId() {
		return preferenceNode.getId();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		Image image = preferenceNode.getLabelImage();
		if (image != null) {
			ImageDescriptor descriptor = ImageDescriptor.createFromImage(image);
			return descriptor;
		}
		return null;
	}

	@Override
	public String getLabel() {
		return preferenceNode.getLabelText();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((preferenceNode == null) ? 0 : preferenceNode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PropertiesElement other = (PropertiesElement) obj;
		if (preferenceNode == null) {
			if (other.preferenceNode != null)
				return false;
		} else if (!preferenceNode.equals(other.preferenceNode))
			return false;
		return true;
	}	
}
