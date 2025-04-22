
package org.eclipse.ui;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.swt.widgets.Shell;

public final class ActiveShellExpression extends Expression {

	private static final int HASH_INITIAL = ActiveShellExpression.class
			.getName().hashCode();

	public static final int SOURCES = ISources.ACTIVE_SHELL
			| ISources.ACTIVE_WORKBENCH_WINDOW;

	private final Shell activeShell;

	public ActiveShellExpression(final Shell activeShell) {
		this.activeShell = activeShell;
	}

	@Override
	public final void collectExpressionInfo(final ExpressionInfo info) {
		info.addVariableNameAccess(ISources.ACTIVE_SHELL_NAME);
		info.addVariableNameAccess(ISources.ACTIVE_WORKBENCH_WINDOW_NAME);
	}

	@Override
	protected final int computeHashCode() {
		return HASH_INITIAL * HASH_FACTOR + hashCode(activeShell);
	}

	@Override
	public final boolean equals(final Object object) {
		if (object instanceof ActiveShellExpression) {
			final ActiveShellExpression that = (ActiveShellExpression) object;
			return equals(this.activeShell, that.activeShell);
		}

		return false;
	}

	@Override
	public final EvaluationResult evaluate(final IEvaluationContext context) {
		if (activeShell != null) {
			Object value = context.getVariable(ISources.ACTIVE_SHELL_NAME);
			if (!activeShell.equals(value)) {
				value = context
						.getVariable(ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME);
				if (!activeShell.equals(value)) {
					return EvaluationResult.FALSE;
				}
			}
		}

		return EvaluationResult.TRUE;
	}

	@Override
	public final String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("ActiveShellExpression("); //$NON-NLS-1$
		buffer.append(activeShell);
		buffer.append(')');
		return buffer.toString();
	}
}
