package org.eclipse.ui.tests.preferences;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.tests.harness.util.UITestCase;


public class DeprecatedFontPreferenceTestCase extends UITestCase {

    public String BAD_FONT_DEFINITION = "BadFont-regular-10";

    public String TEST_FONT_ID = "org.eclipse.jface.tests.preference.testfont";

    public String MISSING_FONT_ID = "org.eclipse.jface.tests.preference.missingfont";

    private IPreferenceStore preferenceStore;

    public DeprecatedFontPreferenceTestCase(String testName) {
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
        FontData bestFont = fontRegistry.filterData(currentTextFonts, Display
                .getCurrent())[0];

        assertEquals(bestFont.getName(), currentTextFonts[0].getName());
        assertEquals(bestFont.getHeight(), currentTextFonts[0].getHeight());
    }


    public void testBadFirstFontDefinition() {

        FontRegistry fontRegistry = JFaceResources.getFontRegistry();
        FontData[] currentTestFonts = PreferenceConverter.getFontDataArray(
                preferenceStore, TEST_FONT_ID);
        FontData bestFont = fontRegistry.filterData(currentTestFonts, Display
                .getCurrent())[0];

        assertEquals(bestFont.getName(), currentTestFonts[1].getName());
        assertEquals(bestFont.getHeight(), currentTestFonts[1].getHeight());
    }


    public void testNoFontDefinition() {

        FontRegistry fontRegistry = JFaceResources.getFontRegistry();
        FontData[] currentTestFonts = PreferenceConverter.getFontDataArray(
                preferenceStore, MISSING_FONT_ID);
        FontData bestFont = fontRegistry.filterData(currentTestFonts, Display
                .getCurrent())[0];

        FontData[] systemFontData = Display.getCurrent().getSystemFont()
                .getFontData();

        assertEquals(bestFont.getName(), systemFontData[0].getName());
        assertEquals(bestFont.getHeight(), systemFontData[0].getHeight());
    }
}
