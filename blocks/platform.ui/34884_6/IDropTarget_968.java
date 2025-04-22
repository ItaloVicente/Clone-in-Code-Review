package org.eclipse.ui.internal.dnd;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

public interface IDragOverListener {

    IDropTarget drag(Control currentControl, Object draggedObject,
            Point position, Rectangle dragRectangle);
}
