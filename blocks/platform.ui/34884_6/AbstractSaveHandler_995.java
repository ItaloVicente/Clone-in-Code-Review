
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionInfo;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.internal.AbstractEvaluationHandler;
import org.eclipse.ui.internal.InternalHandlerUtil;
import org.eclipse.ui.internal.util.Util;

public abstract class AbstractSaveHandler extends AbstractEvaluationHandler {

	protected static DirtyStateTracker dirtyStateTracker;
	private Expression enabledWhen;

	public AbstractSaveHandler() {
		if (dirtyStateTracker == null)
			dirtyStateTracker = new DirtyStateTracker();
	}

	@Override
	protected Expression getEnabledWhenExpression() {
		if (enabledWhen == null) {
			enabledWhen = new Expression() {
				@Override
				public EvaluationResult evaluate(IEvaluationContext context) {
					return AbstractSaveHandler.this.evaluate(context);
				}
	
				@Override
				public void collectExpressionInfo(ExpressionInfo info) {
					info.addVariableNameAccess(ISources.ACTIVE_PART_NAME);
				}
			};
		}
		return enabledWhen;
	}
	
	protected abstract EvaluationResult evaluate(IEvaluationContext context);
	
	protected ISaveablePart getSaveablePart(IEvaluationContext context) {
		IWorkbenchPart activePart = InternalHandlerUtil.getActivePart(context);

		if (activePart instanceof ISaveablePart)
			return (ISaveablePart) activePart;

		ISaveablePart part = (ISaveablePart) Util.getAdapter(activePart, ISaveablePart.class);
		if (part != null)
			return part;

		return InternalHandlerUtil.getActiveEditor(context);
	}
	
	protected ISaveablePart getSaveablePart(ExecutionEvent event) {

		IWorkbenchPart activePart = HandlerUtil.getActivePart(event);
		if (activePart instanceof ISaveablePart) {
			return (ISaveablePart) activePart;
		}

		ISaveablePart part = (ISaveablePart) Util.getAdapter(activePart, ISaveablePart.class);
		if (part != null)
			return part;

		return HandlerUtil.getActiveEditor(event);
	}

}
