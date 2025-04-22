package org.eclipse.ui.tests.preferences;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.ILogger;
import org.eclipse.jface.util.Policy;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.tests.harness.util.UITestCase;


public class FontPreferenceTestCase extends UITestCase {

    public String BAD_FONT_DEFINITION = "BadFont-regular-10";

    public String TEST_FONT_ID = "org.eclipse.jface.tests.preference.testfont";

    public String MISSING_FONT_ID = "org.eclipse.jface.tests.preference.missingfont";

    private IPreferenceStore preferenceStore;

    public FontPreferenceTestCase(String testName) {
        super(testName);
    }

    @Override
	protected void doSetUp() throws Exception {
        super.doSetUp();
        AbstractUIPlugin plugin = (AbstractUIPlugin) Platform
                .getPlugin(PlatformUI.PLUGIN_ID);
        preferenceStore = plugin.getPreferenceStore();

        FontData bogusData = new FontData();
        bogusData.setName("BadData");
        bogusData.setHeight(11);
        FontData[] storedValue = new FontData[2];

        storedValue[0] = bogusData;
        storedValue[1] = (PreferenceConverter.getDefaultFontDataArray(
                preferenceStore, JFaceResources.TEXT_FONT))[0];
        PreferenceConverter
                .setValue(preferenceStore, TEST_FONT_ID, storedValue);
        PreferenceConverter.setDefault(preferenceStore, TEST_FONT_ID,
                storedValue);

    }


    public void testGoodFontDefinition() {

        FontRegistry fontRegistry = JFaceResources.getFontRegistry();
        FontData[] currentTextFonts = PreferenceConverter.getFontDataArray(
                preferenceStore, JFaceResources.TEXT_FONT);
        FontData[] bestFont = fontRegistry.bestDataArray(currentTextFonts,
                Display.getCurrent());

        assertEquals(bestFont[0].getName(), currentTextFonts[0].getName());
        assertEquals(bestFont[0].getHeight(), currentTextFonts[0].getHeight());
    }


    public void testBadFirstFontDefinition() {

        FontRegistry fontRegistry = JFaceResources.getFontRegistry();
        FontData[] currentTestFonts = PreferenceConverter.getFontDataArray(
                preferenceStore, TEST_FONT_ID);
        FontData[] bestFont = fontRegistry.filterData(currentTestFonts,
                Display.getCurrent());

        assertEquals(bestFont[0].getName(), currentTestFonts[1].getName());
        assertEquals(bestFont[0].getHeight(), currentTestFonts[1].getHeight());
    }


    public void testNoFontDefinition() {

        FontRegistry fontRegistry = JFaceResources.getFontRegistry();
        FontData[] currentTestFonts = PreferenceConverter.getFontDataArray(
                preferenceStore, MISSING_FONT_ID);
        FontData[] bestFont = fontRegistry.filterData(currentTestFonts,
                Display.getCurrent());

        FontData[] systemFontData = Display.getCurrent().getSystemFont()
                .getFontData();

        assertEquals(bestFont[0].getName(), systemFontData[0].getName());
        assertEquals(bestFont[0].getHeight(), systemFontData[0].getHeight());
    }

    public void testNonUIThreadFontAccess() {
		final FontRegistry fontRegistry = new FontRegistry("org.eclipse.jface.resource.jfacefonts"); //$NON-NLS-1$
		Font defaultFont = fontRegistry.defaultFont();
		defaultFont.toString(); // avoids compiler warning

		final boolean[] errorLogged = new boolean[] { false };
		ILogger logger = Policy.getLog();
		Policy.setLog(new ILogger() {
			@Override
			public void log(IStatus status) {
				if (status != null && status.getSeverity() == IStatus.ERROR && status.getPlugin().equals(Policy.JFACE)) {
					errorLogged[0] = true;
				}
			}} );


    	Job job = new Job("Non-UI thread FontRegistry Access Test") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				boolean created = checkFont(fontRegistry);
				assertFalse(created);
				return Status.OK_STATUS;
			}
    	};
		job.schedule();
		try {
			job.join();
			assertTrue(errorLogged[0]);
		} catch (InterruptedException e) {
			fail("Worker thread was interrupted in the FontRegistry access test");
		} finally {
			Policy.setLog(logger);
		}

		boolean created = checkFont(fontRegistry);
		assertTrue(created);
    }

	public boolean checkFont(final FontRegistry fontRegistry) {
		FontData[] data = fontRegistry.defaultFont().getFontData();
		int defaultHeight = data[0].getHeight();
		int testHeight = defaultHeight + 20;
		data[0].setHeight(testHeight);
		fontRegistry.put("testFont", data);

		Font testFont = fontRegistry.get("testFont"); // getItalic("testFont");

		FontData[] receivedData = testFont.getFontData();
		int receivedHeight = receivedData[0].getHeight();
		return (receivedHeight != defaultHeight);
	}

}
