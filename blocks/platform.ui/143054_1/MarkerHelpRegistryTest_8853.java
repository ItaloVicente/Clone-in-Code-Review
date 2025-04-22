
package org.eclipse.ui.tests.markers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.eclipse.ui.internal.ide.registry.MarkerHelpRegistry;
import org.eclipse.ui.internal.ide.registry.MarkerHelpRegistryReader;
import org.eclipse.ui.internal.ide.registry.MarkerQuery;
import org.eclipse.ui.tests.harness.util.TestRunLogUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class MarkerHelpRegistryReaderTest {
	@Rule
	public TestWatcher LOG_TESTRUN = TestRunLogUtil.LOG_TESTRUN;

	@Test
	public void testAddHelpForMarkerHelpMatchChildren() {
		MarkerHelpRegistry registry = mock(MarkerHelpRegistry.class);

		new MarkerHelpRegistryReader().addHelp(registry);


		verify(registry).addHelpQuery(eq(new MarkerQuery("org.eclipse.ui.tests.testmarker", new String[0], true)),
				any(), any());
		verify(registry).addHelpQuery(eq(new MarkerQuery("org.eclipse.ui.tests.testmarker2", new String[0], false)),
				any(), any());
		verify(registry).addHelpQuery(
				eq(new MarkerQuery("org.eclipse.ui.tests.testmarker_child", new String[0], false)), any(), any());
	}

}
