
package org.eclipse.ui.tests.keys;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroManager;
import org.eclipse.ui.tests.harness.util.AutomationUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class Bug43610Test extends UITestCase {

	public Bug43610Test(String testName) {
		super(testName);
	}

	public void testShiftAlt() {
		IIntroManager introManager= PlatformUI.getWorkbench().getIntroManager();
		introManager.closeIntro(introManager.getIntro());

		Display display = Display.getCurrent();
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.stateMask == SWT.SHIFT) {
					assertEquals(
							"Incorrect key code for 'Shift+Alt+'", SWT.ALT, event.keyCode); //$NON-NLS-1$
				}
			}
		};
		display.addFilter(SWT.KeyDown, listener);

		try {
			AutomationUtil.performKeyCodeEvent(display, SWT.KeyDown, SWT.SHIFT);
			AutomationUtil.performKeyCodeEvent(display, SWT.KeyDown, SWT.ALT);
			AutomationUtil.performKeyCodeEvent(display, SWT.KeyUp, SWT.ALT);
			AutomationUtil.performKeyCodeEvent(display, SWT.KeyUp, SWT.SHIFT);
			AutomationUtil.performKeyCodeEvent(display, SWT.KeyDown, SWT.ESC);
			AutomationUtil.performKeyCodeEvent(display, SWT.KeyUp, SWT.ESC);

			while (display.readAndDispatch())
				;

		} finally {
			display.removeFilter(SWT.KeyDown, listener);
		}
	}
}
