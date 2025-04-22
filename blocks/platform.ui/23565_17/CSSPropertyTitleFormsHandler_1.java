package org.eclipse.ui.forms.css.dom;

import org.eclipse.e4.ui.css.core.dom.CSSStylableElement;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.CompositeElement;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.forms.widgets.Section;

public class SectionElement extends CompositeElement {


	private Color titleBarBackground;
	private Color titleBarBorderColor;
	private Color titleBarGradientBackground;

	public SectionElement(Section section, CSSEngine engine) {
		super(section, engine);
		titleBarBackground = section.getTitleBarBackground();
		titleBarBorderColor = section.getTitleBarBorderColor();
		titleBarGradientBackground = section.getTitleBarGradientBackground();
	}

	public void reset() {
		super.reset();
		Section section = (Section) getWidget();
		section.setTitleBarBackground(titleBarBackground);
		section.setTitleBarBorderColor(titleBarBorderColor);
		section.setTitleBarGradientBackground(titleBarGradientBackground);
	}

}
