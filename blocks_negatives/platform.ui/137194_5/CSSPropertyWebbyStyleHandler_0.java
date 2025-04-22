/*******************************************************************************
 * Copyright (c) 2009, 2016 IBM Corporation and others.
 *
 * This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License 2.0 which accompanies this distribution, and is
t
t SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.ui.css.swt.properties.custom;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.properties.AbstractCSSPropertySWTHandler;
import org.eclipse.swt.widgets.Control;
import org.w3c.dom.css.CSSValue;

public class CSSPropertyTabMarginOffsetHandler extends
AbstractCSSPropertySWTHandler {

	@Override
	protected void applyCSSProperty(Control control, String property,
			CSSValue value, String pseudo, CSSEngine engine) throws Exception {
	}

	@Override
	protected String retrieveCSSProperty(Control control, String property,
			String pseudo, CSSEngine engine) throws Exception {
		return null;
	}

}
