package org.eclipse.e4.ui.css.forms.dom;

import org.eclipse.e4.ui.css.core.dom.CSSStylableElement;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.CompositeElement;
import org.eclipse.ui.forms.widgets.Section;

public class SectionElement extends CompositeElement {


	public SectionElement(Section section, CSSEngine engine) {
		super(section, engine);
		
	}
	
	@Override
	public void reset() {
		super.reset();
		Section widget = (Section) getWidget();
		widget.setTitleBarBackground(null);
	}

}
