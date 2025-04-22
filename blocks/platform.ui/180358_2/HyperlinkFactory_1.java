package org.eclipse.jface.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Hyperlink;

public class HyperlinkFactory extends AbstractHyperlinkFactory<HyperlinkFactory, Hyperlink> {

	private HyperlinkFactory(int style) {
		super(HyperlinkFactory.class, (Composite parent) -> new Hyperlink(parent, style));
	}

	public static HyperlinkFactory newHyperlink(int style) {
		return new HyperlinkFactory(style);
	}


	public HyperlinkFactory text(String text) {
		addProperty(l -> l.setText(text));
		return this;
	}

	public HyperlinkFactory underline(boolean underlined) {
		addProperty(l -> l.setUnderlined(underlined));
		return this;
	}
}
