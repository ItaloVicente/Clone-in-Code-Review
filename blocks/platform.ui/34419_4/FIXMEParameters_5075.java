package org.eclipse.ui.tests.markers;

import java.util.Iterator;

import org.eclipse.ui.tests.harness.util.UITestCase;
import org.eclipse.ui.views.markers.internal.MarkerSupportRegistry;
import org.eclipse.ui.views.markers.internal.ProblemFilter;

public abstract class DeclarativeFilterTest extends UITestCase {

	public static final String PROBLEM_TEST_ON_PROBLEM = "problemTest.onProblem";

	public static final String PROBLEM_TEST_NOT_ON_METHOD = "problemTest.notOnMethod";

	public static final String PROBLEM_TEST_ON_METHOD = "problemTest.onMethod";

	public static final String PROBLEM_TEST_SAME_CONTAINER_NO_SEVERITY = "problemTest.sameContainerNoSeverity";

	public static final String PROBLEM_TEST_INFO_AND_CHILDREN = "problemTest.infoAndChildren";

	public static final String PROBLEM_TEST_ON_SELECTED_WARNING = "problemTest.onSelectedWarning";

	protected static final String PROBLEM_TEST_ON_ANY_ERROR = "problemTest.onAnyError";

	public DeclarativeFilterTest(String testName) {
		super(testName);
	}

	protected ProblemFilter getFilter(String id) {
		Iterator filters = MarkerSupportRegistry.getInstance()
				.getRegisteredFilters().iterator();
		while (filters.hasNext()) {
			ProblemFilter filter = (ProblemFilter) filters.next();
			if (filter.getId().equals(id)) {
				return filter;
			}
		}
		return null;

	}

	String[] getAllFilterNames() {
		return new String[] { PROBLEM_TEST_ON_PROBLEM,
				PROBLEM_TEST_NOT_ON_METHOD, PROBLEM_TEST_ON_METHOD,
				PROBLEM_TEST_SAME_CONTAINER_NO_SEVERITY,
				PROBLEM_TEST_INFO_AND_CHILDREN,
				PROBLEM_TEST_ON_SELECTED_WARNING, PROBLEM_TEST_ON_ANY_ERROR };
	}

}
