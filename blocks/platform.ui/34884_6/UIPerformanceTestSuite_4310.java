
package org.eclipse.ui.tests.performance;

import java.io.ByteArrayInputStream;

import junit.extensions.TestSetup;
import junit.framework.Test;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class UIPerformanceTestSetup extends TestSetup {

	public static final String PERSPECTIVE1= "org.eclipse.ui.tests.performancePerspective1";
	public static final String PERSPECTIVE2= "org.eclipse.ui.tests.performancePerspective2";

	public static final String PROJECT_NAME = "Performance Project";

	private static final String INTRO_VIEW= "org.eclipse.ui.internal.introview";

    private IProject testProject;
	
	public UIPerformanceTestSetup(Test test) {
		super(test);		
	}

	protected void setUp() throws Exception {
		IWorkbench workbench= PlatformUI.getWorkbench();
		IWorkbenchWindow activeWindow= workbench.getActiveWorkbenchWindow();
		IWorkbenchPage activePage= activeWindow.getActivePage();
		
		activePage.hideView(activePage.findViewReference(INTRO_VIEW));
		
		workbench.showPerspective(PERSPECTIVE1, activeWindow);
		
		boolean wasAutobuilding= ResourceTestHelper.disableAutoBuilding();
		setUpProject();
		ResourceTestHelper.fullBuild();
		if (wasAutobuilding) {
			ResourceTestHelper.enableAutoBuilding();
			EditorTestHelper.calmDown(2000, 30000, 1000);
		}
	}
	
	protected void tearDown() throws Exception {
		
		StackTraceElement[] elements=  new Throwable().getStackTrace();
		for (int i= 0; i < elements.length; i++) {
			StackTraceElement element= elements[i];
			if (element.getClassName().equals("org.eclipse.test.EclipseTestRunner")) {
				PlatformUI.getWorkbench().close();
				break;
			}
		}
	}
	
	private void setUpProject() throws CoreException {
   
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        testProject = workspace.getRoot().getProject(PROJECT_NAME);
        testProject.create(null);
        testProject.open(null);        
        String[] natureIds = { "org.eclipse.jdt.core.javanature" };
        projectDescription.setNatureIds(natureIds);*/
        buildCommand.setBuilderName("org.eclipse.jdt.core.javabuilder");
        projectDescription.setBuildSpec(new ICommand[] { buildCommand });
        testProject.setDescription(projectDescription, null);*/
        
        for (int i = 0; i < EditorPerformanceSuite.EDITOR_FILE_EXTENSIONS.length; i++) {
            createFiles(EditorPerformanceSuite.EDITOR_FILE_EXTENSIONS[i]);
        }
	}

    
    private void createFiles(String ext) throws CoreException {
        for (int i = 0; i < 100; i++) {
            String fileName = i + "." + ext;
	        IFile iFile = testProject.getFile(fileName);
	        iFile.create(new ByteArrayInputStream(new byte[] { '\n' }), true, null);
        }
    }
}
