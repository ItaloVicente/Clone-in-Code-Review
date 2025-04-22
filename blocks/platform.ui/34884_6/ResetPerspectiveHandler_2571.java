
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.ui.IWorkbench;

public class QuitHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEvaluationContext context = (IEvaluationContext) event.getApplicationContext();
		IWorkbench workbench = (IWorkbench) context.getVariable(IWorkbench.class.getName());
		workbench.close();
		return null;
	}

}
