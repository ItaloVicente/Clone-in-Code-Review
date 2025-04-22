package org.eclipse.e4.ui.css.swt.properties.converters;

import static org.mockito.Mockito.mock;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.helpers.CSSSWTHelperTestCase;
import org.eclipse.e4.ui.css.swt.resources.ColorByDefinition;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.css.CSSPrimitiveValue;

@SuppressWarnings("restriction")
public class CSSValueSWTColorConverterImplTest extends CSSSWTHelperTestCase {
	private Display display;
	
	@Override
	public void setUp() throws Exception {
		display = Display.getDefault();
	}
		
	public void testConvert() throws Exception {
		CSSPrimitiveValue colorValue = colorValue("#00FF00");
		
		Object result = CSSValueSWTColorConverterImpl.INSTANCE.convert(colorValue, mock(CSSEngine.class), display);
	
		assertEquals(Color.class, result.getClass());
		assertEquals(new RGB(0, 255, 0), ((Color) result).getRGB());
		
		((Color) result).dispose();
	}
	
	public void testConvertWhenDefinitionAsColorValue() throws Exception {
		registerColorProviderWith("org.eclipse.ui.editors.findScope", new RGB(0, 0, 255));
		CSSPrimitiveValue colorValue = colorValue(addColorDefinitionMarker("org-eclipse-ui-editors-findScope")); 
		
		Object result = CSSValueSWTColorConverterImpl.INSTANCE.convert(colorValue, mock(CSSEngine.class), display);
		
		assertEquals(ColorByDefinition.class, result.getClass());
		assertEquals(new RGB(0, 0, 255), ((ColorByDefinition) result).getResource().getRGB());
		
		((ColorByDefinition) result).getResource().dispose();
	}
}
