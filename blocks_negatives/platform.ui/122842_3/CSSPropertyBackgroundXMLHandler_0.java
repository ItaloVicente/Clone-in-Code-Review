/*******************************************************************************
 * Copyright (c) 2008, 2014 Angelo Zerr and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.ui.css.xml.engine;

import org.eclipse.e4.ui.css.core.dom.properties.css2.ICSSPropertyBackgroundHandler;
import org.eclipse.e4.ui.css.core.dom.properties.css2.ICSSPropertyFontHandler;
import org.eclipse.e4.ui.css.core.dom.properties.css2.ICSSPropertyTextHandler;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.core.impl.engine.CSSEngineImpl;
import org.eclipse.e4.ui.css.xml.properties.css2.CSSPropertyBackgroundXMLHandler;
import org.eclipse.e4.ui.css.xml.properties.css2.CSSPropertyFontXMLHandler;
import org.eclipse.e4.ui.css.xml.properties.css2.CSSPropertyTextXMLHandler;

/**
 * {@link CSSEngine} implementation to apply style sheet to XML DOM.
 */
public class CSSXMLEngineImpl extends CSSEngineImpl {

	public CSSXMLEngineImpl() {
		super.registerCSSPropertyHandler(ICSSPropertyBackgroundHandler.class,
				CSSPropertyBackgroundXMLHandler.INSTANCE);
		super.registerCSSPropertyHandler(ICSSPropertyTextHandler.class,
				CSSPropertyTextXMLHandler.INSTANCE);
		super.registerCSSPropertyHandler(ICSSPropertyFontHandler.class,
				CSSPropertyFontXMLHandler.INSTANCE);
	}

	@Override
	public void reapply() {
	}
}
