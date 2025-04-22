
package org.eclipse.ui;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.swt.widgets.Shell;

public final class LegacyHandlerSubmissionExpression extends Expression {

	private static final int HASH_INITIAL = LegacyHandlerSubmissionExpression.class
			.getName().hashCode();

	private final String activePartId;

	private final Shell activeShell;

	private final IWorkbenchPartSite activeSite;

	public LegacyHandlerSubmissionExpression(final String activePartId,
			final Shell activeShell, final IWorkbenchPartSite activeSite) {

		this.activePartId = activePartId;
		this.activeShell = activeShell;
		this.activeSite = activeSite;
	}

	@Override
	public final void collectExpressionInfo(final ExpressionInfo info) {
		if (activePartId != null) {
			info.addVariableNameAccess(ISources.ACTIVE_PART_ID_NAME);
		}
		if (activeShell != null) {
			info.addVariableNameAccess(ISources.ACTIVE_SHELL_NAME);
			info
					.addVariableNameAccess(ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME);
		}
		if (activeSite != null) {
			info.addVariableNameAccess(ISources.ACTIVE_SITE_NAME);
		}
	}

	@Override
	protected final int computeHashCode() {
		int hashCode = HASH_INITIAL * HASH_FACTOR + hashCode(activePartId);
		hashCode = hashCode * HASH_FACTOR + hashCode(activeShell);
		hashCode = hashCode * HASH_FACTOR + hashCode(activeSite);
		return hashCode;
	}

	@Override
	public final boolean equals(final Object object) {
		if (object instanceof LegacyHandlerSubmissionExpression) {
			final LegacyHandlerSubmissionExpression that = (LegacyHandlerSubmissionExpression) object;
			return equals(this.activePartId, that.activePartId)
					&& equals(this.activeShell, that.activeShell)
					&& equals(this.activeSite, that.activeSite);
		}

		return false;
	}

	@Override
	public final EvaluationResult evaluate(final IEvaluationContext context) {
		if (activePartId != null) {
			final Object value = context
					.getVariable(ISources.ACTIVE_PART_ID_NAME);
			if (!activePartId.equals(value)) {
				return EvaluationResult.FALSE;
			}
		}

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

		if (activeSite != null) {
			final Object value = context.getVariable(ISources.ACTIVE_SITE_NAME);
			if (!activeSite.equals(value)) {
				return EvaluationResult.FALSE;
			}
		}

		return EvaluationResult.TRUE;
	}

	@Override
	public final String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("LegacyHandlerSubmission("); //$NON-NLS-1$
		buffer.append(activeShell);
		buffer.append(',');
		buffer.append(activePartId);
		buffer.append(',');
		buffer.append(activeSite);
		buffer.append(')');
		return buffer.toString();
	}
}
