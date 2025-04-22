
package org.eclipse.ui.tests.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class WorkbenchOperationStressTests extends UITestCase {

	static int STRESS_TEST_REPETITIONS = 2000;

	static int OPEN_WINDOW_REPETITIONS = 2;

	public WorkbenchOperationStressTests(String name) {
		super(name);
	}

	public void test115761() throws ExecutionException {
		for (int j = 0; j < OPEN_WINDOW_REPETITIONS; j++) {
			IWorkbenchWindow secondWorkbenchWindow = null;
			secondWorkbenchWindow = openTestWindow(IDE.RESOURCE_PERSPECTIVE_ID);
			Display display = secondWorkbenchWindow.getShell().getDisplay();
			IOperationHistory workbenchHistory = secondWorkbenchWindow
					.getWorkbench().getOperationSupport().getOperationHistory();
			IUndoContext workbenchContext = secondWorkbenchWindow
					.getWorkbench().getOperationSupport().getUndoContext();
			workbenchHistory.setLimit(workbenchContext, STRESS_TEST_REPETITIONS);

			for (int i = 0; i < STRESS_TEST_REPETITIONS; i++) {
				IUndoableOperation op = new TestOperation("test");
				op.addContext(workbenchContext);
				workbenchHistory.execute(op, null, null);
			}
			for (int i = 0; i < STRESS_TEST_REPETITIONS; i++) {
				if (i % 2 == 0) {
					workbenchHistory.undo(workbenchContext, null, null);
				}
				if (i % 5 == 0) {
					workbenchHistory.redo(workbenchContext, null, null);
				}
			}

			secondWorkbenchWindow.close();
			boolean go = true;
			while (go) {
				go = display.readAndDispatch();
				
			}
		}
	}
}
