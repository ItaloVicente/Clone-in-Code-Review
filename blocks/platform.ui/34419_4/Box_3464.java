package org.eclipse.ui.examples.undo;

import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Canvas;

public class AddBoxOperation extends BoxOperation {

	public AddBoxOperation(String label, IUndoContext context, Boxes boxes, Box box, Canvas canvas) {
		super(label, context, boxes, box, canvas);
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) {
		boxes.add(box);
		canvas.redraw(box.x1, box.y1, box.x2, box.y2, false);
		return Status.OK_STATUS;
	}

	@Override
	public boolean canUndo() {
		return boxes.contains(box);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) {
		boxes.remove(box);
		canvas.redraw(box.x1, box.y1, box.x2, box.y2, false);
		return Status.OK_STATUS;
	}

	@Override
	public boolean canRedo() {
		return !boxes.contains(box);
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) {
		return execute(monitor, info);
	}



}
