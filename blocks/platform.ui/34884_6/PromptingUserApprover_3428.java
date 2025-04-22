package org.eclipse.ui.examples.undo;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;

public class MoveBoxOperation extends BoxOperation {

	private Point origin;
	private Point target;

	public MoveBoxOperation(String label, IUndoContext context, Box box, Canvas canvas, Point newOrigin) {
		super(label, context, null, box, canvas);
		origin = new Point(box.x1, box.y1);
		target = new Point(newOrigin.x, newOrigin.y);
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		if (box==null) {
			throw new ExecutionException("box ix null");
		}
		box.move(target);
		canvas.redraw();
		return Status.OK_STATUS;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		return execute(monitor, info);
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		if (box==null) {
			throw new ExecutionException("box ix null");
		}
		box.move(origin);
		canvas.redraw();
		return Status.OK_STATUS;
	}

	@Override
	public String getLabel() {
		final StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(super.getLabel());
		stringBuffer.append("["); //$NON-NLS-1$
		stringBuffer.append("("); //$NON-NLS-1$
		stringBuffer.append(new Integer(origin.x).toString());
		stringBuffer.append(", "); //$NON-NLS-1$
		stringBuffer.append(new Integer(origin.y).toString());
		stringBuffer.append(')');
		stringBuffer.append(", "); //$NON-NLS-1$
		stringBuffer.append("("); //$NON-NLS-1$
		stringBuffer.append(new Integer(target.x).toString());
		stringBuffer.append(", "); //$NON-NLS-1$
		stringBuffer.append(new Integer(target.y).toString());
		stringBuffer.append(')');
		stringBuffer.append(']');
		return stringBuffer.toString();
	}

}
