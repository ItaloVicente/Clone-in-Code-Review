package org.eclipse.ui.tests.dynamicplugins;

import java.util.Iterator;

import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.views.markers.internal.MarkerGroup;
import org.eclipse.ui.views.markers.internal.MarkerSupportRegistry;
import org.eclipse.ui.views.markers.internal.ProblemFilter;

public class MarkerSupportTests extends DynamicTestCase {

	public static final String FILTER1 = "filter1";

	public static final String FILTER2 = "filter2";

	public static final String FILTER3 = "filter3";

	public static final String DYNAMIC_CATEGORY = "dynamicCategory";

	static final String DYNAMIC_PROBLEM_MARKER = "org.eclipse.ui.tests.dynamicTestMarker";

	static final String PROBLEM_MARKER = "org.eclipse.core.resources.problemmarker";

	public MarkerSupportTests(String testName) {
		super(testName);
	}

	public void testFilters() {
		assertFalse(hasFilter(FILTER1));
		assertFalse(hasFilter(FILTER2));
		assertFalse(hasFilter(FILTER3));
		getBundle();
		assertTrue(hasFilter(FILTER1));
		assertTrue(hasFilter(FILTER2));
		assertTrue(hasFilter(FILTER3));
		removeBundle();
		assertFalse(hasFilter(FILTER1));
		assertFalse(hasFilter(FILTER2));
		assertFalse(hasFilter(FILTER3));
	}

	public void testMarkerGroup() {
		assertFalse(hasMarkerGroup());
		getBundle();
		assertTrue(hasMarkerGroup());
		removeBundle();
		assertFalse(hasMarkerGroup());
	}

	public void testCategories() {
		assertFalse(hasCategory());
		getBundle();
		assertTrue(hasCategory());
		removeBundle();
		assertFalse(hasCategory());
	}

	public void testHierarchies() {
		assertFalse(hasHierarchy());
		getBundle();
		assertTrue(hasHierarchy());
		removeBundle();
		assertFalse(hasHierarchy());
	}

	@Override
	protected String getMarkerClass() {
		return "org.eclipse.ui.dynamic.markerSupport.DynamicTestsSubCategoryProvider";
	}

	private boolean hasHierarchy() {
		return MarkerSupportRegistry.getInstance().getSorterFor(
				DYNAMIC_PROBLEM_MARKER) != MarkerSupportRegistry.getInstance()
				.getSorterFor(PROBLEM_MARKER);
	}

	private boolean hasMarkerGroup() {
		Iterator groups = MarkerSupportRegistry.getInstance()
		.getMarkerGroups().iterator();
		
		while (groups.hasNext()) {
			MarkerGroup element = (MarkerGroup) groups.next();
			if(element.getField().getDescription().equals("Dynamic Test Grouping"))
				return true;
		}
		
		return false;
	}

	private boolean hasCategory() {
		return MarkerSupportRegistry.getInstance().getCategory(
				DYNAMIC_PROBLEM_MARKER) != null;
	}

	private boolean hasFilter(String id) {
		Iterator filters = MarkerSupportRegistry.getInstance()
				.getRegisteredFilters().iterator();
		while (filters.hasNext()) {
			ProblemFilter filter = (ProblemFilter) filters.next();
			if (id.equals(filter.getId()))
				return true;
		}
		return false;
	}

	@Override
	protected String getExtensionId() {
		return "newProblemFilter.testDynamicFilterAddition";
	}

	@Override
	protected String getExtensionPoint() {
		return MarkerSupportRegistry.MARKER_SUPPORT;
	}

	@Override
	protected String getInstallLocation() {
		return "data/org.eclipse.newMarkerSupport";
	}

	@Override
	protected String getDeclaringNamespace() {
		return IDEWorkbenchPlugin.IDE_WORKBENCH;
	}

}
