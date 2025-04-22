package org.eclipse.ui.tests.navigator;

import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.ui.internal.navigator.extensions.NavigatorContentExtension;
import org.eclipse.ui.tests.harness.util.DisplayHelper;
import org.eclipse.ui.tests.navigator.extension.TestEmptyContentProvider;
import org.eclipse.ui.tests.navigator.extension.TestLabelProvider;
import org.eclipse.ui.tests.navigator.extension.TestLabelProviderBlank;
import org.eclipse.ui.tests.navigator.extension.TestLabelProviderCyan;
import org.eclipse.ui.tests.navigator.extension.TestLabelProviderStyledGreen;
import org.eclipse.ui.tests.navigator.extension.TrackingLabelProvider;

public class LabelProviderTest extends NavigatorTestBase {

	private static final boolean PRINT_DEBUG_INFO = false;
	private static final boolean SLEEP_LONG = false;

	public LabelProviderTest() {
		_navigatorInstanceId = "org.eclipse.ui.tests.navigator.OverrideTestView";
	}

	private static final int NONE = 0;
	private static final int OVERRIDDEN = 1;
	private static final int OVERRIDING = 2;
	private static final int BOTH = 3;

	private static final boolean BLANK = true;
	private static final boolean NULL = false;

	private static final String PLAIN = "Plain";

	private void setBlank(String cpToGet, boolean blank) {
		NavigatorContentExtension ext = (NavigatorContentExtension) _contentService
				.getContentExtensionById(cpToGet);
		TestLabelProvider tp = (TestLabelProvider) ext.getLabelProvider();
		if (blank)
			tp._blank = true;
		else
			tp._null = true;

	}

	public void blankLabelProviderOverride(int nce, boolean blank, String suffix) throws Exception {

		String overriddenCp = TEST_CONTENT_OVERRIDDEN1 + suffix; // Red
		String overrideCp = TEST_CONTENT_OVERRIDE1 + suffix; // Green

		String checkColor = "Green";

		switch (nce) {
		case NONE:
			break;
		case OVERRIDDEN:
			setBlank(overriddenCp, blank);
			break;
		case OVERRIDING:
			checkColor = "Red";
			setBlank(overrideCp, blank);
			break;
		case BOTH:
			setBlank(overriddenCp, blank);
			setBlank(overrideCp, blank);
			break;
		}

		_contentService.bindExtensions(new String[] { overriddenCp, overrideCp }, false);
		_contentService.getActivationService().activateExtensions(
				new String[] { overrideCp, overriddenCp }, true);

		refreshViewer();

		TreeItem[] rootItems = _viewer.getTree().getItems();

		ILabelProvider lp = _contentService.createCommonLabelProvider();
		String lpText = lp.getText(rootItems[0].getData());

		if (nce == BOTH) {
			if (!rootItems[0].getText().equals(""))
				fail("Wrong text: " + rootItems[0].getText());

			if (blank) {
				if (!lpText.equals(""))
					fail("Wrong text from ILabelProvider: " + lpText);
			} else {
				if (lpText != null)
					fail("Wrong text from ILabelProvider: " + lpText);
			}
		} else {
			if (!rootItems[0].getText().startsWith(checkColor))
				fail("Wrong text: " + rootItems[0].getText());
			if (!lpText.startsWith(checkColor))
				fail("Wrong text from ILabelProvider: " + lpText);
		}
	}

	public void testBlankLabelProviderOverrideNone() throws Exception {
		blankLabelProviderOverride(NONE, BLANK, "");
	}

	public void testNullLabelProviderOverrideNone() throws Exception {
		blankLabelProviderOverride(NONE, NULL, "");
	}

	public void testPlainBlankLabelProviderOverrideNone() throws Exception {
		blankLabelProviderOverride(NONE, BLANK, PLAIN);
	}

	public void testPlainNullLabelProviderOverrideNone() throws Exception {
		blankLabelProviderOverride(NONE, NULL, PLAIN);
	}

	public void testBlankLabelProviderOverride1() throws Exception {
		blankLabelProviderOverride(OVERRIDDEN, BLANK, "");
	}

	public void testNullLabelProviderOverride1() throws Exception {
		blankLabelProviderOverride(OVERRIDDEN, NULL, "");
	}

