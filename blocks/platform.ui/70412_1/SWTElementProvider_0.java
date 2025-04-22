package org.eclipse.e4.ui.css.swt.dom;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;

public class LinkElement extends ControlElement {

	public LinkElement(Control control, CSSEngine engine) {
		super(control, engine);
	}

	public Link getLink() {
		return (Link) getControl();
	}

}
