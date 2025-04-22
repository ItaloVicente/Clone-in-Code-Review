package org.eclipse.ui.internal.ide;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.tests.fieldassist.AbstractFieldAssistTestCase;
import org.eclipse.jface.tests.fieldassist.AbstractFieldAssistWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;

public class DirectoryProposalContentAssistTestCase extends AbstractFieldAssistTestCase {

	private DirectoryProposalContentAssistWindow directoryContentAssistWindow;

	@Override
	protected AbstractFieldAssistWindow createFieldAssistWindow() {
		directoryContentAssistWindow = new DirectoryProposalContentAssistWindow();
		return directoryContentAssistWindow;
	}

	public void assertPopUpIsOpen() {
		assertTrue("Proposal pop-up must be open",
				directoryContentAssistWindow.getContentProposalAdapter().isProposalPopupOpen());
	}

	public void waitForDirectoryContentAssist() {
		directoryContentAssistWindow.getContentAssist().wait(1000);
	}


	public void sendKeyEventToControl(char character) {
		sendKeyDownToControl(character);
		sendKeyUpToControl(character);
	}

	private void sendKeyUpToControl(char character) {
		sendFocusInToControl();
		Event event = new Event();
		event.type = SWT.KeyUp;
		event.character = character;
		assertTrue("unable to post event to display queue for test case",
				getFieldAssistWindow().getDisplay().post(event));
		spinEventLoop();
	}

	public void sendKeyEventToControl(KeyStroke keyStroke) {
		sendKeyDownToControl(keyStroke);
		sendKeyUpToControl(keyStroke);
	}

	private void sendKeyUpToControl(KeyStroke keyStroke) {
		sendFocusInToControl();
		Event event = new Event();
		event.type = SWT.KeyDown;
		event.keyCode = keyStroke.getNaturalKey();
		assertTrue("unable to post event to display queue for test case",
				getFieldAssistWindow().getDisplay().post(event));
		spinEventLoop();
	}

}
