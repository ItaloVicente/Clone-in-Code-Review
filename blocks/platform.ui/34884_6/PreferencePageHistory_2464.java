package org.eclipse.ui.internal.dialogs;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class PreferenceNodeFilter extends ViewerFilter {

	Collection ids = new HashSet();

	public PreferenceNodeFilter(String[] filteredIds) {
		super();
		for (int i = 0; i < filteredIds.length; i++) {
			ids.add(filteredIds[i]);
		}
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		return checkNodeAndChildren((IPreferenceNode) element);
	}

	private boolean checkNodeAndChildren(IPreferenceNode node) {
		if(ids.contains(node.getId())) {
			return true;
		}
		
		IPreferenceNode[] subNodes = node.getSubNodes();
		for (int i = 0; i < subNodes.length; i++) {
			if(checkNodeAndChildren(subNodes[i])) {
				return true;
			}
			
		}
		return false;
	}

}
