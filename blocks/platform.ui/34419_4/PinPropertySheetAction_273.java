
package org.eclipse.ui.views.properties;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.IShowInSource;
import org.eclipse.ui.part.ShowInContext;

public class NewPropertySheetHandler extends AbstractHandler {

	public static final String ID = "org.eclipse.ui.views.properties.NewPropertySheetCommand"; //$NON-NLS-1$

	private static final boolean PIN_NEW_PROPERTY_VIEW = Boolean.valueOf(System.getProperty("org.eclipse.ui.views.properties.pinNewPV", Boolean.FALSE.toString())).booleanValue(); //$NON-NLS-1$

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPart activePart = HandlerUtil.getActivePartChecked(event);

		PropertyShowInContext context = getShowInContext(event);
		try {
			PropertySheet sheet = findPropertySheet(event, context);
			sheet.show(context);
			if (activePart instanceof PropertySheet) {
				PropertySheet parent = (PropertySheet) activePart;
				parent.setPinned(true);
			} else if(!sheet.isPinned()) {
				sheet.setPinned(PIN_NEW_PROPERTY_VIEW);
			}
		} catch (PartInitException e) {
			throw new ExecutionException("Part could not be initialized", e); //$NON-NLS-1$
		}
		return null;
	}

	protected PropertyShowInContext getShowInContext(ExecutionEvent event)
			throws ExecutionException {
		IWorkbenchPart activePart = HandlerUtil.getActivePartChecked(event);
		if (activePart instanceof PropertySheet) {
			PropertySheet sheet = (PropertySheet) activePart;
			return (PropertyShowInContext) sheet.getShowInContext();
		}
		IShowInSource adapter = activePart
				.getAdapter(IShowInSource.class);
		if (adapter != null) {
			ShowInContext showInContext = adapter.getShowInContext();
			return new PropertyShowInContext(activePart, showInContext);
		}
		return new PropertyShowInContext(activePart, HandlerUtil
				.getShowInSelection(event));
	}

	protected PropertySheet findPropertySheet(ExecutionEvent event,
			PropertyShowInContext context) throws PartInitException,
			ExecutionException {
		IWorkbenchPage page = HandlerUtil.getActivePartChecked(event).getSite()
				.getPage();
		String secondaryId = null;
		if (HandlerUtil.getActivePart(event) instanceof PropertySheet) {
			secondaryId = Long.toString(System.currentTimeMillis());
		} else {
			IViewReference[] refs = page.getViewReferences();
			for (int i = 0; i < refs.length; i++) {
				IViewReference viewReference = refs[i];
				if (IPageLayout.ID_PROP_SHEET.equals(viewReference.getId())) {
					secondaryId = Long.toString(System.currentTimeMillis());
					PropertySheet sheet = (PropertySheet) viewReference
							.getView(true);
					if (!sheet.isPinned()
							|| (sheet.isPinned() && sheet.getShowInContext()
									.equals(context))) {
						secondaryId = sheet.getViewSite().getSecondaryId();
						break;
					}
				}
			}
		}
		return (PropertySheet) page.showView(IPageLayout.ID_PROP_SHEET,
				secondaryId, IWorkbenchPage.VIEW_ACTIVATE);
	}
}
