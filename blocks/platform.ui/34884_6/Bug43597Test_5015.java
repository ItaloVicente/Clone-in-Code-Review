
package org.eclipse.ui.tests.keys;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroManager;
import org.eclipse.ui.tests.harness.util.AutomationUtil;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class Bug43538Test extends UITestCase {

	public Bug43538Test(String testName) {
		super(testName);
	}

	public void testCtrlSpace() {
		IIntroManager introManager= PlatformUI.getWorkbench().getIntroManager();
		introManager.closeIntro(introManager.getIntro());

		Display display = Display.getCurrent();
		Listener listener = new Listener() {
			int count = 0;

			@Override
			public void handleEvent(Event event) {
				if (event.stateMask == SWT.CTRL) {
					assertEquals(
							"Multiple key down events for 'Ctrl+Space'", 0, count++); //$NON-NLS-1$
				}
			}
		};
		display.addFilter(SWT.KeyDown, listener);
		try {

			AutomationUtil.performKeyCodeEvent(display, SWT.KeyDown, SWT.CONTROL);
			AutomationUtil.performKeyCodeEvent(display, SWT.KeyDown, Action.findKeyCode("SPACE")); //$NON-NLS-1$
			AutomationUtil.performKeyCodeEvent(display, SWT.KeyUp, Action.findKeyCode("SPACE")); //$NON-NLS-1$
			AutomationUtil.performKeyCodeEvent(display, SWT.KeyUp, SWT.CONTROL);

			while (display.readAndDispatch())
				;

		} finally {
			display.removeFilter(SWT.KeyDown, listener);
		}
	}
}
