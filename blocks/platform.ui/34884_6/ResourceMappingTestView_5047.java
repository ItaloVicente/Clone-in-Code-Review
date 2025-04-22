package org.eclipse.ui.tests.markers;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.tests.navigator.AbstractNavigatorTest;

public class ResourceMappingMarkersTest extends AbstractNavigatorTest {

	public ResourceMappingMarkersTest(String testName) {
		super(testName);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		createTestFile();
	}

	public void testResourceMappings() {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		ResourceMappingTestView view;

		try {
			view = (ResourceMappingTestView) page
					.showView("org.eclipse.ui.tests.resourceMappingView");
		} catch (PartInitException e) {
			assertTrue(e.getLocalizedMessage(), false);
			return;
		}

		final MarkersTestMarkersView problemView;
		try {
			problemView = (MarkersTestMarkersView) page
					.showView("org.eclipse.ui.tests.markerTests");
		} catch (PartInitException e) {
			assertTrue(e.getLocalizedMessage(), false);
			return;
		}

		IMarker marker=view.addMarkerToFirstProject();
		assertNotNull("Marker creation failed", marker);
		try {
			Job.getJobManager().join(
					problemView.MARKERSVIEW_UPDATE_JOB_FAMILY,
					new NullProgressMonitor());
		} catch (OperationCanceledException e) {
		} catch (InterruptedException e) {
		}
		
		IMarker[] markers=problemView.getCurrentMarkers();
		boolean markerFound = false;
		for (int i = 0; i < markers.length; i++) {
			if(markers[i].equals(marker)){
				markerFound = true;
				break;
			}
		}
		assertTrue("No markers generated",markerFound);
	}
}
