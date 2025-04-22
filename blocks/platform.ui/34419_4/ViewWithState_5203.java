
package org.eclipse.ui.tests.session;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.IThemeManager;

public class ThemeStateTest extends TestCase {
	public static TestSuite suite() {
		TestSuite ts = new TestSuite("org.eclipse.ui.tests.session.ThemeStateTest");
		ts.addTest(new ThemeStateTest("testBadPreference"));
		return ts;
	}

	public ThemeStateTest(final String name) {
		super(name);
	}

	public void testBadPreference() {
		String themeId = PlatformUI.getWorkbench().getThemeManager()
				.getCurrentTheme().getId();
		assertNotNull(themeId);
		assertEquals(IThemeManager.DEFAULT_THEME, themeId);
	}
}
