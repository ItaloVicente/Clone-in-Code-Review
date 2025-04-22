
package org.eclipse.e4.ui.workbench.filter;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.menu.MToolBarElement;

public interface IToolElementFilter {

	public boolean filterElement(MToolBarElement toolBarElement, IEclipseContext context);

}
