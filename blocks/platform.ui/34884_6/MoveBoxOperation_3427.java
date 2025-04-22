package org.eclipse.ui.examples.undo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Canvas;

public class ClearBoxesOperation extends BoxOperation {

	private List<Box> savedBoxes = new ArrayList<Box>();

	public ClearBoxesOperation(String label, IUndoContext context, Boxes boxes, Canvas canvas) {
		super(label, context, boxes, null, canvas);
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info){
		savedBoxes = boxes.getBoxes();
		boxes.clear();
		canvas.redraw();
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) {
		boxes.setBoxes(savedBoxes);
		canvas.redraw();
		return Status.OK_STATUS;
	}

}
