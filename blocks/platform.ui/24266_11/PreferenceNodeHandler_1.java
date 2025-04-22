package org.eclipse.e4.ui.css.swt.dom.preference;

import org.eclipse.e4.ui.css.core.dom.IElementProvider;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.internal.css.swt.preference.PreferenceNode;
import org.w3c.dom.Element;

public class PreferenceNodeProvider implements IElementProvider {
	@Override
	public Element getElement(Object element, CSSEngine engine) {
		if (element instanceof PreferenceNode) {
			return new PreferenceNodeElement((PreferenceNode) element, engine);
		}
		return null;
	}

}
