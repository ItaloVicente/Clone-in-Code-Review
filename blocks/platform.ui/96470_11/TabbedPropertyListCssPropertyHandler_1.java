package org.eclipse.ui.internal.views.properties.tabbed.css;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.CompositeElement;
import org.eclipse.ui.internal.views.properties.tabbed.view.TabbedPropertyList;

public class TabbedPropertyListAdapter extends CompositeElement {

	public TabbedPropertyListAdapter(TabbedPropertyList composite, CSSEngine engine) {
		super(composite, engine);
	}

	@Override
	public void reset() {
		super.reset();
		getTabbedPropertyTitle().initColours();
	}

	private TabbedPropertyList getTabbedPropertyTitle() {
		return (TabbedPropertyList) getNativeWidget();
	}
}
