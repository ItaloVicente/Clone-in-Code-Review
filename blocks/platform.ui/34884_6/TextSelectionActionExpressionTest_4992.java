
package org.eclipse.ui.tests.internal;

import org.eclipse.swt.SWTError;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.URLTransfer;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class TextHandlerTest extends UITestCase {

	public TextHandlerTest(String testName) {
		super(testName);
	}

	public void testEditableText() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		TextControlView view = (TextControlView) window.getActivePage()
				.showView(TextControlView.ID);

		Clipboard clipboard = new Clipboard(window.getWorkbench().getDisplay());
		try {
			clipboard.setContents(new Object[] { "http://www.google.ca" },
					new Transfer[] { URLTransfer.getInstance() });
			processEvents();
			view.updateEnabledState();

			assertFalse(view.getCopyAction().isEnabled());
			assertFalse(view.getCutAction().isEnabled());
			assertFalse(view.getPasteAction().isEnabled());
			assertFalse(view.getSelectAllAction().isEnabled());
			
			view.editableText.setText("Hello");
			processEvents();
			view.updateEnabledState();
			assertFalse(view.getCopyAction().isEnabled());
			assertFalse(view.getCutAction().isEnabled());
			assertFalse(view.getPasteAction().isEnabled());
			assertTrue(view.getSelectAllAction().isEnabled());
			
			view.editableText.setSelection(0, 3);
			processEvents();
			view.updateEnabledState();
			assertTrue(view.getCopyAction().isEnabled());
			assertTrue(view.getCutAction().isEnabled());
			assertFalse(view.getPasteAction().isEnabled());
			assertTrue(view.getSelectAllAction().isEnabled());
			
			clipboard.setContents(new Object[] { "http://www.google.ca" },
					new Transfer[] { TextTransfer.getInstance() });
			processEvents();
			view.updateEnabledState();
			
			assertTrue(view.getCopyAction().isEnabled());
			assertTrue(view.getCutAction().isEnabled());
			assertTrue(view.getPasteAction().isEnabled());
			assertTrue(view.getSelectAllAction().isEnabled());
		} catch (SWTError e) {
			System.err.println(e.getMessage());
		} finally {
			clipboard.dispose();
		}
	}
	
	public void testNonEditableText() throws Exception {
		IWorkbenchWindow window = openTestWindow();
		TextControlView view = (TextControlView) window.getActivePage()
				.showView(TextControlView.ID);

		Clipboard clipboard = new Clipboard(window.getWorkbench().getDisplay());
		try {
			clipboard.setContents(new Object[] { "http://www.google.ca" },
					new Transfer[] { URLTransfer.getInstance() });
			view.nonEditableText.setFocus();
			processEvents();
			view.updateEnabledState();

			assertFalse(view.getCopyAction().isEnabled());
			assertFalse(view.getCutAction().isEnabled());
			assertFalse(view.getPasteAction().isEnabled());
			assertFalse(view.getSelectAllAction().isEnabled());
			
			view.nonEditableText.setText("Hello");
			processEvents();
			view.updateEnabledState();
			assertFalse(view.getCopyAction().isEnabled());
			assertFalse(view.getCutAction().isEnabled());
			assertFalse(view.getPasteAction().isEnabled());
			assertTrue(view.getSelectAllAction().isEnabled());
			
			view.nonEditableText.setSelection(0, 3);
			processEvents();
			view.updateEnabledState();
			assertTrue(view.getCopyAction().isEnabled());
			assertFalse(view.getCutAction().isEnabled());
			assertFalse(view.getPasteAction().isEnabled());
			assertTrue(view.getSelectAllAction().isEnabled());

			clipboard.setContents(new Object[] { "http://www.google.ca" },
					new Transfer[] { TextTransfer.getInstance() });
			processEvents();
			view.updateEnabledState();
			
			assertTrue(view.getCopyAction().isEnabled());
			assertFalse(view.getCutAction().isEnabled());
			assertFalse(view.getPasteAction().isEnabled());
			assertTrue(view.getSelectAllAction().isEnabled());
		} catch (SWTError e) {
			System.err.println(e.getMessage());
		} finally {
			clipboard.dispose();
		}
	}
}
