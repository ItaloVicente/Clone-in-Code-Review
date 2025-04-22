
package org.eclipse.e4.ui.workbench.filter;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;

public interface IMenuElementFilter {

	public boolean filterElement(MMenuElement menuElement, IEclipseContext context);

}
