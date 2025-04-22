package org.eclipse.ui.forms.css.dom;

import org.eclipse.e4.ui.css.core.dom.IElementProvider;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.ui.forms.widgets.Section;
import org.w3c.dom.Element;

public class FormsElementProvider implements IElementProvider {

	public static final IElementProvider INSTANCE = new FormsElementProvider();

	public Element getElement(Object element, CSSEngine engine) {
		if (element instanceof Section) {
			return new SectionElement((Section) element, engine);
		}

		return null;
	}
}
