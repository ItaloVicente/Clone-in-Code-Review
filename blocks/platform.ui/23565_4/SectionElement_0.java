package org.eclipse.e4.ui.css.forms.dom;

import org.eclipse.e4.ui.css.core.dom.IElementProvider;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.ui.forms.widgets.Section;
import org.w3c.dom.Element;

public class FormsElementProvider implements IElementProvider {

	public static final IElementProvider INSTANCE = new FormsElementProvider();

	@Override
	public Element getElement(Object element, CSSEngine engine) {
		if (element instanceof Section) {
			return new SectionElement((Section) element, engine);
		}
		
		return null;
	}
}
