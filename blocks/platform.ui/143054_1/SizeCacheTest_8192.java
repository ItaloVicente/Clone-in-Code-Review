package org.eclipse.ui.tests.forms.widgets;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.tests.forms.layout.ControlFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ScrolledFormTest {
	private Shell shell;
	private Display display;

	@Before
	public void setUp() throws Exception {
		display = Display.getDefault();
		shell = new Shell(display);
	}

	@After
	public void tearDown() throws Exception {
		shell.dispose();
	}

	public void disabled_testWrapping() {
		runTest(new ScrollTestData().setMax(200, 20).expand(false, true).expectScroll(true, false), 200, 83);
		runTest(new ScrollTestData().setMax(200, 400).expand(false, true).setMinWidth(25).expectScroll(false, true), 83,
				963);
		runTest(new ScrollTestData().setMax(200, 400).setMinWidth(25).expectScroll(false, true), 83, 963);
		runTest(new ScrollTestData().setMax(200, 400).expand(true, true).setMinWidth(25).expectScroll(false, true), 83,
				963);
		runTest(new ScrollTestData().setMax(50, 400).expand(true, true).expectScroll(false, true), 83, 240);
		runTest(new ScrollTestData().setMax(50, 400).expand(true, false).expectScroll(false, true), 83, 240);
		runTest(new ScrollTestData().setMax(200, 400).expand(true, false).setMinWidth(25).expectScroll(false, true), 83,
				963);
		runTest(new ScrollTestData().setMax(200, 20).expand(true, true).expectScroll(true, false), 200, 83);
	}

	@Test
	public void testVerticalExpand() {
		runTest(new ScrollTestData().setMax(50, 20).expand(false, true), 50, 100);
		runTest(new ScrollTestData().setMax(50, 400).expand(false, true).expectScroll(false, true), 50, 400);
		runTest(new ScrollTestData().setMax(200, 400).expand(false, true).expectScroll(true, true), 200, 400);
		runTest(new ScrollTestData().setMax(50, 20).expand(false, true).setMinWidth(25), 50, 100);
		runTest(new ScrollTestData().setMax(200, 400).expand(false, true).setMinWidth(150).expectScroll(true, true),
				150, 533);
	}

	@Test
	public void testNoExpand() {
		runTest(new ScrollTestData().setMax(50, 20), 50, 20);
		runTest(new ScrollTestData().setMax(50, 400).expectScroll(false, true), 50, 400);
		runTest(new ScrollTestData().setMax(200, 400).expectScroll(true, true), 200, 400);
		runTest(new ScrollTestData().setMax(200, 20).expectScroll(true, false), 200, 20);
		runTest(new ScrollTestData().setMax(50, 20).setMinWidth(25), 50, 20);
		runTest(new ScrollTestData().setMax(200, 400).setMinWidth(150).expectScroll(true, true), 150, 533);
	}

	@Test
	public void testHorizontalExpand() {
		runTest(new ScrollTestData().setMax(50, 20).expand(true, false), 100, 11);
		runTest(new ScrollTestData().setMax(200, 400).expand(true, false).expectScroll(true, true), 200, 400);
		runTest(new ScrollTestData().setMax(200, 20).expand(true, false).expectScroll(true, false), 200, 20);
		runTest(new ScrollTestData().setMax(50, 20).expand(true, false).setMinWidth(25), 100, 11);
		runTest(new ScrollTestData().setMax(200, 400).expand(true, false).setMinWidth(150).expectScroll(true, true),
				150, 533);
	}

	@Test
	public void testExpandBoth() {
		runTest(new ScrollTestData().setMax(50, 20).expand(true, true), 100, 100);
		runTest(new ScrollTestData().setMax(200, 400).expand(true, true).expectScroll(true, true), 200, 400);
		runTest(new ScrollTestData().setMax(50, 20).expand(true, true).setMinWidth(25), 100, 100);
		runTest(new ScrollTestData().setMax(200, 400).expand(true, true).setMinWidth(150).expectScroll(true, true), 150,
				533);
	}

	public static Point computeLayout(Shell shell, ScrollTestData testData) {
		ScrolledForm form = new ScrolledForm(shell);
		Composite parent = form.getBody();
		form.setExpandHorizontal(testData.expandHorizontal());
		form.setExpandVertical(testData.expandVertical());
		Layout layout;
		if (testData.useLayoutExtension()) {
			layout = ControlFactory.createLayout(testData.getMinWidth(), testData.getMaxWidth(),
					testData.getMaxHeight());
		} else {
			layout = ControlFactory.createLayout(testData.getMaxWidth(),
					testData.getMaxHeight());
		}
		parent.setLayout(layout);
		form.setMinSize(testData.getFormMinX(), testData.getFormMinY());
		int hAdjust = form.getVerticalBar().getSize().x - 17;
		int vAdjust = form.getHorizontalBar().getSize().y - 17;
		form.setSize(100 + (testData.expectsVScroll() ? hAdjust : 0), 100 + (testData.expectsHScroll() ? vAdjust : 0));
		form.layout(true);
		while (!shell.isDisposed() && shell.getDisplay().readAndDispatch()) {
		}
		assertEquals("Horizontal scrollbar visibility was wrong for " + testData, testData.expectsHScroll(),
				form.getHorizontalBar().getVisible());
		assertEquals("Vertical scrollbar visibility was wrong for " + testData, testData.expectsVScroll(),
				form.getVerticalBar().getVisible());
		Point result = parent.getSize();
		form.dispose();
		return result;
	}

	private void runTest(ScrollTestData testData, int expectedWidth, int expectedHeight) {
		Point result = computeLayout(shell, testData);
		boolean heightOkay = (Math.abs(expectedHeight - result.y) <= 1);
		boolean widthOkay = (expectedWidth == result.x);
		if (!(heightOkay && widthOkay)) {
			assertEquals("Child control had unexpected size for test case " + testData.toString(),
					new Point(expectedWidth, expectedHeight), result);
		}
	}

}
