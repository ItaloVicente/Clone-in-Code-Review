
package org.eclipse.ui.tests.fieldassist;

import org.eclipse.jface.tests.fieldassist.AbstractFieldAssistTestCase;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchCommandConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;

public abstract class AbstractContentAssistCommandAdapterTest extends
		AbstractFieldAssistTestCase {

	protected void executeContentAssistHandler() throws Exception {
		IHandlerService handlerService = PlatformUI
				.getWorkbench().getService(IHandlerService.class);
		handlerService.executeCommand(
				IWorkbenchCommandConstants.EDIT_CONTENT_ASSIST, null);
	}
	
	public void testHandlerPromptsPopup() throws Exception {
		getFieldAssistWindow().open();

		sendFocusInToControl();
		executeContentAssistHandler();

		assertTwoShellsUp();
	}

	public void testBug271339EmptyAutoActivationCharacters() throws Exception {
		getFieldAssistWindow().open();

		sendFocusInToControl();
		executeContentAssistHandler();

		assertTwoShellsUp();

		sendKeyDownToControl('o');
		assertTwoShellsUp();
	}

	public void testBug271339EmptyAutoActivationCharacters2() throws Exception {
		getFieldAssistWindow().open();

		sendFocusInToControl();
		sendKeyDownToControl('o');
		
		assertOneShellUp();
	}

	public void testBug271339EmptyAutoActivationCharacters3() throws Exception {
		getFieldAssistWindow().open();

		sendFocusInToControl();
		executeContentAssistHandler();

		assertTwoShellsUp();

		sendKeyDownToControl('o');
		assertTwoShellsUp();

		sendKeyDownToControl(SWT.BS);
		assertTwoShellsUp();
	}
}
