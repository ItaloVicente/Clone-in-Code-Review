
package org.eclipse.ui.internal.expressions;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.SelectionEnabler;

public final class LegacySelectionEnablerWrapper extends
		WorkbenchWindowExpression {

	private static final int HASH_INITIAL = LegacySelectionEnablerWrapper.class
			.getName().hashCode();

	private final SelectionEnabler enabler;

	public LegacySelectionEnablerWrapper(final SelectionEnabler enabler,
			final IWorkbenchWindow window) {
		super(window);

		if (enabler == null) {
			throw new NullPointerException("There is no enabler"); //$NON-NLS-1$
		}
		this.enabler = enabler;
	}

	@Override
	public final void collectExpressionInfo(final ExpressionInfo info) {
		super.collectExpressionInfo(info);
		info.markDefaultVariableAccessed();
	}

	@Override
	protected final int computeHashCode() {
		int hashCode = HASH_INITIAL * HASH_FACTOR + hashCode(getWindow());
		hashCode = hashCode * HASH_FACTOR + hashCode(enabler);
		return hashCode;
	}

	@Override
	public final boolean equals(final Object object) {
		if (object instanceof LegacySelectionEnablerWrapper) {
			final LegacySelectionEnablerWrapper that = (LegacySelectionEnablerWrapper) object;
			return equals(this.enabler, that.enabler)
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
		if (defaultVariable instanceof ISelection) {
			final ISelection selection = (ISelection) defaultVariable;
			if (enabler.isEnabledForSelection(selection)) {
				return EvaluationResult.TRUE;
			}
		}

		return EvaluationResult.FALSE;
	}

	@Override
	public final String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("LegacySelectionEnablerWrapper("); //$NON-NLS-1$
		buffer.append(enabler);
		buffer.append(',');
		buffer.append(getWindow());
		buffer.append(')');
		return buffer.toString();
	}
}