	public void testPlainBlankLabelProviderOverride1() throws Exception {
		blankLabelProviderOverride(OVERRIDDEN, BLANK, PLAIN);
	}

	public void testPlainNullLabelProviderOverride1() throws Exception {
		blankLabelProviderOverride(OVERRIDDEN, NULL, PLAIN);
	}

	public void testBlankLabelProviderOverride2() throws Exception {
		blankLabelProviderOverride(OVERRIDING, BLANK, "");
	}

	public void testNullLabelProviderOverride2() throws Exception {
		blankLabelProviderOverride(OVERRIDING, NULL, "");
	}

	public void testPlainBlankLabelProviderOverride2() throws Exception {
		blankLabelProviderOverride(OVERRIDING, BLANK, PLAIN);
	}

	public void testPlainNullLabelProviderOverride2() throws Exception {
		blankLabelProviderOverride(OVERRIDING, NULL, PLAIN);
	}

	public void testBlankLabelProviderBoth() throws Exception {
		blankLabelProviderOverride(BOTH, BLANK, "");
	}

	public void testNullLabelProviderBoth() throws Exception {
		blankLabelProviderOverride(BOTH, NULL, "");
	}

	public void testPlainBlankLabelProviderBoth() throws Exception {
		blankLabelProviderOverride(BOTH, BLANK, PLAIN);
	}

	public void testPlainNullLabelProviderBoth() throws Exception {
		blankLabelProviderOverride(BOTH, NULL, PLAIN);
	}

