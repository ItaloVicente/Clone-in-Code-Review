package org.eclipse.e4.ui.tests.css.swt;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;

public class Bug419482Test extends CSSSWTTestCase {

	private static final RGB RGB_BLUE = new RGB(0, 0, 255);
	private static final RGB RGB_RED = new RGB(255, 0, 0);

	private CSSEngine engine;
	private ToolBar toolbar1;
	private ToolBar toolbar2;
	private ToolBar toolbar3;

	public void testTwoLevelsWildcard() throws Exception {
		String cssString = "Shell > * > * { color: red; } \n"
				+ "Label { color: blue; }";

		Label label = createTestLabel(cssString);

		RGB rgb = label.getForeground().getRGB();
		assertEquals(RGB_BLUE, rgb);
	}

	public void testOneLevelWildcardOneSpecific() throws Exception {
		String cssString = "Shell > * > Label { color: red; } \n"
				+ "Label { color: blue; }";

		Label label = createTestLabel(cssString);

		RGB rgb = label.getForeground().getRGB();
		assertEquals(RGB_RED, rgb);
	}

	public void testDescendentsWildcard() throws Exception {
		String cssString = "Shell * { color: red; } \n"
				+ "Label { color: blue; }";

		Label label = createTestLabel(cssString);

		RGB rgb = label.getForeground().getRGB();
		assertEquals(RGB_BLUE, rgb);
	}

	public void testDescendentsSpecific() throws Exception {
		String cssString = "Shell Label { color: red; } \n"
				+ "Label { color: blue; }";

		Label label = createTestLabel(cssString);

		RGB rgb = label.getForeground().getRGB();
		assertEquals(RGB_RED, rgb);
	}


	private Label createTestLabel(String styleSheet) {
		Display display = Display.getDefault();
		engine = createEngine(styleSheet, display);

		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayout(new FillLayout());

		Label labelToTest = new Label(composite, SWT.NONE);
		labelToTest.setText("Some label text");

		engine.applyStyles(labelToTest, true);
		return labelToTest;
	}

	public void testOriginalBugReport() {
		String css = "Shell, Shell > *, Shell > * > * {\n" +
				"    background-color: red;\n" +
				"}\n" +
				"ToolBar {\n" +
				"    background-color: blue;\n" +
				"}";

		Display display = Display.getDefault();
		engine = createEngine(css, display);

		Shell shell = createShellWithToolbars(display);

		engine.applyStyles(shell, true);

		assertEquals(RGB_BLUE, toolbar1.getBackground().getRGB());
		assertEquals(RGB_BLUE, toolbar2.getBackground().getRGB());
		assertEquals(RGB_BLUE, toolbar3.getBackground().getRGB());
	}

	public void testOriginalBugReportDifferentOrder() {
		String css = "ToolBar {\n" + "    background-color: blue;\n" + "}"
				+ "Shell, Shell > *, Shell > * > * {\n"
				+ "    background-color: red;\n" + "}\n";

		Display display = Display.getDefault();
		engine = createEngine(css, display);

		Shell shell = createShellWithToolbars(display);

		engine.applyStyles(shell, true);

		assertEquals(RGB_RED, toolbar1.getBackground().getRGB());
		assertEquals(RGB_RED, toolbar2.getBackground().getRGB());
		assertEquals(RGB_BLUE, toolbar3.getBackground().getRGB());
	}

	private Shell createShellWithToolbars(Display display) {
		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		shell.setLayout(new RowLayout(SWT.VERTICAL));

		toolbar1 = new ToolBar(shell, SWT.BORDER);
		Composite composite1 = new Composite(shell, SWT.NONE);
		composite1.setLayout(new RowLayout(SWT.VERTICAL));

		toolbar2 = new ToolBar(composite1, SWT.BORDER);
		Composite composite2 = new Composite(composite1, SWT.NONE);
		composite2.setLayout(new RowLayout(SWT.VERTICAL));

		toolbar3 = new ToolBar(composite2, SWT.BORDER);
		return shell;
	}
}
