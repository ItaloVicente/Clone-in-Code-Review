
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

	public QuickAccessElement getElementForId(String id) {
		return null;
	}

	public QuickAccessElement[] getElements() {
		return previousPicksList.toArray(new QuickAccessElement[previousPicksList.size()]);
	}

	public QuickAccessElement[] getElementsSorted() {
		return getElements();
	}

	public String getId() {
		return "org.eclipse.ui.previousPicks"; //$NON-NLS-1$
	}

	public ImageDescriptor getImageDescriptor() {
		return WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_OBJ_NODE);
	}

	public String getName() {
		return QuickAccessMessages.QuickAccess_Previous;
	}

	protected void doReset() {
	}

	public boolean isAlwaysPresent() {
		return true;
	}
}
