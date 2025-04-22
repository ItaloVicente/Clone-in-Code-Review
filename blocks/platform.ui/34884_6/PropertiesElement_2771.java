
package org.eclipse.ui.internal.quickaccess;

import java.util.List;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;

class PreviousPicksProvider extends QuickAccessProvider {

	private List<QuickAccessElement> previousPicksList;

	PreviousPicksProvider(List<QuickAccessElement> previousPicksList) {
		this.previousPicksList = previousPicksList;
	}

	@Override
	public QuickAccessElement getElementForId(String id) {
		return null;
	}

	@Override
	public QuickAccessElement[] getElements() {
		return previousPicksList.toArray(new QuickAccessElement[previousPicksList.size()]);
	}

	@Override
	public QuickAccessElement[] getElementsSorted() {
		return getElements();
	}

	@Override
	public String getId() {
		return "org.eclipse.ui.previousPicks"; //$NON-NLS-1$
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_OBJ_NODE);
	}

	@Override
	public String getName() {
		return QuickAccessMessages.QuickAccess_Previous;
	}

	@Override
	protected void doReset() {
	}

	@Override
	public boolean isAlwaysPresent() {
		return true;
	}
}
