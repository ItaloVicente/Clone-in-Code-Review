
package org.eclipse.ui.internal.e4.migration;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.internal.IWorkbenchConstants;

public class WorkbenchMementoReader extends MementoReader {

	WorkbenchMementoReader(IMemento memento) {
		super(memento);
	}

	List<WindowReader> getWindowReaders() {
		IMemento[] windowMems = getChildren(IWorkbenchConstants.TAG_WINDOW);
		List<WindowReader> windows = new ArrayList<WindowReader>(windowMems.length);
		for (IMemento windowMem : windowMems) {
			windows.add(new WindowReader(windowMem));
		}
		return windows;
	}

	IMemento getMruMemento() {
		XMLMemento root = XMLMemento.createWriteRoot(IWorkbenchConstants.TAG_WORKBENCH);
		IMemento mruList = memento.getChild(IWorkbenchConstants.TAG_MRU_LIST);
		if (mruList != null) {
			root.copyChild(mruList);
		}
		return root;
	}

}
