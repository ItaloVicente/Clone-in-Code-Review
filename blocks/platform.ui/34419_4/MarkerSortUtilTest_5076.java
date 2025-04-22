
package org.eclipse.ui.tests.markers;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.ui.views.markers.FiltersContributionParameters;
import org.eclipse.ui.views.markers.MarkerSupportConstants;

public class FIXMEParameters extends FiltersContributionParameters {

	private static Map fixmeMap;
	static {
		fixmeMap = new HashMap();
		fixmeMap.put(MarkerSupportConstants.CONTAINS_KEY, "FIXME"); //$NON-NLS-1$
	}

	public FIXMEParameters() {

	}

	@Override
	public Map getParameterValues() {
		return fixmeMap;
	}

}
