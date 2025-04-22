
package org.eclipse.ui.internal.expressions;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchPart;

public final class ActivePartExpression extends Expression {

	private static final int HASH_INITIAL = ActivePartExpression.class
			.getName().hashCode();

	private final IWorkbenchPart activePart;

	public ActivePartExpression(final IWorkbenchPart activePart) {
		if (activePart == null) {
			throw new NullPointerException("The active part must not be null"); //$NON-NLS-1$
		}
		this.activePart = activePart;
	}

	@Override
	public final void collectExpressionInfo(final ExpressionInfo info) {
		info.addVariableNameAccess(ISources.ACTIVE_PART_NAME);
	}

	@Override
	protected final int computeHashCode() {
		return HASH_INITIAL * HASH_FACTOR + hashCode(activePart);
	}

	@Override
	public final boolean equals(final Object object) {
		if (object instanceof ActivePartExpression) {
			final ActivePartExpression that = (ActivePartExpression) object;
			return equals(this.activePart, that.activePart);
		}

		return false;
	}

	@Override
	public final EvaluationResult evaluate(final IEvaluationContext context) {
		final Object variable = context.getVariable(ISources.ACTIVE_PART_NAME);
		if (equals(activePart, variable)) {
			return EvaluationResult.TRUE;
		}
		return EvaluationResult.FALSE;
	}

	@Override
	public final String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("ActivePartExpression("); //$NON-NLS-1$
		buffer.append(activePart);
		buffer.append(')');
		return buffer.toString();
	}
}
