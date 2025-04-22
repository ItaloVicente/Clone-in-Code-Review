package org.eclipse.ui.internal.quickaccess;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.quickaccess.IQuickAccessElement;
import org.eclipse.ui.quickaccess.QuickAccessMatch;

public class ExtensionQuickAccessElement extends QuickAccessElement {
	private IQuickAccessElement element;

	public ExtensionQuickAccessElement(ExtensionQuickAccessProvider provider, IQuickAccessElement element) {
		super(provider);
		this.element = element;
	}

	@Override
	public String getLabel() {
		return getElement().getLabel();
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		ImageDescriptor descriptor = getElement().getImageDescriptor();
		if (descriptor != null)
			return descriptor;
		return getProvider().getImageDescriptor();
	}

	@Override
	public String getId() {
		return getElement().getId();
	}

	@Override
	public void execute() {
		getElement().execute();
	}

	@Override
	public String getSortLabel() {
		return getElement().getSortLabel();
	}

	@Override
	public QuickAccessEntry match(String filter, QuickAccessProvider providerForMatching) {
		if (providerForMatching != getProvider())
			return null;

		QuickAccessMatch match = getElement().match(filter,
				((ExtensionQuickAccessProvider) providerForMatching).getProvider());
		if (match == null)
			return null;

		return new QuickAccessEntry(this, providerForMatching, match.elementMatchRegions, match.providerMatchRegions,
				match.getMatchQuality());
	}

	public IQuickAccessElement getElement() {
		return element;
	}

}
