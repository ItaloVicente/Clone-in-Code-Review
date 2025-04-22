
package org.eclipse.ui.internal.contexts;

import java.util.Iterator;

import org.eclipse.core.expressions.Expression;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.internal.services.INestable;

public class NestableContextService extends SlaveContextService implements
		INestable {
	private boolean fActive;

	public NestableContextService(IContextService parentService,
			Expression defaultExpression) {
		super(parentService, defaultExpression);
		fActive = false;
	}

	@Override
	protected IContextActivation doActivateContext(IContextActivation activation) {
		if (fActive) {
			return super.doActivateContext(activation);
		}
		fLocalActivations.put(activation, null);
		return activation;
	}

	@Override
	public void activate() {
		if (fActive) {
			return;
		}

		Iterator c = fLocalActivations.keySet().iterator();
		while (c.hasNext()) {
			IContextActivation activation = (IContextActivation) c.next();
			super.doActivateContext(activation);
		}
		fActive = true;
	}

	@Override
	public void deactivate() {
		if (!fActive) {
			return;
		}
		deactivateContexts(fParentActivations);
		fParentActivations.clear();

		Iterator c = fLocalActivations.keySet().iterator();
		while (c.hasNext()) {
			fLocalActivations.put(c.next(), null);
		}
		fActive = false;
	}
}
