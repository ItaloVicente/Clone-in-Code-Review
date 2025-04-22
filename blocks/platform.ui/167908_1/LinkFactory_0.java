package org.eclipse.jface.widgets;

import java.util.function.Consumer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;

public final class LinkFactory extends AbstractControlFactory<LinkFactory, Link> {

	private LinkFactory(int style) {
		super(LinkFactory.class, (Composite parent) -> new Link(parent, style));
	}

	public static LinkFactory newLink(int style) {
		return new LinkFactory(style);
	}

	public LinkFactory text(String text) {
		addProperty(l -> l.setText(text));
		return this;
	}

	public LinkFactory onSelect(Consumer<SelectionEvent> consumer) {
		addProperty(c -> c.addSelectionListener(SelectionListener.widgetSelectedAdapter(consumer)));
		return this;
	}

	public LinkFactory data(Object data) {
		addProperty(l -> l.setData(data));
		return this;
	}
}