	public void testSimpleResFirst() throws Exception {

		_contentService.bindExtensions(new String[] { TEST_CONTENT_OVERRIDDEN1,
				TEST_CONTENT_OVERRIDE1 }, false);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_OVERRIDE1, TEST_CONTENT_OVERRIDDEN1 }, true);

		refreshViewer();

		TreeItem[] rootItems = _viewer.getTree().getItems();
		checkItems(rootItems, TestLabelProviderStyledGreen.instance);
	}

	public void testOverrideChain() throws Exception {
		final String[] EXTENSIONS = new String[] { TEST_CONTENT_TRACKING_LABEL + ".A",
				TEST_CONTENT_TRACKING_LABEL + ".B", TEST_CONTENT_TRACKING_LABEL + ".C",
				TEST_CONTENT_TRACKING_LABEL + ".D", TEST_CONTENT_TRACKING_LABEL + ".E",
				TEST_CONTENT_TRACKING_LABEL + ".F", TEST_CONTENT_TRACKING_LABEL + ".G" };
		_contentService.bindExtensions(EXTENSIONS, true);
		_contentService.getActivationService().activateExtensions(EXTENSIONS, true);

		refreshViewer();
		_viewer.getTree().getItems();

		TrackingLabelProvider.resetQueries();

		DisplayHelper.sleep(200);

		refreshViewer();

		DisplayHelper.sleep(200);

		if (PRINT_DEBUG_INFO)
			System.out.println("Map: " + TrackingLabelProvider.styledTextQueries);
		String queries = (String) TrackingLabelProvider.styledTextQueries.get(_project);
		assertTrue("F has the highest priority", queries.startsWith("F"));
		assertBefore(queries, 'C', 'A');
		assertBefore(queries, 'B', 'A');
		assertBefore(queries, 'D', 'B');
		assertBefore(queries, 'E', 'D');
		assertBefore(queries, 'F', 'C');
		assertBefore(queries, 'G', 'C');
	}

	private void assertBefore(String queries, char firstChar, char secondChar) {
		boolean first = false;
		final int LEN = queries.length();
		for (int i=0; i<LEN; i++) {
			char cur = queries.charAt(i);
			if (cur == firstChar) {
				first = true;
			}
			if (cur == secondChar) {
				assertTrue("Failed to find " + firstChar + " before " + secondChar + " in " + queries, first);
				return;
			}
		}
	}

	public void testSimpleResLast() throws Exception {
		_contentService.bindExtensions(new String[] { TEST_CONTENT_OVERRIDDEN2,
				TEST_CONTENT_OVERRIDE2 }, false);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_OVERRIDDEN2, TEST_CONTENT_OVERRIDE2 }, true);

		refreshViewer();

		if (SLEEP_LONG)
			DisplayHelper.sleep(10000000);

		TreeItem[] rootItems = _viewer.getTree().getItems();
		checkItems(rootItems, TestLabelProviderCyan.instance);
	}

	public void testOverrideAdd() throws Exception {
		_contentService.bindExtensions(new String[] { TEST_CONTENT_OVERRIDDEN2,
				TEST_CONTENT_OVERRIDE2 }, false);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_OVERRIDDEN2, TEST_CONTENT_OVERRIDE2 }, true);

		refreshViewer();

		_viewer.expandToLevel(_project, 3);
		IFile f = _project.getFile("newfile");
		_viewer.add(_project, new Object[] { f });

		if (SLEEP_LONG)
			DisplayHelper.sleep(10000000);

		TreeItem[] rootItems = _viewer.getTree().getItems();
		checkItems(rootItems, TestLabelProviderCyan.instance);
	}

	public void testChangeActivation() throws Exception {
		TreeItem[] rootItems = _viewer.getTree().getItems();
		checkItems(rootItems, TestLabelProviderStyledGreen.instance);

		_contentService.bindExtensions(new String[] { TEST_CONTENT_OVERRIDDEN2,
				TEST_CONTENT_OVERRIDE2 }, false);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_OVERRIDDEN2, TEST_CONTENT_OVERRIDE2 }, true);

		_viewer.expandAll();
		
		
		for (int i = 0; i < 1200; i++) {
			rootItems = _viewer.getTree().getItems();
			if (rootItems[0].getBackground(0).equals(TestLabelProviderCyan.instance.backgroundColor))
				break;
			DisplayHelper.sleep(50);
		}


		if (SLEEP_LONG)
			DisplayHelper.sleep(10000000);
		
		DisplayHelper.sleep(500);
		
		rootItems = _viewer.getTree().getItems();
		checkItems(rootItems, TestLabelProviderCyan.instance);
	}

	public void testUsingOverriddenLabelProvider() throws Exception {

		_contentService.bindExtensions(new String[] { TEST_CONTENT_OVERRIDDEN2,
				TEST_CONTENT_OVERRIDE2_BLANK }, true);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_OVERRIDDEN2, TEST_CONTENT_OVERRIDE2_BLANK }, true);

		refreshViewer();

		TreeItem[] rootItems = _viewer.getTree().getItems();

		if (SLEEP_LONG)
			DisplayHelper.sleep(10000000);

		if (!rootItems[0].getText().startsWith("Blue"))
			fail("Wrong text: " + rootItems[0].getText());

		checkItems(rootItems, TestLabelProviderBlank.instance, ALL, !TEXT);
	}

	public void testMultiNceSameObject() throws Exception {
		
		_contentService.bindExtensions(new String[] { TEST_CONTENT_OVERRIDDEN1, COMMON_NAVIGATOR_RESOURCE_EXT }, true);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_OVERRIDDEN1, COMMON_NAVIGATOR_RESOURCE_EXT }, true);

		refreshViewer();

		TreeItem[] rootItems = _viewer.getTree().getItems();


		if (!rootItems[0].getText().equals("p1"))
			fail("Wrong text: " + rootItems[0].getText());
	}

	public void testLabelProviderPriority() throws Exception {
		
		_contentService.bindExtensions(new String[] { TEST_CONTENT_EMPTY, COMMON_NAVIGATOR_RESOURCE_EXT }, true);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_EMPTY, COMMON_NAVIGATOR_RESOURCE_EXT }, true);

		refreshViewer();

		TreeItem[] rootItems = _viewer.getTree().getItems();


		assertEquals(TestLabelProviderCyan.instance.image, rootItems[0].getImage(0));
	}

	public void testLabelProviderThrow() throws Exception {
		
		_contentService.bindExtensions(new String[] { TEST_CONTENT_EMPTY, COMMON_NAVIGATOR_RESOURCE_EXT }, true);
		_contentService.getActivationService().activateExtensions(
				new String[] { TEST_CONTENT_EMPTY, COMMON_NAVIGATOR_RESOURCE_EXT }, true);

		TestLabelProvider._throw = true;
		TestEmptyContentProvider._throw = true;
		
		refreshViewer();
	}

}
