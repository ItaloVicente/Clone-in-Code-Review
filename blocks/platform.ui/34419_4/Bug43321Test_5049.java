
package org.eclipse.ui.tests.keys;

import org.eclipse.jface.bindings.keys.KeySequence;
import org.eclipse.jface.bindings.keys.KeySequenceText;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class Bug43168Test extends UITestCase {

    public Bug43168Test(String name) {
        super(name);
    }

    public void testStackOverflow() throws ParseException {
        Display display = Display.getCurrent();
        Shell shell = new Shell(display);
        shell.setLayout(new RowLayout());
        Text text = new Text(shell, SWT.BORDER);
        KeySequenceText keySequenceText = new KeySequenceText(text);

        shell.pack();
        shell.open();
        keySequenceText.setKeySequence(KeySequence.getInstance("CTRL+")); //$NON-NLS-1$
        shell.close();
    }
}
