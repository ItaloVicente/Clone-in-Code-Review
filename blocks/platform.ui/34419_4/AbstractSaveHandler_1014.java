
package org.eclipse.ui.internal.expressions;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchWindow;

public class WorkbenchWindowExpression extends Expression {

	private static final int HASH_INITIAL = WorkbenchWindowExpression.class
			.getName().hashCode();

	private final IWorkbenchWindow window;

	public WorkbenchWindowExpression(final IWorkbenchWindow window) {
		this.window = window;
	}

	@Override
	public void collectExpressionInfo(final ExpressionInfo info) {
		if (window != null) {
			info.addVariableNameAccess(ISources.ACTIVE_WORKBENCH_WINDOW_NAME);
		}
	}

	@Override
	protected int computeHashCode() {
		return HASH_INITIAL * HASH_FACTOR + hashCode(window);
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof WorkbenchWindowExpression) {
			final WorkbenchWindowExpression that = (WorkbenchWindowExpression) object;
			return equals(this.window, that.window);
		}

		return false;
	}

	@Override
	public EvaluationResult evaluate(final IEvaluationContext context)
			throws CoreException {
		if (window != null) {
			Object value = context
					.getVariable(ISources.ACTIVE_WORKBENCH_WINDOW_NAME);
			if (window.equals(value)) {
				return EvaluationResult.TRUE;
			}
		}

		return EvaluationResult.FALSE;
	}

	protected final IWorkbenchWindow getWindow() {
		return window;
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("WorkbenchWindowExpression("); //$NON-NLS-1$
		buffer.append(window);
		buffer.append(')');
		return buffer.toString();
	}
}

