package org.eclipse.ui.tests.markers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.IMarkerHelpContextProvider;

public class TestMarkerHelpContextProvider implements IMarkerHelpContextProvider {

	private static List<IMarker> paramsHasHc;
	private static List<IMarker> paramsGetHc;

	public static void init() {
		paramsHasHc = new ArrayList<>();
		paramsGetHc = new ArrayList<>();
	}

	@Override
	public String getHelpContextForMarker(IMarker marker) {
		paramsGetHc.add(marker);
		return marker.getAttribute(MarkerHelpRegistryTest.ATT_HELP_CONTEXT, null);
	}

	@Override
	public boolean hasHelpContextForMarker(IMarker marker) {
		paramsHasHc.add(marker);
		return marker.getAttribute(MarkerHelpRegistryTest.ATT_HAS_HELP, false);
	}

	public static List<IMarker> getParamsHasHc() {
		return paramsHasHc;
	}

	public static List<IMarker> getParamsGetHc() {
		return paramsGetHc;
	}
}
