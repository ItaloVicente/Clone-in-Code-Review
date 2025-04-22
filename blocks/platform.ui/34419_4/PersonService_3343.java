
package org.eclipse.ui.examples.contributions.model;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public class PersonInput implements IEditorInput {
	private int index;

	public PersonInput(int i) {
		index = i;
	}

	public int getIndex() {
		return index;
	}

	public boolean exists() {
		return true;
	}

	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	public String getName() {
		return "" + index; //$NON-NLS-1$
	}

	public IPersistableElement getPersistable() {
		return null;
	}

	public String getToolTipText() {
		return getName();
	}

	public Object getAdapter(Class adapter) {
		return null;
	}
	public int hashCode() {
		return index;
	}
	
	public boolean equals(Object o) {
		if (o instanceof PersonInput) {
			return index == ((PersonInput)o).index; 
		}
		return false;
	}
}
