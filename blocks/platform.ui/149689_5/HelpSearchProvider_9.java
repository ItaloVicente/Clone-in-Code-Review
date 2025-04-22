package org.eclipse.ui.internal.quickaccess.providers;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.internal.quickaccess.QuickAccessEntry;
import org.eclipse.ui.internal.quickaccess.QuickAccessMessages;
import org.eclipse.ui.internal.quickaccess.QuickAccessProvider;
import org.eclipse.ui.quickaccess.QuickAccessElement;

public class HelpSearchProvider extends QuickAccessProvider {
	public static final int MIN_SEARCH_LENGTH = 3;

	@Override
	public String getName() {
		return QuickAccessMessages.QuickAccessContents_HelpCategory;
	}

	@Override
	public String getId() {
		return "search.help"; //$NON-NLS-1$
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public QuickAccessElement[] getElements() {
		return new QuickAccessElement[0];
	}

	@Override
	public QuickAccessElement[] getElements(String filter, IProgressMonitor monitor) {
		return new QuickAccessElement[] { new HelpSearchElement(filter) };
	}

	@Override
	public QuickAccessElement findElement(String id, String filterText) {
		return null;
	}

	@Override
	protected void doReset() {
	}

	public QuickAccessEntry makeHelpSearchEntry(String text) {
		if (text.length() >= MIN_SEARCH_LENGTH) {
			QuickAccessElement searchHelpElement = getElements(text, null)[0];
			return new QuickAccessEntry(searchHelpElement, new HelpSearchProvider(), new int[][] {},
					new int[][] {}, QuickAccessEntry.MATCH_PERFECT);
		}
		return null;
	}

}
