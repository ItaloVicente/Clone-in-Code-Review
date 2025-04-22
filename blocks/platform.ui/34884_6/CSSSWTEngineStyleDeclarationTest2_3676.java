package org.akrogen.tkui.css.swt.engine.styleDeclaration;

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.swt.engine.CSSSWTEngineImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class CSSSWTEngineStyleDeclarationTest {

	public static void main(String[] args) {
		try {
			Display display = new Display();
			CSSEngine engine = new CSSSWTEngineImpl(display);

			Shell shell = new Shell(display, SWT.SHELL_TRIM);
			FillLayout layout = new FillLayout();
			shell.setLayout(layout);

			Composite panel1 = new Composite(shell, SWT.NONE);
			panel1.setLayout(new FillLayout());

			Label label1 = new Label(panel1, SWT.NONE);
			label1.setText("Label 0 [color:red;]");
			engine.parseAndApplyStyleDeclaration(label1, "color:red;");

			Text text1 = new Text(panel1, SWT.NONE);
			text1.setText("bla bla bla...");

			Label label2 = new Label(panel1, SWT.NONE);
			label2.setText("Label 2 [color:blue;]");
			engine.parseAndApplyStyleDeclaration(label2, "color:blue;");

			Label label3 = new Label(panel1, SWT.NONE);
			label3.setText("Label 3 [color:yellow;]");
			engine.parseAndApplyStyleDeclaration(label3, "color:yellow;");

			Composite panel2 = new Composite(panel1, SWT.NONE);
			panel2.setLayout(new FillLayout());

			Label label4 = new Label(panel2, SWT.NONE);
			label4.setText("Label 4 [color:green;]");
			engine.parseAndApplyStyleDeclaration(label4, "color:green;");

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
