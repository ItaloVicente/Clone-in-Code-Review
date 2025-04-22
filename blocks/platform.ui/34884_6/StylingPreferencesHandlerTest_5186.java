package org.eclipse.ui.tests.themes;

import java.util.Iterator;

import org.eclipse.jface.resource.ColorDescriptor;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.themes.ITheme;
import org.eclipse.ui.themes.IThemeManager;

public class JFaceThemeTest extends ThemeTest {

    public JFaceThemeTest(String testName) {
        super(testName);
    }

    private void setAndTest(String themeId, IPropertyChangeListener listener) {
        JFaceResources.getFontRegistry().addListener(listener);
        JFaceResources.getColorRegistry().addListener(listener);
        fManager.setCurrentTheme(themeId);
        ITheme theme = fManager.getTheme(themeId);
        assertEquals(theme, fManager.getCurrentTheme());
        {
            FontRegistry jfaceFonts = JFaceResources.getFontRegistry();
            FontRegistry themeFonts = theme.getFontRegistry();
            assertTrue(jfaceFonts.getKeySet().containsAll(
                    themeFonts.getKeySet()));
            for (Iterator i = themeFonts.getKeySet().iterator(); i.hasNext();) {
                String key = (String) i.next();
                assertArrayEquals(themeFonts.getFontData(key), jfaceFonts
                        .getFontData(key));
            }
        }
        {
            ColorRegistry jfaceColors = JFaceResources.getColorRegistry();
            ColorRegistry themeColors = theme.getColorRegistry();
            assertTrue(jfaceColors.getKeySet().containsAll(
                    themeColors.getKeySet()));
            for (Iterator i = themeColors.getKeySet().iterator(); i.hasNext();) {
                String key = (String) i.next();
                assertEquals(themeColors.getRGB(key), jfaceColors.getRGB(key));
            }
        }
        JFaceResources.getFontRegistry().removeListener(listener);
        JFaceResources.getColorRegistry().removeListener(listener);
    }

    public void testPushdown() {
        ThemePropertyListener listener = new ThemePropertyListener();
        setAndTest(THEME1, listener);
        assertEquals(10, listener.getEvents().size());
        listener.getEvents().clear();
        setAndTest(IThemeManager.DEFAULT_THEME, listener);
        assertEquals(10, listener.getEvents().size());
    }
    
	public void testDefaultColorDescriptor() {
		ColorDescriptor desc = getDefaultTheme().getColorRegistry()
				.getColorDescriptor("somegarbage");
		assertNotNull(desc);
		Color color = desc.createColor(getWorkbench().getDisplay());
		assertNotNull(color);
		color.dispose();

		desc = getDefaultTheme().getColorRegistry().getColorDescriptor(
				"somegarbage", null);
		assertNull(desc);

		desc = getDefaultTheme().getColorRegistry().getColorDescriptor(
				"somegarbage", ColorDescriptor.createFrom(new RGB(0, 0, 0)));
		assertNotNull(desc);
		color = desc.createColor(getWorkbench().getDisplay());
		assertNotNull(color);
		color.dispose();
	}
}
