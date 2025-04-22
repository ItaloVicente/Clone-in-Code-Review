
package org.eclipse.ui.internal.e4.migration;

import javax.inject.Inject;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;

public class ModelBuilderFactoryImpl implements IModelBuilderFactory {

	@Inject
	private IEclipseContext context;

	@Override
	public WindowBuilder createWindowBuilder(WindowReader windowReader) {
		IEclipseContext childContext = context.createChild();
		childContext.set(WindowReader.class, windowReader);
		return ContextInjectionFactory.make(WindowBuilder.class, childContext);
	}

	@Override
	public PerspectiveBuilder createPerspectiveBuilder(PerspectiveReader perspReader) {
		IEclipseContext childContext = context.createChild();
		childContext.set(PerspectiveReader.class, perspReader);
		return ContextInjectionFactory.make(PerspectiveBuilder.class,
				childContext);
	}

	@Override
	public ApplicationBuilder createApplicationBuilder(WorkbenchMementoReader reader) {
		IEclipseContext childContext = context.createChild();
		childContext.set(WorkbenchMementoReader.class, reader);
		return ContextInjectionFactory.make(ApplicationBuilder.class,
				childContext);
	}

}
