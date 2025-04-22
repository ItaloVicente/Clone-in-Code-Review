package org.eclipse.ui.tests.markers;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivity;
import org.eclipse.ui.views.markers.internal.ProblemFilter;

public class DeclarativeFilterActivityTest extends DeclarativeFilterTest {

	static final String PROBLEM_FILTER_TEST_ACTIVITY = "problemFilterTestActivity";

	public DeclarativeFilterActivityTest(String testName) {
		super(testName);
	}

	public void testActivityEnablement() {
		enableFilterActivity();
		
		checkFilteredOut(false);
		
		disableFilterActivity();
		checkFilteredOut(true);
		enableFilterActivity();

	}

	private void checkFilteredOut(boolean filteredOut) {
		String[] allFilterNames = getAllFilterNames();
		String failureMessage = filteredOut ? " should be filtered out" : " should not be filtered out";
		for (int i = 0; i < allFilterNames.length; i++) {
			ProblemFilter filter = getFilter(allFilterNames[i]);
			if(filteredOut)
				assertNull("Should filter out " + allFilterNames[i] ,filter);
			else{
			assertNotNull("No filter for " + allFilterNames[i] ,filter);
			assertTrue(allFilterNames[i] + failureMessage, filter.isFilteredOutByActivity() == filteredOut);
			}
		}
		
	}

	private void enableFilterActivity() {
		IActivity activity = PlatformUI.getWorkbench().getActivitySupport()
				.getActivityManager().getActivity(PROBLEM_FILTER_TEST_ACTIVITY);
		Set enabledActivityIds = new HashSet(PlatformUI.getWorkbench()
				.getActivitySupport().getActivityManager()
				.getEnabledActivityIds());
		
		if (!enabledActivityIds.contains(activity.getId()))
			enabledActivityIds.add(activity.getId());
		
		PlatformUI.getWorkbench().getActivitySupport().setEnabledActivityIds(
				enabledActivityIds);
	}
	
	private void disableFilterActivity() {
		IActivity activity = PlatformUI.getWorkbench().getActivitySupport()
				.getActivityManager().getActivity(PROBLEM_FILTER_TEST_ACTIVITY);
		Set enabledActivityIds = new HashSet(PlatformUI.getWorkbench()
				.getActivitySupport().getActivityManager()
				.getEnabledActivityIds());
		
		if (enabledActivityIds.contains(activity.getId()))
			enabledActivityIds.remove(activity.getId());
		
		PlatformUI.getWorkbench().getActivitySupport().setEnabledActivityIds(
				enabledActivityIds);
	}

}
