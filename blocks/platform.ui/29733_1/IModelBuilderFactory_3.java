
package org.eclipse.ui.internal.e4.migration;

import org.eclipse.ui.IMemento;

public interface IMementoReaderFactory {

	WorkbenchMementoReader createWorkbenchReader(IMemento workbenchMem);

	WindowReader createWindowReader(IMemento windowMem);

	PerspectiveReader createPerspectiveReader(IMemento perspectiveMem);

	InfoReader createInfoReader(IMemento infoMem);

}
