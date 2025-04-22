
package org.eclipse.ui.menus;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.services.IServiceLocator;

public abstract class AbstractContributionFactory {
	private String location = null;
	private String namespace;

	public AbstractContributionFactory(String location, String namespace) {
		this.location = location;
		this.namespace = namespace;
	}

	public String getLocation() {
		return location;
	}

	public abstract void createContributionItems(IServiceLocator serviceLocator,
			IContributionRoot additions);

	public String getNamespace() {
		return namespace;
	}
}
