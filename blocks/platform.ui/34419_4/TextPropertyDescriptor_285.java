
package org.eclipse.ui.views.properties;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ShowInContext;

public class PropertyShowInContext extends ShowInContext {

	private IWorkbenchPart part;

	public PropertyShowInContext(IWorkbenchPart aPart, ISelection selection) {
		super(null, selection);
		part = aPart;
	}

	public PropertyShowInContext(IWorkbenchPart aPart,
			ShowInContext aShowInContext) {
		super(aShowInContext.getInput(), aShowInContext.getSelection());
		part = aPart;
	}

	public IWorkbenchPart getPart() {
		return part;
	}

	public void setPart(IWorkbenchPart part) {
		this.part = part;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((part == null) ? 0 : part.hashCode())
				+ ((getSelection() == null) ? 0 : getSelection().hashCode())
				+ ((getInput() == null) ? 0 : getInput().hashCode());
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
		PropertyShowInContext other = (PropertyShowInContext) obj;
		if (part == null) {
			if (other.part != null)
				return false;
		} else if (!part.equals(other.part))
			return false;
		if (getSelection() == null) {
			if (other.getSelection() != null)
				return false;
		} else if (!getSelection().equals(other.getSelection()))
			return false;
		if (getInput() == null || other.getInput() == null) {
				return true;
		} else if (!getInput().equals(other.getInput()))
			return false;
		return true;
	}
}
