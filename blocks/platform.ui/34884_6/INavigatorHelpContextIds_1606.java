package org.eclipse.ui.internal.navigator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.expressions.ElementHandler;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.SafeRunner;

public class CustomAndExpression extends Expression {

	protected List<Expression> fExpressions;

	public CustomAndExpression(IConfigurationElement element) {
		Assert.isNotNull(element);

		final IConfigurationElement[] children = element.getChildren();
		if (children.length == 0)
			return;
		SafeRunner.run(new NavigatorSafeRunnable() {
			@Override
			public void run() throws Exception {
				fExpressions = new ArrayList<Expression>();
				for (int i = 0; i < children.length; i++) {
					fExpressions.add(ElementHandler.getDefault().create(
							ExpressionConverter.getDefault(), children[i]));
				}
			}
		});

	}

	@Override
	public EvaluationResult evaluate(IEvaluationContext scope) {
		if (fExpressions == null) {
			return EvaluationResult.TRUE;
		}
		NavigatorPlugin.Evaluator evaluator = new NavigatorPlugin.Evaluator();
		EvaluationResult result = EvaluationResult.TRUE;
		for (Iterator<Expression> iter = fExpressions.iterator(); iter.hasNext();) {
			Expression expression = iter.next();
			evaluator.expression = expression;
			evaluator.scope = scope;
			SafeRunner.run(evaluator);
			result = result.and(evaluator.result);
			if (result == EvaluationResult.FALSE) {
				return result;
			}
		}
		return result;
	}

}
