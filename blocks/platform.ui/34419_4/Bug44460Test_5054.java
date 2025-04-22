
package org.eclipse.ui.tests.keys;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.keys.SWTKeySupport;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class Bug43800Test extends UITestCase {

    public Bug43800Test(String testName) {
        super(testName);
    }

    public void testTruncatingCast() {
        Event event = new Event();
        event.keyCode = SWT.ARROW_LEFT;
        event.character = 0x00;
        event.stateMask = 0x00;

        int accelerator = SWTKeySupport
                .convertEventToUnmodifiedAccelerator(event);
        assertEquals("Arrow_Left key truncated.", SWT.ARROW_LEFT, accelerator); //$NON-NLS-1$
    }
}
