package org.eclipse.ui.internal.handlers;

import org.eclipse.ui.internal.InternalHandlerUtil;
import org.eclipse.ui.internal.SaveableHelper;
import org.eclipse.ui.internal.WorkbenchPage;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.ISaveablesSource;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class SaveHandler extends AbstractSaveHandler {

	public SaveHandler() {
		registerEnablement();
	}

	public Object execute(ExecutionEvent event) {

		ISaveablePart saveablePart = getSaveablePart(event);

		if (saveablePart == null)
			return null;

		if (saveablePart instanceof IEditorPart) {
			IEditorPart editorPart = (IEditorPart) saveablePart;
			IWorkbenchPage page = editorPart.getSite().getPage();
			page.saveEditor(editorPart, false);
			return null;
		}

		IWorkbenchPart activePart = HandlerUtil.getActivePart(event);
		WorkbenchPage page = (WorkbenchPage) activePart.getSite().getPage();
		page.saveSaveable(saveablePart, false, false);

		return null;

	}

	protected EvaluationResult evaluate(IEvaluationContext context) {

		IWorkbenchWindow window = InternalHandlerUtil.getActiveWorkbenchWindow(context);
		if (window == null)
			return EvaluationResult.FALSE;
		WorkbenchPage page = (WorkbenchPage) window.getActivePage();

		if (page == null)
			return EvaluationResult.FALSE;

		ISaveablePart saveablePart = getSaveablePart(context);
		if (saveablePart == null)
			return EvaluationResult.FALSE;

		if (saveablePart instanceof ISaveablesSource) {
			ISaveablesSource modelSource = (ISaveablesSource) saveablePart;
			if (SaveableHelper.needsSave(modelSource))
				return EvaluationResult.TRUE;
			return EvaluationResult.FALSE;
		}

		if (saveablePart != null && saveablePart.isDirty())
			return EvaluationResult.TRUE;

		return EvaluationResult.FALSE;
	}
}
