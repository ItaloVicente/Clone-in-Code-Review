package org.eclipse.jface.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.eclipse.swt.widgets.Widget;

public abstract class AbstractWidgetFactory<F extends AbstractWidgetFactory<?, ?, ?>, W extends Widget, P extends Widget> {
	private Class<F> factoryClass;

	private Function<P, W> widgetCreator;

	private List<Property<W>> properties = new ArrayList<>();

	protected AbstractWidgetFactory(Class<F> factoryClass, Function<P, W> widgetCreator) {
		this.factoryClass = factoryClass;
		this.widgetCreator = widgetCreator;
	}

	protected final F cast(AbstractWidgetFactory<F, W, P> factory) {
		return factoryClass.cast(factory);
	}

	public final W create(P parent) {
		W widget = widgetCreator.apply(parent);
		properties.forEach(p -> p.apply(widget));
		return widget;
	}

	protected final void addProperty(Property<W> property) {
		this.properties.add(property);
	}
}
