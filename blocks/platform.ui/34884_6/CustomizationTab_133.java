
package org.eclipse.ui.internal.navigator.filters;

import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;

public class CoreExpressionFilter extends ViewerFilter {

	private Expression filterExpression;

	public CoreExpressionFilter(Expression aFilterExpression) {
		filterExpression = aFilterExpression;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {

		IEvaluationContext context = NavigatorPlugin.getEvalContext(element);
		return NavigatorPlugin.safeEvaluate(filterExpression, context) != EvaluationResult.TRUE;
	}

}
