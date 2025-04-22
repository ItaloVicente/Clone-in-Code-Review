
package org.eclipse.ui.tests.commands;

import java.util.Collection;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.ui.ISources;
import org.eclipse.ui.internal.expressions.ActivePartExpression;

public class ActiveContextExpression extends Expression {
	private static final int HASH_INITIAL = ActivePartExpression.class
			.getName().hashCode();

	private String contextId;

	private String[] expressionInfo;

	public ActiveContextExpression(String id, String[] info) {
		contextId = id;
		expressionInfo = info;
	}

	@Override
	public void collectExpressionInfo(ExpressionInfo info) {
		for (int i = 0; i < expressionInfo.length; i++) {
			info.addVariableNameAccess(expressionInfo[i]);
		}
	}

	@Override
	public EvaluationResult evaluate(IEvaluationContext context) {
		final Object variable = context
				.getVariable(ISources.ACTIVE_CONTEXT_NAME);
		if (variable != null) {
			if (((Collection) variable).contains(contextId)) {
				return EvaluationResult.TRUE;
			}
		}
		return EvaluationResult.FALSE;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ActiveContextExpression) {
			ActiveContextExpression ace = (ActiveContextExpression) o;
			return equals(contextId, ace.contextId);
		}
		return false;
	}

	@Override
	protected final int computeHashCode() {
		return HASH_INITIAL * HASH_FACTOR + hashCode(contextId);
	}
}
