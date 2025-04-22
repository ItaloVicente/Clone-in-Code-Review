package org.eclipse.ui.dynamic.markerSupport;


import org.eclipse.core.resources.IMarker;
import org.eclipse.ui.views.markers.ISubCategoryProvider;

public class DynamicTestsSubCategoryProvider implements ISubCategoryProvider {

	public String categoryFor(IMarker marker) {
		return "Dynamic Test";
	}

}
