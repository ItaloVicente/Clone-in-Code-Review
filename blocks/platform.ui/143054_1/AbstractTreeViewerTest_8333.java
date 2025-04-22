package org.eclipse.jface.tests.resources;

import static org.junit.Assert.assertArrayEquals;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.tests.harness.util.TestRunLogUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

public class FontRegistryTest {
	@Rule
	public TestWatcher LOG_TESTRUN = TestRunLogUtil.LOG_TESTRUN;

	@Test
	public void testBug544026() {
		FontData[] fontData = JFaceResources.getDefaultFont().getFontData();
		fontData[0].setHeight(fontData[0].getHeight() + 1);

		Font temp = new Font(Display.getCurrent(), fontData);
		fontData = temp.getFontData();
		temp.dispose();

		JFaceResources.getFontRegistry().put(JFaceResources.DEFAULT_FONT, fontData);

		assertArrayEquals(fontData, JFaceResources.getDefaultFont().getFontData());
	}

}
