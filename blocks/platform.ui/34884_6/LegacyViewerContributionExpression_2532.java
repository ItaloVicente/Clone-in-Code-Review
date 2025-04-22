
package org.eclipse.ui.internal.expressions;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchWindow;

public final class LegacyViewContributionExpression extends
		WorkbenchWindowExpression {

	private static final int HASH_INITIAL = LegacyViewContributionExpression.class
			.getName().hashCode();

	private final String activePartId;

	public LegacyViewContributionExpression(final String activePartId,
			final IWorkbenchWindow window) {
		super(window);

		if (activePartId == null) {
			throw new NullPointerException(
					"The targetId for a view contribution must not be null"); //$NON-NLS-1$
		}
		this.activePartId = activePartId;
	}

	@Override
	public final void collectExpressionInfo(final ExpressionInfo info) {
		super.collectExpressionInfo(info);
		info.addVariableNameAccess(ISources.ACTIVE_PART_ID_NAME);
	}

	@Override
	protected final int computeHashCode() {
		int hashCode = HASH_INITIAL * HASH_FACTOR + hashCode(getWindow());
		hashCode = hashCode * HASH_FACTOR + hashCode(activePartId);
		return hashCode;
	}

	@Override
	public final boolean equals(final Object object) {
		if (object instanceof LegacyViewContributionExpression) {
			final LegacyViewContributionExpression that = (LegacyViewContributionExpression) object;
			return equals(this.activePartId, that.activePartId)
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

		final Object variable = context
				.getVariable(ISources.ACTIVE_PART_ID_NAME);
		if (equals(activePartId, variable)) {
			return EvaluationResult.TRUE;
		}
		return EvaluationResult.FALSE;
	}

	@Override
	public final String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("LegacyViewContributionExpression("); //$NON-NLS-1$
		buffer.append(activePartId);
		buffer.append(',');
		buffer.append(getWindow());
		buffer.append(')');
		return buffer.toString();
	}

}
