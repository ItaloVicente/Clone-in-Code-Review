
package org.eclipse.ui.dnd;

import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Control;

public interface IDragAndDropService {
	public void addMergedDropTarget(Control control, int ops, Transfer[] transfers, DropTargetListener listener);
	
	public void removeMergedDropTarget(Control control);
}
