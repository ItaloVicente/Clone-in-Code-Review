
package org.eclipse.ui.contexts;

import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.ui.internal.services.IEvaluationResultCache;

public interface IContextActivation extends IEvaluationResultCache {

	@Deprecated
	public void clearActive();

	public String getContextId();

	public IContextService getContextService();

	@Deprecated
	public boolean isActive(IEvaluationContext context);
}
