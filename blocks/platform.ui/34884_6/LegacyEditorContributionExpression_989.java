
package org.eclipse.ui.internal.expressions;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.ui.ISources;
import org.eclipse.ui.internal.services.SourcePriorityNameMapping;

public class LegacyEditorActionBarExpression extends Expression {
	private static final int HASH_INITIAL = LegacyEditorActionBarExpression.class
			.getName().hashCode();

	private final String activeEditorId;

	public LegacyEditorActionBarExpression(final String editorId) {

		if (editorId == null) {
			throw new IllegalArgumentException(
					"The targetId for an editor contribution must not be null"); //$NON-NLS-1$
		}
		activeEditorId = editorId;
	}

	@Override
	public final void collectExpressionInfo(final ExpressionInfo info) {
		info.addVariableNameAccess(ISources.ACTIVE_PART_ID_NAME);
		info
				.addVariableNameAccess(SourcePriorityNameMapping.LEGACY_LEGACY_NAME);
	}

	@Override
	protected final int computeHashCode() {
		int hashCode = HASH_INITIAL * HASH_FACTOR + hashCode(activeEditorId);
		return hashCode;
	}

	@Override
	public final boolean equals(final Object object) {
		if (object instanceof LegacyEditorActionBarExpression) {
			final LegacyEditorActionBarExpression that = (LegacyEditorActionBarExpression) object;
			return activeEditorId.equals(that.activeEditorId);
		}

		return false;
	}

	@Override
	public final EvaluationResult evaluate(final IEvaluationContext context) {
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
		buffer.append("LegacyEditorActionBarExpression("); //$NON-NLS-1$
		buffer.append(activeEditorId);
		buffer.append(')');
		return buffer.toString();
	}
}
