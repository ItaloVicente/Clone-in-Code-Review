package org.eclipse.e4.ui.css.swt.dom;

import org.eclipse.e4.ui.css.core.dom.CSSStylableElement;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.swt.widgets.Link;

public class LinkElement extends ControlElement {

	public LinkElement(Link link, CSSEngine engine) {
		super(link, engine);
	}

	@Override
	public boolean isPseudoInstanceOf(String s) {
		if ("link".equals(s)) {
			return true;
		}
		return super.isPseudoInstanceOf(s);
	}

	@Override
	protected void computeStaticPseudoInstances() {
		super.computeStaticPseudoInstances();
		super.addStaticPseudoInstance("link");
	}
}
