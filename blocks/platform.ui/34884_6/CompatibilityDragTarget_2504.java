package org.eclipse.ui.internal.dnd;

import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Rectangle;

public abstract class AbstractDropTarget implements IDropTarget {
    @Override
	public abstract void drop();

    @Override
	public abstract Cursor getCursor();

    @Override
	public Rectangle getSnapRectangle() {
        return null;
    }
}
