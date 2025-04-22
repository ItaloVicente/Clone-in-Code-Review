
package org.eclipse.ui.tests.markers;

import org.eclipse.ui.internal.ide.registry.MarkerHelpRegistry;
import org.eclipse.ui.internal.ide.registry.MarkerHelpRegistryReader;
import org.eclipse.ui.internal.ide.registry.MarkerQuery;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import junit.framework.TestCase;

public class MarkerHelpRegistryReaderTest extends TestCase {

	public MarkerHelpRegistryReaderTest() {
		super("MarkerHelpRegistryReaderTest");
	}

	@Test
	public void testAddHelpForMarkerHelpMatchChildren() {
		MarkerHelpRegistry registry = Mockito.mock(MarkerHelpRegistry.class);
		new MarkerHelpRegistryReader().addHelp(registry);
		Mockito.verify(registry).addHelpQuery(
				ArgumentMatchers.eq(new MarkerQuery("org.eclipse.ui.tests.testmarker", new String[0], true)),
				ArgumentMatchers.any(), ArgumentMatchers.any());
		Mockito.verify(registry).addHelpQuery(
				ArgumentMatchers.eq(new MarkerQuery("org.eclipse.ui.tests.testmarker2", new String[0], false)),
				ArgumentMatchers.any(), ArgumentMatchers.any());
		Mockito.verify(registry).addHelpQuery(
				ArgumentMatchers.eq(new MarkerQuery("org.eclipse.ui.tests.testmarker_child", new String[0], false)),
				ArgumentMatchers.any(), ArgumentMatchers.any());
	}

}
