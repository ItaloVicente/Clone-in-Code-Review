
package org.eclipse.ui.internal.expressions;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;

public final class AlwaysEnabledExpression extends Expression {

	public static final AlwaysEnabledExpression INSTANCE = new AlwaysEnabledExpression();


	private AlwaysEnabledExpression() {
	}

	@Override
	public EvaluationResult evaluate(IEvaluationContext context) {
		return EvaluationResult.TRUE;
	}
}
