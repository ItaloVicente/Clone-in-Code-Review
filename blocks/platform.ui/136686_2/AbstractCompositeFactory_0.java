package org.eclipse.jface.widgets;

import java.util.function.Function;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

public abstract class AbstractCompositeFactory<F extends AbstractCompositeFactory<?, ?>, C extends Composite>
		extends ControlFactory<F, C> {

	protected AbstractCompositeFactory(Class<F> factoryClass, Function<Composite, C> controlCreator) {
		super(factoryClass, controlCreator);
	}

	private Layout layout;

	public F layout(Layout layout) {
		this.layout = layout;
		return factoryClass.cast(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void applyProperties(Composite control) {
		super.applyProperties((C) control);
		if (layout != null) {
			control.setLayout(layout);
		}
	}
}
