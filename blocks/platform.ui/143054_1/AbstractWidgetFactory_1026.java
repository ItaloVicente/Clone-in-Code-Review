package org.eclipse.jface.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Widget;

public abstract class AbstractWidgetFactory<F extends AbstractWidgetFactory<?, ?, ?>, W extends Widget, P extends Widget> {
	private Class<F> factoryClass;

	private WidgetSupplier<W, P> widgetCreator;

	private List<Property<W>> properties = new ArrayList<>();

	protected AbstractWidgetFactory(Class<F> factoryClass, WidgetSupplier<W, P> widgetCreator) {
		this.factoryClass = factoryClass;
		this.widgetCreator = widgetCreator;
	}

	protected final F cast(AbstractWidgetFactory<F, W, P> factory) {
		return factoryClass.cast(factory);
	}

	public final W create(P parent) {
		W widget = widgetCreator.create(parent);
		properties.forEach(p -> p.apply(widget));
		return widget;
	}

	protected final void addProperty(Property<W> property) {
		this.properties.add(property);
	}
}
