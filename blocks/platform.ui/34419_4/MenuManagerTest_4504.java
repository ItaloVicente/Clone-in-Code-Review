package org.eclipse.jface.tests.action;

import junit.framework.TestCase;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class JFaceActionTest extends TestCase {

    private Display display;
    private Shell shell;

    protected JFaceActionTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
	    display = Display.getCurrent();
	    if (display == null) {
	        display = new Display();
	    }
	    shell = new Shell(display);
	    shell.setSize(500, 500);
	    shell.setLayout(new FillLayout());
	    shell.open();
    }

    @Override
    protected void tearDown() throws Exception {
       shell.dispose();
    }

    protected Display getDisplay() {
        return display;
    }

    protected Shell getShell() {
        return shell;
    }

}
