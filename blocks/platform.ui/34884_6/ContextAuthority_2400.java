
package org.eclipse.ui.internal.contexts;

import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.ui.ISources;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.internal.services.EvaluationResultCache;

final class ContextActivation extends EvaluationResultCache implements
		IContextActivation {

	private final String contextId;

	private final IContextService contextService;

	public ContextActivation(final String contextId,
			final Expression expression, final IContextService contextService) {
		super(expression);

		if (contextId == null) {
			throw new NullPointerException(
					"The context identifier for a context activation cannot be null"); //$NON-NLS-1$
		}

		if (contextService == null) {
			throw new NullPointerException(
					"The context service for an activation cannot be null"); //$NON-NLS-1$
		}

		this.contextId = contextId;
		this.contextService = contextService;
	}

	@Override
	public final void clearActive() {
		clearResult();
	}

	@Override
	public final String getContextId() {
		return contextId;
	}

	@Override
	public final IContextService getContextService() {
		return contextService;
	}

	@Override
	public final boolean isActive(final IEvaluationContext context) {
		return evaluate(context);
	}

	@Override
	public final String toString() {
		final StringBuffer buffer = new StringBuffer();

		buffer.append("ContextActivation(contextId="); //$NON-NLS-1$
		buffer.append(contextId);
		buffer.append(",sourcePriority="); //$NON-NLS-1$
		buffer.append(getSourcePriority());
		buffer.append(')');

		return buffer.toString();
	}

}
