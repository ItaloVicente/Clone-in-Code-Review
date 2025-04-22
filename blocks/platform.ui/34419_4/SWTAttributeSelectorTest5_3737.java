package org.akrogen.tkui.css.swt.selectors.attribute;

import java.io.StringReader;

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.swt.engine.CSSSWTEngineImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class SWTAttributeSelectorTest4 {

	public static void main(String[] args) {
		try {
			Display display = new Display();
			CSSEngine engine = new CSSSWTEngineImpl(display);
			engine.parseStyleSheet(new StringReader(
					"Label[foo^=\"bar\"] {color:red} Label {color:green}"));
			
			Shell shell = new Shell(display, SWT.SHELL_TRIM);
			FillLayout layout = new FillLayout();
			shell.setLayout(layout);

			Composite panel1 = new Composite(shell, SWT.NONE);
			panel1.setLayout(new FillLayout());

			Label label1 = new Label(panel1, SWT.NONE);
			label1.setText("Label foo");
			label1.setData("foo", "bar bla bla bla");

			Label label2 = new Label(panel1, SWT.NONE);
			label2.setText("Label ");

			engine.applyStyles(shell, true);

			shell.pack();
			shell.open();

			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}

			display.dispose();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
