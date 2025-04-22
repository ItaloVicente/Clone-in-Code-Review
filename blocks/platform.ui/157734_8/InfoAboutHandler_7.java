package org.eclipse.e4.ui.internal.dialogs.about;

import org.eclipse.core.runtime.IProduct;
import org.eclipse.e4.ui.dialogs.textbundles.E4DialogMessages;
import org.osgi.framework.Bundle;

public final class UnavailableProduct implements IProduct {

	@Override
	public String getApplication() {
		return "";
	}

	@Override
	public String getName() {
		return E4DialogMessages.AboutDialog_defaultProductName;
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public String getId() {
		return "";
	}

	@Override
	public String getProperty(String key) {
		return "";
	}

	@Override
	public Bundle getDefiningBundle() {
		return null;
	}

}
