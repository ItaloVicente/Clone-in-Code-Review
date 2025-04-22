package org.eclipse.jface.widgets;

import java.util.function.Supplier;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class AbstractControlFactory<F extends AbstractControlFactory<?, ?>, C extends Control>
		extends AbstractWidgetFactory<F, C, Composite> {

	protected AbstractControlFactory(Class<F> factoryClass, WidgetSupplier<C, Composite> controlCreator) {
		super(factoryClass, controlCreator);
	}

	public F tooltip(String tooltipText) {
		addProperty(c -> c.setToolTipText(tooltipText));
		return cast(this);
	}

	public F enabled(boolean enabled) {
		addProperty(c -> c.setEnabled(enabled));
		return cast(this);
	}

	public F layoutData(Supplier<Object> layoutDataSupplier) {
		addProperty(c -> c.setLayoutData(layoutDataSupplier.get()));
		return cast(this);
	}

	public F layoutData(Object layoutData) {
		addProperty(c -> c.setLayoutData(layoutData));
		return cast(this);
	}

	public F font(Font font) {
		addProperty(c -> c.setFont(font));
		return cast(this);
	}
}
