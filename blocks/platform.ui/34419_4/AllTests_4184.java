 
package org.eclipse.ui.tests.forms.util;

import junit.framework.TestCase;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class FormToolkitTest extends TestCase {
	
	public void testDispose() {
		Display display = Display.getCurrent();
		FormToolkit toolkit = new FormToolkit(display);
		toolkit.dispose();
		toolkit.dispose();
    }   
	
}
