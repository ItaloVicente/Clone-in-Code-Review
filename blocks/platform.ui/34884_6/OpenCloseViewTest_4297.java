
package org.eclipse.ui.tests.performance;

import java.util.HashMap;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.test.performance.Dimension;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveRegistry;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.WorkbenchPlugin;

public class OpenClosePerspectiveTest extends BasicPerformanceTest {

    private String id;

    public OpenClosePerspectiveTest(String id, int tagging) {
        super("testOpenClosePerspectives:" + id, tagging);
        this.id = id;
    }
    
    protected void runTest() throws Throwable {
        final IPerspectiveRegistry registry = WorkbenchPlugin.getDefault()
                .getPerspectiveRegistry();
        final IPerspectiveDescriptor perspective1 = registry
                .findPerspectiveWithId(id);

        if (perspective1 == null) {
            System.out.println("Unknown perspective id: " + id);
            return;
        }
        
        IWorkbenchWindow window = openTestWindow();          
        final IWorkbenchPage activePage = window.getActivePage();
        
        activePage.setPerspective(perspective1);
        IViewReference [] refs = activePage.getViewReferences();
        String [] ids = new String[refs.length];
        for (int i = 0; i < refs.length; i++) {
            ids[i] = refs[i].getId();
        }
        closePerspective(activePage);
        for (int i = 0; i < ids.length; i++) {
            activePage.showView(ids[i]);
        }      

        tagIfNecessary("UI - Open/Close " + perspective1.getLabel() + " Perspective", Dimension.ELAPSED_PROCESS);
        
        exercise(new TestRunnable() {
            public void run() throws Exception {
                processEvents();
                EditorTestHelper.calmDown(500, 30000, 500);
                
                startMeasuring();
                activePage.setPerspective(perspective1);
                processEvents();      
                closePerspective(activePage);
                processEvents(); 
                stopMeasuring();
            } 
        });
        
        commitMeasurements();
        assertPerformance();
    }

    private void closePerspective(IWorkbenchPage activePage) {
		IPerspectiveDescriptor persp = activePage.getPerspective();

		ICommandService commandService = fWorkbench
				.getService(ICommandService.class);
		Command command = commandService
				.getCommand("org.eclipse.ui.window.closePerspective");

		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(IWorkbenchCommandConstants.WINDOW_CLOSE_PERSPECTIVE_PARM_ID,
				persp.getId());

		ParameterizedCommand pCommand = ParameterizedCommand.generateCommand(
				command, parameters);

		IHandlerService handlerService = fWorkbench
				.getService(IHandlerService.class);
		try {
			handlerService.executeCommand(pCommand, null);
		} catch (ExecutionException e1) {
		} catch (NotDefinedException e1) {
		} catch (NotEnabledException e1) {
		} catch (NotHandledException e1) {
		}

	}
}
