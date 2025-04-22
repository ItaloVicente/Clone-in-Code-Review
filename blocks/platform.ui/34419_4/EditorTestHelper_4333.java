package org.eclipse.ui.tests.performance;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.ide.IDE;

public class EditorSwitchTest extends BasicPerformanceTest {

	private String extension1;

	private String extension2;

	public EditorSwitchTest(String[] pair) {
		super("testEditorSwitch:" + pair[0] + "," + pair[1]);
		extension1 = pair[0];
		extension2 = pair[1];
	}

	protected void runTest() throws CoreException {

		IWorkbenchWindow window = openTestWindow(UIPerformanceTestSetup.PERSPECTIVE1);
		final IWorkbenchPage activePage = window.getActivePage();
		final IFile file1 = getProject().getFile("1." + extension1);
		assertTrue(file1.exists());
		final IFile file2 = getProject().getFile("1." + extension2);
		assertTrue(file2.exists());
		IDE.openEditor(activePage, file1, true);
		IDE.openEditor(activePage, file2, true);
		processEvents();
        EditorTestHelper.calmDown(500, 30000, 500);
        waitForBackgroundJobs();
        
		for (int j = 0; j < 100; j++) {

			startMeasuring();
			for (int i = 0; i < 12; i++) {
                IDE.openEditor(activePage, file1, true);
                processEvents();
                IDE.openEditor(activePage, file2, true);
                processEvents();
			}
			stopMeasuring();
            EditorTestHelper.calmDown(500, 30000, 100);
		}
        
		commitMeasurements();
		assertPerformance();
	}
}
