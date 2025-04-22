/*******************************************************************************
 * Copyright (c) 2009, 2014 EclipseSource and others. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *
 * Contributors:
 *   EclipseSource - initial API and implementation
 *   Lars Vogel <Lars.Vogel@gmail.com> - Bug 430468
 ******************************************************************************/
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
