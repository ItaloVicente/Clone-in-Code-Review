
package org.eclipse.ui.internal.expressions;

import java.util.Collection;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchWindow;

public final class LegacyViewerContributionExpression extends
		WorkbenchWindowExpression {

	private static final int HASH_INITIAL = LegacyViewerContributionExpression.class
			.getName().hashCode();

	private final Expression expression;

	private final String targetId;

	public LegacyViewerContributionExpression(final String targetId,
			final IWorkbenchWindow window, final Expression childExpression) {
		super(window);

		if (targetId == null) {
			throw new NullPointerException("The targetId cannot be null"); //$NON-NLS-1$
		}
		this.targetId = targetId;
		this.expression = childExpression;
	}

	@Override
	public final void collectExpressionInfo(final ExpressionInfo info) {
		super.collectExpressionInfo(info);
		info.addVariableNameAccess(ISources.ACTIVE_MENU_NAME);
		if (expression != null) {
			expression.collectExpressionInfo(info);
		}
	}

	@Override
	protected final int computeHashCode() {
		int hashCode = HASH_INITIAL * HASH_FACTOR + hashCode(getWindow());
		hashCode = hashCode * HASH_FACTOR + hashCode(expression);
		hashCode = hashCode * HASH_FACTOR + hashCode(targetId);
		return hashCode;
	}

	@Override
	public final boolean equals(final Object object) {
		if (object instanceof LegacyViewerContributionExpression) {
			final LegacyViewerContributionExpression that = (LegacyViewerContributionExpression) object;
			return equals(this.targetId, that.targetId)
					&& equals(this.expression, that.expression)
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

		final Object value = context.getVariable(ISources.ACTIVE_MENU_NAME);
		if (value instanceof String) {
			final String menuId = (String) value;
			if (targetId.equals(menuId)) {
				if (expression == null) {
					return EvaluationResult.TRUE;
				}

				return expression.evaluate(context);
			}
		} else if (value instanceof Collection) {
			final Collection menuIds = (Collection) value;
			if (menuIds.contains(targetId)) {
				if (expression == null) {
					return EvaluationResult.TRUE;
				}

				return expression.evaluate(context);
			}
		}

		return EvaluationResult.FALSE;
	}

	@Override
	public final String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("ViewerContributionExpression("); //$NON-NLS-1$
		buffer.append(targetId);
		buffer.append(',');
		buffer.append(expression);
		buffer.append(',');
		buffer.append(getWindow());
		buffer.append(')');
		return buffer.toString();
	}
}
