package org.eclipse.e4.ui.tests.css.swt;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.internal.css.swt.CSSActivator;
import org.eclipse.e4.ui.internal.css.swt.definition.IColorAndFontProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.internal.themes.ColorDefinition;

@SuppressWarnings("restriction")
public class ColorDefinitionTest extends CSSSWTTestCase {
	private Display display;

	@Override
	protected void setUp() throws Exception {
		display = Display.getDefault();
	}

	public void testColorDefinition() throws Exception {
		CSSEngine engine = createEngine("ColorDefinition#ACTIVE_HYPERLINK_COLOR{color: green}", display);
		ColorDefinition definition = colorDefinition("ACTIVE_HYPERLINK_COLOR", "name", "categoryId", "description");

		assertEquals(new RGB(0, 0, 0), definition.getValue());
		assertFalse(definition.isOverridden());

		engine.applyStyles(definition, true);

		assertEquals(new RGB(0, 128, 0), definition.getValue());
		assertEquals("name", definition.getName());
		assertEquals("categoryId", definition.getCategoryId());
		assertTrue(definition.getDescription().startsWith("description"));
		assertTrue(definition.isOverridden());
	}

	public void testColorDefinitionWhenNameCategoryIdAndDescriptionOverridden()
			throws Exception {
		CSSEngine engine = createEngine("ColorDefinition#ACTIVE_HYPERLINK_COLOR{color: green;" +
				"label:'nameOverridden'; category:'#categoryIdOverridden'; description: 'descriptionOverridden'}", display);
		ColorDefinition definition = colorDefinition("ACTIVE_HYPERLINK_COLOR","name", "categoryId", "description");

		assertEquals(new RGB(0, 0, 0), definition.getValue());
		assertFalse(definition.isOverridden());

		engine.applyStyles(definition, true);

		assertEquals(new RGB(0, 128, 0), definition.getValue());
		assertEquals("nameOverridden", definition.getName());
		assertEquals("categoryIdOverridden", definition.getCategoryId());
		assertTrue(definition.getDescription().startsWith("descriptionOverridden"));
		assertTrue(definition.isOverridden());
	}

	public void testColorDefinitionWhenDefinitionStylesheetNotFound() throws Exception{
		CSSEngine engine = createEngine("ColorDefinition#ACTIVE_HYPERLINK_COLOR{color: green}", display);
		ColorDefinition definition = colorDefinition("color definition uniqueId without matching stylesheet",
				"name", "categoryId", "description");

		assertEquals(new RGB(0, 0, 0), definition.getValue());
		assertFalse(definition.isOverridden());

		engine.applyStyles(definition, true);

		assertEquals(new RGB(0, 0, 0), definition.getValue());
		assertFalse(definition.isOverridden());
	}

	public void testWidgetWithColorDefinitionAsBackgroundColor() throws Exception {
		registerColorProviderWith("ACTIVE_HYPERLINK_COLOR", new RGB(255, 0, 0));

		CSSEngine engine = createEngine("Label {background-color: '#ACTIVE_HYPERLINK_COLOR'}", display);

		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		Label label = new Label(shell, SWT.NONE);
		label.setText("Some label text");

		engine.applyStyles(label, true);

		assertEquals(new RGB(255, 0, 0), label.getBackground().getRGB());

		shell.dispose();
	}

	private ColorDefinition colorDefinition(String uniqueId, String name,
			String categoryId, String description) {
		return new ColorDefinition(name, uniqueId, "defaultsTo", "black",
				categoryId, true, description, "pluginId");
	}

	private void registerColorProviderWith(final String symbolicName, final RGB rgb) throws Exception {
		new CSSActivator() {
			@Override
			public IColorAndFontProvider getColorAndFontProvider() {
				IColorAndFontProvider provider = mock(IColorAndFontProvider.class);
				doReturn(rgb).when(provider).getColor(symbolicName);
				return provider;
			};
		}.start(null);
	}
}
