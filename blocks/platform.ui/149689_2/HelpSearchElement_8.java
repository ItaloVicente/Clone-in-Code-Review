package org.eclipse.ui.internal.quickaccess.providers;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.quickaccess.QuickAccessMessages;
import org.eclipse.ui.quickaccess.QuickAccessElement;

public class HelpSearchElement extends QuickAccessElement {

	private static final String SEARCH_IN_HELP_ID = "search.in.help"; //$NON-NLS-1$

	private String searchText;

	public HelpSearchElement(String filter) {
		this.searchText = filter;
	}

	@Override
	public String getLabel() {
		return NLS.bind(QuickAccessMessages.QuickAccessContents_SearchInHelpLabel, searchText);
	}

	@Override
	public String getId() {
		return SEARCH_IN_HELP_ID;
	}

	@Override
	public void execute() {
		PlatformUI.getWorkbench().getHelpSystem().search(searchText);
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_HELP_SEARCH);
	}

}
