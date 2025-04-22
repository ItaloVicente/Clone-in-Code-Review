
package org.eclipse.ui.tests.markers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.internal.ide.registry.MarkerHelpRegistry;
import org.eclipse.ui.internal.ide.registry.MarkerHelpRegistryReader;
import org.junit.Test;

import junit.framework.TestCase;

public class MarkerHelpRegistryTest extends TestCase {

	static final String ATT_HELP_CONTEXT = "helpContext";
	static final String ATT_HAS_HELP = "hasHelp";

	static final String DUMP_PRE = "provider state:";
	static final String DUMP_PRE_HAS = "hasHc:";
	static final String DUMP_PRE_GET = "getHc:";
	static final String DUMP_NEXT_MARKER = "m:";

	static final String HELP_CONTEXT_STRING = "helpContextIdHere";

	public MarkerHelpRegistryTest() {
		super("MarkerHelpRegistryTest");
	}

	IMarker markerWithHelp;
	IMarker markerNoHelp;
	IMarker markerWhichMayHaveHelp;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();

		markerWithHelp = wsRoot.createMarker("org.eclipse.ui.tests.testmarker3");
		markerNoHelp = wsRoot.createMarker("org.eclipse.ui.tests.testmarker3");
		markerWhichMayHaveHelp = wsRoot.createMarker("org.eclipse.ui.tests.testmarker3");

		markerWithHelp.setAttribute(ATT_HELP_CONTEXT, HELP_CONTEXT_STRING);
		markerWithHelp.setAttribute(ATT_HAS_HELP, true);

		markerNoHelp.setAttribute(ATT_HELP_CONTEXT, null);
		markerNoHelp.setAttribute(ATT_HAS_HELP, false);

		markerWhichMayHaveHelp.setAttribute(ATT_HELP_CONTEXT, null);
		markerWhichMayHaveHelp.setAttribute(ATT_HAS_HELP, true);

		TestMarkerHelpContextProvider.init();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		markerWithHelp.delete();
		markerNoHelp.delete();
		markerWhichMayHaveHelp.delete();
	}

	@Test
	public void testGetHelpForHelpContextProvider() {
		MarkerHelpRegistry markerHelpRegistry = new MarkerHelpRegistry();
		new MarkerHelpRegistryReader().addHelp(markerHelpRegistry);

		List<IMarker> expectedParamsHasHc = new ArrayList<>();
		List<IMarker> expectedParamsGetHc = new ArrayList<>();

		assertEquals("Expected to get help", HELP_CONTEXT_STRING, markerHelpRegistry.getHelp(markerWithHelp));
		expectedParamsHasHc.add(markerWithHelp);
		expectedParamsGetHc.add(markerWithHelp);

		assertNull("Expected to get no help", markerHelpRegistry.getHelp(markerNoHelp));
		expectedParamsHasHc.add(markerNoHelp);

		assertNull("Expected to get no help", markerHelpRegistry.getHelp(markerWhichMayHaveHelp));
		expectedParamsHasHc.add(markerWhichMayHaveHelp);
		expectedParamsGetHc.add(markerWhichMayHaveHelp);

		assertEquals("Unexpected params for hasHelpContextForMarker", expectedParamsHasHc,
				TestMarkerHelpContextProvider.getParamsHasHc());
		assertEquals("Unexpected params for getHelpContextForMarker", expectedParamsGetHc,
				TestMarkerHelpContextProvider.getParamsGetHc());
	}
}
