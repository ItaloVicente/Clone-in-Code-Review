package org.eclipse.e4.ui.tests.css.swt;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;


public class GradientTest extends CSSSWTTestCase {

	static final RGB RED = new RGB(255, 0, 0);
	static final RGB GREEN = new RGB(0, 255, 0);
	static final RGB BLUE = new RGB(0, 0, 255);
	static final RGB WHITE = new RGB(255, 255, 255);

	protected CTabFolder createTestCTabFolder(String styleSheet) {
		engine = createEngine(styleSheet, display);

		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		FillLayout layout = new FillLayout();
		shell.setLayout(layout);
		Composite panel = new Composite(shell, SWT.NONE);
		panel.setLayout(new FillLayout());

		CTabFolder folderToTest = new CTabFolder(panel, SWT.NONE);
		CTabItem tab1 = new CTabItem(folderToTest, SWT.NONE);
		tab1.setText("A TAB ITEM");

		engine.applyStyles(folderToTest, true);

		shell.pack();
		return folderToTest;
	}

	@Test
	public void testGradients() {
		CTabFolder folderToTest = createTestCTabFolder(
				"CTabItem:selected { background-color: #FF0000 #0000FF}");
		assertEquals(RED, getSelectionBackgroundBegin(folderToTest).getRGB()); //gradient begin
		assertEquals(BLUE, folderToTest.getSelectionBackground().getRGB()); //gradient end
	}

	@Test
	public void testDefaultPercents() {
		CTabFolder folderToTest = createTestCTabFolder(
				"CTabItem:selected { background-color: #FF0000 #0000FF}");
		assertEquals(RED, getSelectionBackgroundBegin(folderToTest).getRGB()); //gradient begin
		assertEquals(BLUE, folderToTest.getSelectionBackground().getRGB()); //gradient end
		assertArrayEquals(new int[] { 100 }, getSelectionGradientPercents(folderToTest)); // default
	}

	@Test
	public void testDefaultManyPercents() {
		CTabFolder folderToTest = createTestCTabFolder(
				"CTabItem:selected { background-color: red green blue yellow}");
		assertArrayEquals(new int[] { 33, 67, 100 }, getSelectionGradientPercents(folderToTest)); // default
	}

	@Test
	public void testSpecifiedPercents() {
		CTabFolder folderToTest = createTestCTabFolder(
				"CTabItem:selected { background-color: #FF0000 #0000FF 53%}");
		assertArrayEquals(new int[] { 53 }, getSelectionGradientPercents(folderToTest));
	}

	@Test
	public void testManyColorsAndSpecifiedManyPercents() {
		CTabFolder folderToTest = createTestCTabFolder(
				"CTabItem:selected { background-color: #FF0000 #00FF00 #0000FF 22% 44%}");
		assertEquals(RED, getSelectionBackgroundBegin(folderToTest).getRGB()); //gradient begin
		assertEquals(GREEN, getSelectionBackground(folderToTest, 1).getRGB()); //2nd gradient
		assertEquals(BLUE, folderToTest.getSelectionBackground().getRGB()); //gradient end
		assertArrayEquals(new int[] { 22, 44 }, getSelectionGradientPercents(folderToTest));
	}

	@Test
	public void testBadPercents() {
		CTabFolder folderToTest = createTestCTabFolder(
				"CTabItem:selected { background-color: red green blue yellow 10%}");
		assertArrayEquals(new int[] { 33, 67, 100 }, getSelectionGradientPercents(folderToTest)); // default
	}

	@Test
	public void testBadColors() {
		CTabFolder folderToTest = createTestCTabFolder(
				"CTabItem:selected { background-color: #FF0000 notAColor #0000FF}");
		assertEquals(RED, getSelectionBackgroundBegin(folderToTest).getRGB()); //gradient begin
		assertEquals(BLUE, folderToTest.getSelectionBackground().getRGB()); //gradient end
		assertArrayEquals(new int[] { 100 }, getSelectionGradientPercents(folderToTest)); // default
	}

	@Test
	public void testAboveRangePercents() {
		CTabFolder folderToTest = createTestCTabFolder(
				"CTabItem:selected { background-color: #FF0000 #00FF00 #0000FF 20% 110%}");
		assertEquals(RED, getSelectionBackgroundBegin(folderToTest).getRGB()); //gradient begin
		assertEquals(BLUE, folderToTest.getSelectionBackground().getRGB()); //gradient end
		assertArrayEquals(new int[] { 50, 100 }, getSelectionGradientPercents(folderToTest)); // default
	}

	@Test
	public void testBelowRangePercents() {
		CTabFolder folderToTest = createTestCTabFolder(
				"CTabItem:selected { background-color: #FF0000 #00FF00 #0000FF -20% 50%}");
		assertEquals(RED, getSelectionBackgroundBegin(folderToTest).getRGB()); //gradient begin
		assertEquals(BLUE, folderToTest.getSelectionBackground().getRGB()); //gradient end
		assertArrayEquals(new int[] { 50, 100 }, getSelectionGradientPercents(folderToTest)); // default
	}

	@Test
	public void testAltSyntax() {
		CTabFolder folderToTest = createTestCTabFolder(
				"CTabItem:selected { background-color: gradient, rgb(140,140,140), rgb(48,48,48), 100%;");
		assertEquals(new RGB(140,140,140), getSelectionBackgroundBegin(folderToTest).getRGB()); //gradient begin
		assertEquals(new RGB(48,48,48), folderToTest.getSelectionBackground().getRGB()); //gradient end
		assertArrayEquals(new int[] { 100 }, getSelectionGradientPercents(folderToTest)); // default
	}




	Color getSelectionBackgroundBegin(CTabFolder folder) {
		return getSelectionBackground(folder, 0);
	}

	Color getSelectionBackground(CTabFolder folder, int i) {
		try {
			Field field = folder.getClass().getDeclaredField("selectionGradientColors");
			field.setAccessible(true);
			return ((Color[]) field.get(folder))[i];
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	int[] getSelectionGradientPercents(CTabFolder folder) {
		try {
			Field field = folder.getClass().getDeclaredField("selectionGradientPercents");
			field.setAccessible(true);
			return (int[]) field.get(folder);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
