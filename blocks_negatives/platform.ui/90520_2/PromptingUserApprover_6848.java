/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.undo;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.GC;

/**
 * A collection of boxes
 */
public class Boxes  {

	/*
	 * The "model, a list of boxes
-	 */
-	private List<Box> boxes = new ArrayList<Box>();
-
-	/*
-	 * Constructs a box collection
-	 */
-	public Boxes() {
-		super();
-	}
-
-	/*
-	 * Add the specified box to the group of boxes.
-	 */
-	public void add(Box box) {
-		boxes.add(box);
-	}
-
-	/*
-	 * Remove the specified box from the group of boxes.
-	 */
-	public void remove(Box box) {
-		boxes.remove(box);
-	}
-
-	/*
-	 * Clear all the boxes from the list of boxes.
-	 */
-	public void clear() {
-		boxes = new ArrayList<Box>();
-	}
-
-	/*
-	 * Return true if the group of boxes contains the specified box.
-	 */
-	public boolean contains(Box box) {
-		return boxes.contains(box);
-	}
-
-	/*
-	 * Draw the boxes with the specified gc.
-	 */
-	public void draw(GC gc) {
-		for (int i = 0; i < boxes.size(); i++) {
-			boxes.get(i).draw(gc);
-		}
-	}
-
-	/*
-	 * Return the box containing the specified x and y, or null
-	 * if no box contains the point.
-	 */
-	public Box getBox(int x, int y) {
-		for (int i=0; i< boxes.size(); i++) {
-			Box box = boxes.get(i);
-			if (box.contains(x, y)) {
-				return box;
-			}
-		}
-		return null;
-	}
-
-	/*
-	 * Return the list of boxes known by this group of boxes.
-	 */
-	public List<Box> getBoxes() {
-		return boxes;
-	}
-
-	/*
-	 * Set the list of boxes known by this group of boxes.
-	 */
-	public void setBoxes(List<Box> boxes) {
-		this.boxes = boxes;
-	}
-
-}
diff --git a/examples/org.eclipse.ui.examples.undo/Eclipse UI Examples Undo/org/eclipse/ui/examples/undo/ClearBoxesOperation.java b/examples/org.eclipse.ui.examples.undo/Eclipse UI Examples Undo/org/eclipse/ui/examples/undo/ClearBoxesOperation.java
deleted file mode 100644
index de5eef984f..0000000000
--- a/examples/org.eclipse.ui.examples.undo/Eclipse UI Examples Undo/org/eclipse/ui/examples/undo/ClearBoxesOperation.java	
+++ /dev/null
@@ -1,57 +0,0 @@
-/*******************************************************************************
- * Copyright (c) 2005, 2006 IBM Corporation and others.
- * All rights reserved. This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License v1.0
- * which accompanies this distribution, and is available at
- * http://www.eclipse.org/legal/epl-v10.html
- *
- * Contributors:
- *     IBM Corporation - initial API and implementation
- *******************************************************************************/
-package org.eclipse.ui.examples.undo;
-
-import java.util.ArrayList;
-import java.util.List;
-
-import org.eclipse.core.commands.operations.IUndoContext;
-import org.eclipse.core.runtime.IAdaptable;
-import org.eclipse.core.runtime.IProgressMonitor;
-import org.eclipse.core.runtime.IStatus;
-import org.eclipse.core.runtime.Status;
-import org.eclipse.swt.widgets.Canvas;
-
-/**
- * An operation that adds a box.
- */
-public class ClearBoxesOperation extends BoxOperation {
-
-	/*
-	 * The boxes that are saved after clearing
-	 */
-	private List<Box> savedBoxes = new ArrayList<Box>();
-
-	public ClearBoxesOperation(String label, IUndoContext context, Boxes boxes, Canvas canvas) {
-		super(label, context, boxes, null, canvas);
-	}
-
-	@Override
-	public IStatus execute(IProgressMonitor monitor, IAdaptable info){
-		savedBoxes = boxes.getBoxes();
-		boxes.clear();
-		canvas.redraw();
-		return Status.OK_STATUS;
-	}
-
-	@Override
-	public IStatus redo(IProgressMonitor monitor, IAdaptable info) {
-		return execute(monitor, info);
-	}
-
-	@Override
-	public IStatus undo(IProgressMonitor monitor, IAdaptable info) {
-		boxes.setBoxes(savedBoxes);
-		canvas.redraw();
-		return Status.OK_STATUS;
-	}
-
-}
diff --git a/examples/org.eclipse.ui.examples.undo/Eclipse UI Examples Undo/org/eclipse/ui/examples/undo/MoveBoxOperation.java b/examples/org.eclipse.ui.examples.undo/Eclipse UI Examples Undo/org/eclipse/ui/examples/undo/MoveBoxOperation.java
deleted file mode 100644
index 918c7c07c5..0000000000
--- a/examples/org.eclipse.ui.examples.undo/Eclipse UI Examples Undo/org/eclipse/ui/examples/undo/MoveBoxOperation.java	
+++ /dev/null
@@ -1,87 +0,0 @@
-/*******************************************************************************
- * Copyright (c) 2005, 2007 IBM Corporation and others.
- * All rights reserved. This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License v1.0
- * which accompanies this distribution, and is available at
- * http://www.eclipse.org/legal/epl-v10.html
- *
- * Contributors:
- *     IBM Corporation - initial API and implementation
- *******************************************************************************/
-package org.eclipse.ui.examples.undo;
-
-import org.eclipse.core.commands.ExecutionException;
-import org.eclipse.core.commands.operations.IUndoContext;
-import org.eclipse.core.runtime.IAdaptable;
-import org.eclipse.core.runtime.IProgressMonitor;
-import org.eclipse.core.runtime.IStatus;
-import org.eclipse.core.runtime.Status;
-import org.eclipse.swt.graphics.Point;
-import org.eclipse.swt.widgets.Canvas;
-
-/**
- * An operation that adds a box.
- */
-public class MoveBoxOperation extends BoxOperation {
-
-	/*
-	 * The point the box should move to/from.
-	 */
-	private Point origin;
-	private Point target;
-
-	public MoveBoxOperation(String label, IUndoContext context, Box box, Canvas canvas, Point newOrigin) {
-		super(label, context, null, box, canvas);
-		origin = new Point(box.x1, box.y1);
-		target = new Point(newOrigin.x, newOrigin.y);
-	}
-
-	@Override
-	public IStatus execute(IProgressMonitor monitor, IAdaptable info)
-			throws ExecutionException {
-		if (box==null) {
-			throw new ExecutionException(box ix null");
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
		stringBuffer.append(Integer.valueOf(origin.x).toString());
		stringBuffer.append(", "); //$NON-NLS-1$
		stringBuffer.append(Integer.valueOf(origin.y).toString());
		stringBuffer.append(')');
		stringBuffer.append(", "); //$NON-NLS-1$
		stringBuffer.append(Integer.valueOf(target.x).toString());
		stringBuffer.append(", "); //$NON-NLS-1$
		stringBuffer.append(Integer.valueOf(target.y).toString());
		stringBuffer.append(')');
		stringBuffer.append(']');
		return stringBuffer.toString();
	}

}
