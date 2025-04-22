package org.eclipse.jface.widgets;

import org.eclipse.swt.widgets.Composite;

public class CompositeFactory extends AbstractCompositeFactory<CompositeFactory, Composite> {

	private CompositeFactory(int style) {
		super(CompositeFactory.class, (Composite parent) -> new Composite(parent, style));
	}

	public static CompositeFactory newComposite(int style) {
		return new CompositeFactory(style);
	}
}
