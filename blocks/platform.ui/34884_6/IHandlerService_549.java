
package org.eclipse.ui.handlers;

import org.eclipse.core.commands.IHandler;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.ui.internal.services.IEvaluationResultCache;

public interface IHandlerActivation extends IEvaluationResultCache, Comparable {

	public static final int ROOT_DEPTH = 1;

	@Deprecated
	public void clearActive();

	public String getCommandId();

	public int getDepth();

	public IHandler getHandler();

	public IHandlerService getHandlerService();

	@Deprecated
	public boolean isActive(IEvaluationContext context);
}
