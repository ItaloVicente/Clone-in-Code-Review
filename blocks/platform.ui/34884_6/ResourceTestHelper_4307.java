
package org.eclipse.ui.tests.performance;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.test.performance.Dimension;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.tests.performance.parts.PerformanceProblemsView;
import org.eclipse.ui.texteditor.MarkerUtilities;

public class ProblemsViewPerformanceTest extends BasicPerformanceTest {

	private String EMPTY_PERSPECTIVE_ID = "org.eclipse.ui.tests.harness.util.EmptyPerspective";

	private String PROBLEMS_VIEW_ID = "org.eclipse.ui.tests.performance.problemsView";

	public ProblemsViewPerformanceTest(String testName) {
		super(testName);
	}

	public void testPopulation() {
		IWorkbenchWindow window = openTestWindow(EMPTY_PERSPECTIVE_ID);
		final IWorkbenchPage page = window.getActivePage();

		PerformanceProblemsView view;

		try {
			view = (PerformanceProblemsView) page.showView(PROBLEMS_VIEW_ID);
		} catch (PartInitException e) {
			e.printStackTrace();
			fail();
			return;
		}

		tagIfNecessary("UI - Problems View population",
				Dimension.ELAPSED_PROCESS);

		for (int i = 0; i < 100; i++) {
			createMarkers();
			processEvents();
			startMeasuring();
			while (view.getTreeWidget().getItemCount() == 0)
				processEvents();
			stopMeasuring();
			removeMarkers();
			while (view.getTreeWidget().getItemCount() > 0)
				processEvents();
		}
		commitMeasurements();
		assertPerformance();

	}

	private void removeMarkers() {

		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

			IMarker[] markers = root.findMarkers(IMarker.PROBLEM, false,
					IResource.DEPTH_ZERO);

			for (int i = 0; i < markers.length; i++) {
				String message = (String) markers[i]
						.getAttribute(IMarker.MESSAGE);

				if (message != null && message.startsWith("this is a test")) {
					markers[i].delete();
				}
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

	}

	private void createMarkers() {
		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			Map attribs = new HashMap();
			for (int i = 0; i < 1000; i++) {
				attribs.put(IMarker.SEVERITY, new Integer(
						IMarker.SEVERITY_ERROR));
				attribs.put(IMarker.MESSAGE, "this is a test " + i);
				MarkerUtilities.createMarker(root, attribs, IMarker.PROBLEM);
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

	}

}
