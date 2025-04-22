
package org.eclipse.ui.handlers;

import java.util.Collection;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandler2;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.services.IServiceWithSources;


public interface IHandlerService extends IServiceWithSources {

	public IHandlerActivation activateHandler(IHandlerActivation activation);

	public IHandlerActivation activateHandler(String commandId, IHandler handler);

	public IHandlerActivation activateHandler(String commandId,
			IHandler handler, Expression expression);

	public IHandlerActivation activateHandler(String commandId,
			IHandler handler, Expression expression, boolean global);

	@Deprecated
	public IHandlerActivation activateHandler(String commandId,
			IHandler handler, Expression expression, int sourcePriorities);

	public ExecutionEvent createExecutionEvent(Command command, Event event);

	public ExecutionEvent createExecutionEvent(ParameterizedCommand command,
			Event event);

	public void deactivateHandler(IHandlerActivation activation);

	public void deactivateHandlers(Collection activations);

	public Object executeCommand(String commandId, Event event)
			throws ExecutionException, NotDefinedException,
			NotEnabledException, NotHandledException;

	public Object executeCommand(ParameterizedCommand command, Event event)
			throws ExecutionException, NotDefinedException,
			NotEnabledException, NotHandledException;

	public Object executeCommandInContext(ParameterizedCommand command,
			Event event, IEvaluationContext context) throws ExecutionException,
			NotDefinedException, NotEnabledException, NotHandledException;

	public IEvaluationContext createContextSnapshot(boolean includeSelection);

	public IEvaluationContext getCurrentState();

	public void readRegistry();

	public void setHelpContextId(IHandler handler, String helpContextId);
}
