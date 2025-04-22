
package org.eclipse.ui.internal;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class ActivateEditorHandler extends AbstractEvaluationHandler {

	private Expression enabledWhen;

	public ActivateEditorHandler() {
		registerEnablement();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil
				.getActiveWorkbenchWindowChecked(event);
		IWorkbenchPage page = window.getActivePage();
		if (page != null) {
			IEditorPart part = HandlerUtil.getActiveEditor(event);
			if (part != null) {
				page.activate(part);
			} else {
				IWorkbenchPartReference ref = page.getActivePartReference();
				if (ref instanceof IViewReference) {
				}
			}
		}
		return null;
	}

	@Override
	protected Expression getEnabledWhenExpression() {
		if (enabledWhen == null) {
			enabledWhen = new Expression() {
				@Override
				public EvaluationResult evaluate(IEvaluationContext context)
						throws CoreException {
					IWorkbenchWindow window = InternalHandlerUtil
							.getActiveWorkbenchWindow(context);
					if (window != null) {
						if (window.getActivePage() != null) {
							return EvaluationResult.TRUE;
						}
					}
					return EvaluationResult.FALSE;
				}

				@Override
				public void collectExpressionInfo(ExpressionInfo info) {
					info
							.addVariableNameAccess(ISources.ACTIVE_WORKBENCH_WINDOW_NAME);
				}
			};
		}
		return enabledWhen;
	}
}
