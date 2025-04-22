
package org.eclipse.ui.internal.services;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.ISources;

public abstract class EvaluationResultCache implements IEvaluationResultCache {

	private EvaluationResult evaluationResult = null;

	private final Expression expression;

	private final int sourcePriority;

	protected EvaluationResultCache(final Expression expression) {
		this.expression = expression;
		this.sourcePriority = SourcePriorityNameMapping
				.computeSourcePriority(expression);
	}

	@Override
	public final void clearResult() {
		evaluationResult = null;
	}

	@Override
	public final boolean evaluate(final IEvaluationContext context) {
		if (expression == null) {
			return true;
		}

		if (evaluationResult == null) {
			try {
				evaluationResult = expression.evaluate(context);
			} catch (final CoreException e) {
				evaluationResult = EvaluationResult.FALSE;
				return false;
			}
		}

		return evaluationResult != EvaluationResult.FALSE;
	}

	@Override
	public final Expression getExpression() {
		return expression;
	}

	@Override
	public final int getSourcePriority() {
		return sourcePriority;
	}

	@Override
	public final void setResult(final boolean result) {
		if (result) {
			evaluationResult = EvaluationResult.TRUE;
		} else {
			evaluationResult = EvaluationResult.FALSE;
		}
	}
}
