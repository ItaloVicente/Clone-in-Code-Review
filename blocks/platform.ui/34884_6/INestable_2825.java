
package org.eclipse.ui.internal.services;

import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.ui.ISources;

public interface IEvaluationResultCache {

	public void clearResult();

	public Expression getExpression();

	public int getSourcePriority();

	public boolean evaluate(IEvaluationContext context);

	public void setResult(boolean result);
}
