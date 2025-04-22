
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
	static final String HELP_CONTEXT_STRING = "helpContextIdHere";
	private static final String TEST_MARKER_TYPE = "org.eclipse.ui.tests.testmarker3";

	private IMarker markerWithHelp;
	private IMarker markerNoHelp;
	private IMarker markerWhichMayHaveHelp;

	public MarkerHelpRegistryTest() {
		super("MarkerHelpRegistryTest");
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();

		markerWithHelp = wsRoot.createMarker(TEST_MARKER_TYPE);
		markerNoHelp = wsRoot.createMarker(TEST_MARKER_TYPE);
		markerWhichMayHaveHelp = wsRoot.createMarker(TEST_MARKER_TYPE);

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
		markerWithHelp.delete();
		markerNoHelp.delete();
		markerWhichMayHaveHelp.delete();
		super.tearDown();
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
