
package org.eclipse.ui.tests.keys;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class Bug43597Test extends UITestCase {

    private Font textFont;

    public Bug43597Test(String name) {
        super(name);
    }

    public void testFontReset() {
        String metaCharacter = "\u2325X"; //$NON-NLS-1$

        Display display = Display.getCurrent();
        Shell shell = new Shell(display);
        GridLayout gridLayout = new GridLayout();
        shell.setLayout(gridLayout);
        Text text = new Text(shell, SWT.LEFT);
        textFont = new Font(text.getDisplay(),
                "Lucida Grande", 13, SWT.NORMAL);
		text.setFont(textFont); //$NON-NLS-1$
        text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        shell.pack();
        shell.open();

        text.setText(metaCharacter); //$NON-NLS-1$
        Font fontBefore = text.getFont();

        text.setText(""); //$NON-NLS-1$
        text.setText(metaCharacter);
        Font fontAfter = text.getFont();

		assertEquals("Clearing text resets font.", fontBefore, fontAfter); //$NON-NLS-1$

        shell.close();
        shell.dispose();
    }

	@Override
	protected void doTearDown() throws Exception {
		if (textFont != null) {
			textFont.dispose();
		}
		super.doTearDown();
	}
}
