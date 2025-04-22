package org.eclipse.ui.internal.dnd;

import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Rectangle;

public interface IDropTarget {

    void drop();

    Cursor getCursor();

    Rectangle getSnapRectangle();
}
