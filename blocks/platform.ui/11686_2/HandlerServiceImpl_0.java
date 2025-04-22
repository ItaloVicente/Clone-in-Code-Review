
package org.eclipse.e4.core.commands.internal;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.e4.core.commands.ExpressionContext;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

public class HandlerServiceHandler extends AbstractHandler {

	private static final String FAILED_TO_FIND_HANDLER_DURING_EXECUTION = "Failed to find handler during execution"; //$NON-NLS-1$
	private String commandId;

	public HandlerServiceHandler(String commandId) {
		this.commandId = commandId;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		IEclipseContext executionContext = getExecutionContext(evaluationContext);
		if (executionContext == null) {
			return;
		}

		IEclipseContext staticContext = (IEclipseContext) executionContext
				.get(HandlerServiceImpl.STATIC_CONTEXT);
		Object handler = HandlerServiceImpl.lookUpHandler(executionContext, commandId);
		if (handler == null) {
			setBaseEnabled(false);
			return;
		}
		try {
			Boolean result = ((Boolean) ContextInjectionFactory.invoke(handler, CanExecute.class,
					executionContext, staticContext, Boolean.TRUE));
			setBaseEnabled(result.booleanValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	IEclipseContext getExecutionContext(Object evalObj) {
		if (evalObj instanceof IEclipseContext) {
			return (IEclipseContext) evalObj;
		}
		if (evalObj instanceof ExpressionContext) {
			return ((ExpressionContext) evalObj).eclipseContext;
		}
		if (evalObj instanceof IEvaluationContext) {
			return getExecutionContext(((IEvaluationContext) evalObj).getParent());
		}
		return null;
	}

	@Override
	public boolean isHandled() {
		IEclipseContext executionContext = HandlerServiceImpl.peek();
		if (executionContext != null) {
			Object handler = HandlerServiceImpl.lookUpHandler(executionContext, commandId);
			return handler != null;

		}
		return false;
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEclipseContext executionContext = getExecutionContext(event.getApplicationContext());
		if (executionContext == null) {
			throw new ExecutionException(FAILED_TO_FIND_HANDLER_DURING_EXECUTION,
					new NotHandledException(FAILED_TO_FIND_HANDLER_DURING_EXECUTION));
		}

		IEclipseContext staticContext = (IEclipseContext) executionContext
				.get(HandlerServiceImpl.STATIC_CONTEXT);
		Object handler = HandlerServiceImpl.lookUpHandler(executionContext, commandId);
		if (handler == null) {
			return null;
		}
		return ContextInjectionFactory.invoke(handler, Execute.class, executionContext,
				staticContext, null);
	}

}
