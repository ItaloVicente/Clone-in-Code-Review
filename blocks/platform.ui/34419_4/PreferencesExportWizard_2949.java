
package org.eclipse.ui.internal.wizards.preferences;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.internal.preferences.PreferenceTransferElement;

public class PreferencesContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getChildren(Object parentElement) {
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof PreferenceTransferElement[])
			return (PreferenceTransferElement[]) inputElement;
		return new PreferenceTransferElement[0];
	}

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}
	

}
