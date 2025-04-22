
package org.eclipse.ui.internal.e4.migration;

import javax.inject.Inject;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;

public class ModelBuilderFactoryImpl implements IModelBuilderFactory {

	@Inject
	private IEclipseContext context;

	@Override
	public WindowBuilder createWindowBuilder(WindowReader windowReader) {
		IEclipseContext childContext = context.createChild();
		childContext.set(WindowReader.class, windowReader);
		WindowBuilder builder = ContextInjectionFactory.make(WindowBuilder.class, childContext);
		return builder;
	}

	@Override
	public PerspectiveBuilder createPerspectiveBuilder(PerspectiveReader perspReader,
			MPerspectiveStack perspStack, MWindow window) {
		IEclipseContext childContext = context.createChild();
		childContext.set(PerspectiveReader.class, perspReader);
		childContext.set(MPerspectiveStack.class, perspStack);
		childContext.set(MWindow.class, window);
		PerspectiveBuilder builder = ContextInjectionFactory.make(PerspectiveBuilder.class,
				childContext);
		return builder;
	}

	@Override
	public PerspectiveBuilder createPerspectiveBuilder(PerspectiveReader perspReader) {
		IEclipseContext childContext = context.createChild();
		childContext.set(PerspectiveReader.class, perspReader);
		PerspectiveBuilder builder = ContextInjectionFactory.make(PerspectiveBuilder.class,
				childContext);
		return builder;
	}

	@Override
	public ApplicationBuilder createApplicationBuilder(WorkbenchMementoReader reader) {
		IEclipseContext childContext = context.createChild();
		childContext.set(WorkbenchMementoReader.class, reader);
		ApplicationBuilder builder = ContextInjectionFactory.make(ApplicationBuilder.class,
				childContext);
		return builder;
	}

}
