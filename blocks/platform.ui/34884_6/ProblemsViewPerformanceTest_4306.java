package org.eclipse.ui.tests.performance;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.test.performance.Dimension;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.WorkbenchPlugin;

public class PerspectiveSwitchTest extends BasicPerformanceTest {

    private String id1;
    private String id2;
    private String activeEditor;

    public PerspectiveSwitchTest(String [] ids, int tagging) {
        super("testPerspectiveSwitch:" + ids[0] + "," + ids[1] + ",editor " + ids[2], tagging);
        this.id1 = ids[0];
        this.id2 = ids[1];
        this.activeEditor = ids[2];
    }
	
    protected void runTest() throws CoreException, WorkbenchException {
        final IPerspectiveRegistry registry = WorkbenchPlugin.getDefault()
                .getPerspectiveRegistry();
        final IPerspectiveDescriptor perspective1 = registry
                .findPerspectiveWithId(id1);
        final IPerspectiveDescriptor perspective2 = registry
                .findPerspectiveWithId(id2);

        if (perspective1 == null) {
            System.out.println("Unknown perspective ID: " + id1);
            return;
        }

        if (perspective2 == null) {
            System.out.println("Unknown perspective ID: " + id2);
            return;
        }
        
        IWorkbenchWindow window = openTestWindow(id1);
        final IWorkbenchPage page = window.getActivePage();
        assertNotNull(page);
        page.setPerspective(perspective2);
        
        IFile aFile = getProject().getFile(activeEditor);
        assertTrue(aFile.exists());

        IDE.openEditor(page, aFile, true);

       	tagIfNecessary("UI - Perspective Switch", Dimension.ELAPSED_PROCESS);
        
        exercise(new TestRunnable() {
            public void run() throws Exception {
                processEvents();
                
                startMeasuring();
                page.setPerspective(perspective1);
                processEvents();
                page.setPerspective(perspective2);
                processEvents();
                stopMeasuring();
            } 
        });
        
        commitMeasurements();
        assertPerformance();
    }
}
