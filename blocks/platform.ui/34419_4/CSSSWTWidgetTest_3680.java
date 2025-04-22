package org.eclipse.e4.ui.tests.css.swt;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.StringReader;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.core.engine.CSSErrorHandler;
import org.eclipse.e4.ui.css.swt.engine.CSSSWTEngineImpl;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.junit.After;
import org.junit.Before;

public class CSSSWTTestCase {

	protected Display display;
	protected CSSEngine engine;

	public CSSEngine createEngine(String styleSheet, Display display) {
		engine = new CSSSWTEngineImpl(display);

		engine.setErrorHandler(new CSSErrorHandler() {
			@Override
			public void error(Exception e) {
				fail(e.getMessage());
			}
		});

		try {
			engine.parseStyleSheet(new StringReader(styleSheet));
		} catch (IOException e) {
			fail(e.getMessage());
		}
		return engine;

	}

	public void clearAndApply(CSSEngine engine, Widget widget, String styleSheet) {

		engine.reset();
		if (styleSheet != null) {

			try {
				engine.parseStyleSheet(new StringReader(styleSheet));
			} catch (IOException e) {
				fail(e.getMessage());
			}
		}

		engine.applyStyles(widget, true, true);
	}

	@Before
	public void setUp() {
		display = Display.getDefault();
	}

	@After
	public void tearDown() {
		display = Display.getDefault();
		if (!display.isDisposed()) {
			for (Shell shell : display.getShells()) {
				shell.dispose();
			}
			display.dispose();
		}
	}

	protected Label createTestLabel(String styleSheet) {
		engine = createEngine(styleSheet, display);

		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);

		Composite panel = new Composite(shell, SWT.NONE);
		panel.setLayout(new FillLayout());

		Label labelToTest = new Label(panel, SWT.NONE);
		labelToTest.setText("Some label text");

		engine.applyStyles(labelToTest, true);

		shell.pack();
		return labelToTest;
	}

}
