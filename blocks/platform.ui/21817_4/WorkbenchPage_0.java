package org.eclipse.ui.internal;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class E4PartWrapper extends ViewPart {

	public static final String E4_WRAPPER_KEY = "e4Wrapper"; //$NON-NLS-1$
	MPart wrappedPart;

	public E4PartWrapper(MPart part) {
		wrappedPart = part;
		setPartName(part.getLabel());
	}

	@Override
	public void createPartControl(Composite parent) {
	}

	@Override
	public void setFocus() {
		if (wrappedPart.getObject() != null && wrappedPart.getContext() != null)
			ContextInjectionFactory.invoke(wrappedPart.getObject(), Focus.class,
					wrappedPart.getContext());
	}
}

