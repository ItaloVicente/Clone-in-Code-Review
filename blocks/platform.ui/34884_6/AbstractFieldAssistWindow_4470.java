package org.eclipse.jface.tests.fieldassist;

import junit.framework.TestCase;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public abstract class AbstractFieldAssistTestCase extends TestCase {

	private AbstractFieldAssistWindow window;
	
	private Shell anotherShell;

	private int originalShellCount;

	@Override
	final protected void setUp() throws Exception {
		super.setUp();
		Display display = getDisplay();
		anotherShell = new Shell(display);
		new Text(anotherShell, SWT.SINGLE);
		anotherShell.open();
		spinEventLoop();
		originalShellCount = display.getShells().length;
		window = createFieldAssistWindow();
		assertNotNull(window);
	}

	@Override
	final protected void tearDown() throws Exception {
		if (window != null) {
			spinEventLoop();
		}
		closeFieldAssistWindow();
		anotherShell.close();

		super.tearDown();
	}
	
	protected Display getDisplay() {
		Display display = Display.getCurrent();
		if (display == null)
			display = Display.getDefault();
		return display;
	}

	protected void closeFieldAssistWindow() {
		if (window != null) {
			window.close();
			window = null;
		}
	}

	protected abstract AbstractFieldAssistWindow createFieldAssistWindow();

	protected AbstractFieldAssistWindow getFieldAssistWindow() {
		return window;
	}

	protected void spinEventLoop() {

		Display disp = getDisplay();
		while (disp.readAndDispatch()) {
			;
		}
	}

	protected void ensurePopupIsUp() {
		if (window.getAutoActivationDelay() == 0) {
			spinEventLoop();
		} else {
			long time = System.currentTimeMillis();
			long target = time + window.getAutoActivationDelay();
			while (target > time) {
				spinEventLoop(); // remain responsive
				time = System.currentTimeMillis();
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			spinEventLoop();
		}
	}

	protected void sendFocusInToControl() {
		window.getFieldAssistControl().setFocus();
		spinEventLoop();
	}
	
	protected void sendFocusElsewhere() {
		anotherShell.setFocus();
		spinEventLoop();
	}
	
	protected void sendFocusToPopup() {
		getFieldAssistWindow().getContentProposalAdapter().setProposalPopupFocus();
		spinEventLoop();
	}

	protected void sendKeyDownToControl(char character) {
		sendFocusInToControl();
		Event event = new Event();
		event.type = SWT.KeyDown;
		event.character = character;
		assertTrue("unable to post event to display queue for test case", window.getDisplay().post(event));
		spinEventLoop();
	}

	protected void sendKeyDownToControl(KeyStroke keystroke) {
		sendFocusInToControl();
		Event event = new Event();
		event.type = SWT.KeyDown;
		event.keyCode = keystroke.getNaturalKey();
		assertTrue("unable to post event to display queue for test case", window.getDisplay().post(event));
		spinEventLoop();
	}

	protected void assertOneShellUp() {
		spinEventLoop();
		assertEquals("There should only be one shell up, the dialog",
				originalShellCount + 1, window.getDisplay().getShells().length);
	}

	protected void assertTwoShellsUp() {
		spinEventLoop();
		assertEquals(
				"There should two shells up, the dialog and the proposals dialog",
				originalShellCount + 2, window.getDisplay().getShells().length);
	}

	protected void setControlContent(String text) {
		window.getControlContentAdapter().setControlContents(
				window.getFieldAssistControl(), text, text.length());

	}

	protected String getControlContent() {
		return window.getControlContentAdapter().getControlContents(
				window.getFieldAssistControl());

	}

}
