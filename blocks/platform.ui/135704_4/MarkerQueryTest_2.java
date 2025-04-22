
package org.eclipse.ui.tests.markers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.eclipse.ui.internal.ide.registry.MarkerHelpRegistry;
import org.eclipse.ui.internal.ide.registry.MarkerHelpRegistryReader;
import org.eclipse.ui.internal.ide.registry.MarkerQuery;
import org.junit.Test;

import junit.framework.TestCase;

public class MarkerHelpRegistryReaderTest extends TestCase {

	public MarkerHelpRegistryReaderTest() {
		super("MarkerHelpRegistryReaderTest");
	}

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
