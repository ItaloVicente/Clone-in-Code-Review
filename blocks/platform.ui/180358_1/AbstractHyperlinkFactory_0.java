package org.eclipse.jface.widgets;

import java.util.function.Consumer;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.AbstractHyperlink;

public abstract class AbstractHyperlinkFactory<F extends AbstractHyperlinkFactory<?, ?>, C extends AbstractHyperlink>
		extends AbstractCompositeFactory<F, C> {

	protected AbstractHyperlinkFactory(Class<F> factoryClass, WidgetSupplier<C, Composite> controlCreator) {
		super(factoryClass, controlCreator);
	}

	public F href(Object href) {
		addProperty(l -> l.setHref(href));
		return cast(this);
	}

	public F onClick(Consumer<HyperlinkEvent> consumer) {
		addProperty(l -> l.addHyperlinkListener(IHyperlinkListener.linkActivatedAdapter(consumer)));
		return cast(this);
	}
}
