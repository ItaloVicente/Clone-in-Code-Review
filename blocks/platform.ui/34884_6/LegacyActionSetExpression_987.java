
package org.eclipse.ui.internal.expressions;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.ActionExpression;

public final class LegacyActionExpressionWrapper extends
		WorkbenchWindowExpression {

	private static final int HASH_INITIAL = LegacyActionExpressionWrapper.class
			.getName().hashCode();

	private final ActionExpression expression;

	public LegacyActionExpressionWrapper(final ActionExpression expression,
			final IWorkbenchWindow window) {
		super(window);

		if (expression == null) {
			throw new NullPointerException(
					"The action expression cannot be null"); //$NON-NLS-1$
		}
		this.expression = expression;
	}

	@Override
	public final void collectExpressionInfo(final ExpressionInfo info) {
		super.collectExpressionInfo(info);
		info.markDefaultVariableAccessed();
	}

	@Override
	protected final int computeHashCode() {
		int hashCode = HASH_INITIAL * HASH_FACTOR + hashCode(getWindow());
		hashCode = hashCode * HASH_FACTOR + hashCode(expression);
		return hashCode;
	}

	@Override
	public final boolean equals(final Object object) {
		if (object instanceof LegacyActionExpressionWrapper) {
			final LegacyActionExpressionWrapper that = (LegacyActionExpressionWrapper) object;
			return equals(this.expression, that.expression)
					&& equals(this.getWindow(), that.getWindow());
		}

		return false;
	}

	@Override
	public final EvaluationResult evaluate(final IEvaluationContext context)
			throws CoreException {
		final EvaluationResult result = super.evaluate(context);
		if (result == EvaluationResult.FALSE) {
			return result;
		}

		final Object defaultVariable = context
				.getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);
		if (defaultVariable instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) defaultVariable;
			if (expression.isEnabledFor(selection)) {
				return EvaluationResult.TRUE;
			}
		} else if (expression.isEnabledFor(defaultVariable)) {
			return EvaluationResult.TRUE;
		}

		return EvaluationResult.FALSE;
	}

	@Override
	public final String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("LegacyActionExpressionWrapper("); //$NON-NLS-1$
		buffer.append(expression);
		buffer.append(',');
		buffer.append(getWindow());
		buffer.append(')');
		return buffer.toString();
	}
}
