
package org.eclipse.ui.internal.e4.migration;

import org.eclipse.e4.ui.model.application.ui.advanced.MPerspectiveStack;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;

public interface IModelBuilderFactory {

	ApplicationBuilder createApplicationBuilder(WorkbenchMementoReader reader);

	WindowBuilder createWindowBuilder(WindowReader windowReader);

	PerspectiveBuilder createPerspectiveBuilder(PerspectiveReader perspReader,
			MPerspectiveStack perspStack, MWindow window);

	PerspectiveBuilder createPerspectiveBuilder(PerspectiveReader perspReader);

}
