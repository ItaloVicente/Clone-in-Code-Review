
package org.eclipse.ui.internal.expressions;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchWindow;

public final class LegacyEditorContributionExpression extends
		WorkbenchWindowExpression {

	private static final int HASH_INITIAL = LegacyEditorContributionExpression.class
			.getName().hashCode();

	private final String activeEditorId;

	public LegacyEditorContributionExpression(final String activeEditorId,
			final IWorkbenchWindow window) {
		super(window);

		if (activeEditorId == null) {
			throw new NullPointerException(
					"The targetId for an editor contribution must not be null"); //$NON-NLS-1$
		}
		this.activeEditorId = activeEditorId;
	}

	@Override
	public final void collectExpressionInfo(final ExpressionInfo info) {
		super.collectExpressionInfo(info);
		info.addVariableNameAccess(ISources.ACTIVE_PART_ID_NAME);
	}

	@Override
	protected final int computeHashCode() {
		int hashCode = HASH_INITIAL * HASH_FACTOR + hashCode(getWindow());
		hashCode = hashCode * HASH_FACTOR + hashCode(activeEditorId);
		return hashCode;
	}

	@Override
	public final boolean equals(final Object object) {
		if (object instanceof LegacyEditorContributionExpression) {
			final LegacyEditorContributionExpression that = (LegacyEditorContributionExpression) object;
			return equals(this.activeEditorId, that.activeEditorId)
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
		if (equals(activeEditorId, variable)) {
			return EvaluationResult.TRUE;
		}
		return EvaluationResult.FALSE;
	}

	@Override
	public final String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("LegacyEditorContributionExpression("); //$NON-NLS-1$
		buffer.append(activeEditorId);
		buffer.append(',');
		buffer.append(getWindow());
		buffer.append(')');
		return buffer.toString();
	}

}
