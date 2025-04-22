
package org.eclipse.ui.navigator;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.viewers.AbstractTreeViewer;

public final class PipelinedViewerUpdate {

	private static final String[] NO_PROPERTIES = new String[0];

	private final Set refreshTargets = new LinkedHashSet();

	private boolean updateLabels = false;

	private Map properties;

	public final String[] getProperties(Object aTarget) {
		if (properties != null && properties.containsKey(aTarget)) {
			String[] props = (String[]) properties.get(aTarget);
			return props != null ? props : NO_PROPERTIES;
		}
		return NO_PROPERTIES;
	}

	public final void setProperties(Object aTarget, String[] theProperties) {
		if (theProperties != null && theProperties.length > 0) {
			if (properties == null) {
				properties = new HashMap();
			}
			properties.put(aTarget, theProperties);

		} else {
			properties.remove(aTarget);
		}

		if (properties.size() == 0) {
			properties = null;
		}

	}

	public final Set getRefreshTargets() {
		return refreshTargets;
	}

	public final boolean isUpdateLabels() {
		return updateLabels;
	}

	public final void setUpdateLabels(boolean toUpdateLabels) {
		updateLabels = toUpdateLabels;
	}

}
