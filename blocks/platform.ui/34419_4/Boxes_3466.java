package org.eclipse.ui.examples.undo;

import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Canvas;

public abstract class BoxOperation extends AbstractOperation {

	Box box;

	Boxes boxes;

	Canvas canvas;

	public BoxOperation(String label, IUndoContext undoContext, Boxes boxes, Box box, Canvas canvas) {
		super (label);
		addContext(undoContext);
		this.boxes = boxes;
		this.box = box;
		this.canvas = canvas;
	}

	void showMessage(String message) {
		MessageDialog.openInformation(canvas.getShell(),
				UndoExampleMessages.BoxView_Title, message);
	}
}
