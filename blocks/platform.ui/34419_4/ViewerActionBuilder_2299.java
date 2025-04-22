package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.internal.e4.compatibility.ActionBars;

public class ViewSite extends PartSite implements IViewSite {
    
	public ViewSite(MPart model, IWorkbenchPart part, IWorkbenchPartReference ref,
			IConfigurationElement element) {
		super(model, part, ref, element);
		initializeDefaultServices();
	}

	private void initializeDefaultServices() {
		setActionBars(new ActionBars(((WorkbenchPage) getPage()).getActionBars(), serviceLocator,
				model));
		serviceLocator.registerService(IViewPart.class, (IViewPart) getPart());
	}

	@Override
	public String getSecondaryId() {
		MPart part = getModel();

		int colonIndex = part.getElementId().indexOf(':');
		if (colonIndex == -1 || colonIndex == (part.getElementId().length() - 1))
			return null;

		return part.getElementId().substring(colonIndex + 1);
	}

	@Override
	public void dispose() {
		final IActionBars actionBars = getActionBars();
		if (actionBars instanceof SubActionBars) {
			((SubActionBars) actionBars).dispose();
		}
		super.dispose();
	}
}
