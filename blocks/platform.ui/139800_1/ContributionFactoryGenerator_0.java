package org.eclipse.ui.internal.activities;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.ui.activities.IIdentifier;

public class EnabledIdentifierExpression extends Expression {

	private IIdentifier identifier;

	public EnabledIdentifierExpression(IIdentifier identifier) {
		this.identifier = identifier;
	}

	@Override
	public EvaluationResult evaluate(IEvaluationContext context) {
		return EvaluationResult.valueOf(identifier.isEnabled());
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EnabledIdentifierExpression)) {
			return false;
		}
		EnabledIdentifierExpression exp = (EnabledIdentifierExpression) obj;
		return identifier.equals(exp.identifier);
	}

	@Override
	public int hashCode() {
		return identifier.hashCode();
	}

}
