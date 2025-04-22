
package org.eclipse.ui.tests.performance;

import org.eclipse.core.resources.IFile;
import org.eclipse.test.performance.Dimension;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.ide.IDE;

public class OpenCloseEditorTest extends BasicPerformanceTest {

	private String extension;

	public OpenCloseEditorTest(String extension, int tagging) {
		super("testOpenAndCloseEditors:" + extension, tagging);
		this.extension = extension;
	}

	protected void runTest() throws Throwable {
		final IFile file = getProject().getFile("1." + extension);
		assertTrue(file.exists());
		
		IWorkbenchWindow window = openTestWindow(UIPerformanceTestSetup.PERSPECTIVE1);
		final IWorkbenchPage activePage = window.getActivePage();

        exercise(new TestRunnable() {
            public void run() throws Exception {
                startMeasuring();
                for (int j = 0; j < 10; j++) {
                    IEditorPart part = IDE.openEditor(activePage, file, true);
                    processEvents();
                    activePage.closeEditor(part, false);
                    processEvents();
                    
                }
                stopMeasuring();
            } 
        });
        
		tagIfNecessary("UI - Open/Close Editor", Dimension.ELAPSED_PROCESS);
		commitMeasurements();
		assertPerformance();
	}
}
