package org.eclipse.e4.ui.css.swt.helpers;

import static org.eclipse.e4.ui.css.swt.helpers.CSSSWTColorHelper.getSWTColor;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.css.CSSValue;

@SuppressWarnings("restriction")
public class CSSSWTColorHelperTest extends CSSSWTHelperTestCase {
	private Display display;

	@Override
	protected void setUp() throws Exception {
		display = Display.getDefault();
	}

	public void testGetSWTColor() throws Exception {
		Color result = getSWTColor(colorValue("red"), display);

		assertNotNull(result);
		assertEquals(255, result.getRed());
		assertEquals(0, result.getBlue());
		assertEquals(0, result.getGreen());
	}

	public void testGetSWTColorWhenNotSupportedColorType() throws Exception {
		Color result = getSWTColor(colorValue("123213", CSSValue.CSS_CUSTOM),
				display);

		assertNull(result);
	}

	public void testGetSWTColorWhenInvalidColorValue() throws Exception {
		Color result = getSWTColor(colorValue("asdsad12"), display);

		assertNotNull(result);
		assertEquals(0, result.getRed());
		assertEquals(0, result.getBlue());
		assertEquals(0, result.getGreen());
	}

	public void testGetSWTColorWhenColorFromDefinition() throws Exception {
		registerColorProviderWith("org.eclipse.jdt.debug.ui.InDeadlockColor", new RGB(0, 255, 0));

		Color result = getSWTColor(
				colorValue(addColorDefinitionMarker("org-eclipse-jdt-debug-ui-InDeadlockColor")),
				display);

		assertNotNull(result);
		assertEquals(0, result.getRed());
		assertEquals(0, result.getBlue());
		assertEquals(255, result.getGreen());
	}
}
