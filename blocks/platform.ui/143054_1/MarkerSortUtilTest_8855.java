
package org.eclipse.ui.tests.markers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.internal.ide.registry.MarkerQuery;
import org.eclipse.ui.tests.harness.util.TestRunLogUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class MarkerQueryTest {
	@Rule
	public TestWatcher LOG_TESTRUN = TestRunLogUtil.LOG_TESTRUN;

	private IMarker marker;
	private IMarker child_marker;

	@Before
	public void setUp() throws Exception {
		IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();

		marker = wsRoot.createMarker("org.eclipse.ui.tests.testmarker");
		child_marker = wsRoot.createMarker("org.eclipse.ui.tests.testmarker_child");
	}

	@After
	public void tearDown() throws Exception {
		marker.delete();
		child_marker.delete();
	}

	@Test
	public void testPerformQueryForMatchChildren() throws Exception{
		MarkerQuery queryWithMatchChildren = new MarkerQuery("org.eclipse.ui.tests.testmarker", new String[0], true);
		MarkerQuery queryNoMatchChildren = new MarkerQuery("org.eclipse.ui.tests.testmarker", new String[0], false);

		assertNotNull("Query with match children was not successfull for equal type.",
				queryWithMatchChildren.performQuery(marker));
		assertNotNull("Query with match children was not successfull for child type.",
				queryWithMatchChildren.performQuery(child_marker));

		assertNotNull("Query without match children was not successfull for equal type.",
				queryNoMatchChildren.performQuery(marker));
		assertNull("Query without match children was falsly successfull for child type.",
				queryNoMatchChildren.performQuery(child_marker));
	}

}
