
package org.eclipse.ui.internal.e4.migration;

public interface IModelBuilderFactory {

	ApplicationBuilder createApplicationBuilder(WorkbenchMementoReader reader);

	WindowBuilder createWindowBuilder(WindowReader windowReader);

	PerspectiveBuilder createPerspectiveBuilder(PerspectiveReader perspReader);

}
