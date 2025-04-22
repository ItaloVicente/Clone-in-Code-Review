package org.eclipse.jface.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

public abstract class AbstractCompositeFactory<F extends AbstractCompositeFactory<?, ?>, C extends Composite>
		extends AbstractControlFactory<F, C> {

	protected AbstractCompositeFactory(Class<F> factoryClass, WidgetSupplier<C, Composite> controlCreator) {
		super(factoryClass, controlCreator);
	}

	public F layout(Layout layout) {
		addProperty(control -> control.setLayout(layout));
		return cast(this);
	}
}
