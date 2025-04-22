package org.eclipse.e4.ui.tests.css.core.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.tests.css.core.util.ParserTestUtil;
import org.junit.Test;
import org.w3c.dom.css.CSSValue;
import org.w3c.dom.css.RGBColor;


public class RGBColorImplTest {

	@Test
	public void testGetCssText() throws Exception {
		CSSEngine engine = ParserTestUtil.createEngine();
		CSSValue value = engine.parsePropertyValue("#FF8000");
		assertTrue( value instanceof RGBColor );
		assertEquals( "rgb(255, 128, 0)", value.getCssText() );

		value = engine.parsePropertyValue("rgb( 300, -10, 42 )");
		assertTrue( value instanceof RGBColor );

	}
}
