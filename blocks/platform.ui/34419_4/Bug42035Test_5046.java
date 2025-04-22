
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

public class Bug42024Test extends UITestCase {

    private Shell shell = null;

    private KeySequenceText text = null;

    public Bug42024Test(String name) {
        super(name);
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();

        Display display = Display.getCurrent();
        shell = new Shell(display);
        shell.setLayout(new RowLayout());
        text = new KeySequenceText(new Text(shell, SWT.BORDER));

        shell.pack();
        shell.open();
    }

    @Override
	protected void doTearDown() throws Exception {
        super.doTearDown();

        shell.close();
        shell.dispose();

        shell = null;
        text = null;
    }

    public void testInfiniteStrokes() throws ParseException {
        String keySequenceText = "A B C D E F"; //$NON-NLS-1$
        KeySequence keySequence = KeySequence.getInstance(keySequenceText);
        text.setKeyStrokeLimit(KeySequenceText.INFINITE);
        text.setKeySequence(keySequence);
        assertEquals(
                "Infinite limit but sequence changed.", keySequence, text.getKeySequence()); //$NON-NLS-1$
    }

    public void testTruncation() throws ParseException {
        final int length = 4;
        text.setKeyStrokeLimit(length);

        String matchingKeySequenceText = "1 2 3 4"; //$NON-NLS-1$
        KeySequence matchingKeySequence = KeySequence
                .getInstance(matchingKeySequenceText);
        text.setKeySequence(matchingKeySequence);
        assertEquals(
                "Limit of four change four stroke sequence.", matchingKeySequence, text.getKeySequence()); //$NON-NLS-1$

        String longerKeySequenceText = "1 2 3 4 5"; //$NON-NLS-1$
        KeySequence longerKeySequence = KeySequence
                .getInstance(longerKeySequenceText);
        text.setKeySequence(longerKeySequence);
        assertEquals(
                "Limit of four did not truncate to four.", length, text.getKeySequence().getKeyStrokes().length); //$NON-NLS-1$
    }

    public void testZeroStroke() {
        KeySequence zeroStrokeSequence = KeySequence.getInstance();

        text.setKeyStrokeLimit(4);
        text.setKeySequence(zeroStrokeSequence);
        assertEquals(
                "Limit of four changed zero stroke sequence.", zeroStrokeSequence, text.getKeySequence()); //$NON-NLS-1$

        text.setKeyStrokeLimit(KeySequenceText.INFINITE);
        text.setKeySequence(zeroStrokeSequence);
        assertEquals(
                "Infinite limit changed zero stroke sequence.", zeroStrokeSequence, text.getKeySequence()); //$NON-NLS-1$
    }
}
