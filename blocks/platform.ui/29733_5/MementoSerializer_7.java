
package org.eclipse.ui.internal.e4.migration;

import javax.inject.Inject;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.ui.IMemento;

public class MementoReaderFactoryImpl implements IMementoReaderFactory {

	@Inject
	private IEclipseContext context;

	@Override
	public WorkbenchMementoReader createWorkbenchReader(IMemento workbenchMem) {
		IEclipseContext childContext = createContextWithMemento(workbenchMem);
		WorkbenchMementoReader reader = ContextInjectionFactory.make(WorkbenchMementoReader.class,
				childContext);
		return reader;
	}

	@Override
	public PerspectiveReader createPerspectiveReader(IMemento perspectiveMem) {
		IEclipseContext childContext = createContextWithMemento(perspectiveMem);
		PerspectiveReader reader = ContextInjectionFactory.make(PerspectiveReader.class,
				childContext);
		return reader;
	}

	@Override
	public WindowReader createWindowReader(IMemento windowMem) {
		IEclipseContext childContext = createContextWithMemento(windowMem);
		WindowReader reader = ContextInjectionFactory.make(WindowReader.class,
				childContext);
		return reader;
	}

	@Override
	public InfoReader createInfoReader(IMemento infoMem) {
		IEclipseContext childContext = createContextWithMemento(infoMem);
		InfoReader reader = ContextInjectionFactory.make(InfoReader.class, childContext);
		return reader;
	}

	private IEclipseContext createContextWithMemento(IMemento workbenchMem) {
		IEclipseContext childContext = context.createChild();
		childContext.set(IMemento.class, workbenchMem);
		return childContext;
	}

}
