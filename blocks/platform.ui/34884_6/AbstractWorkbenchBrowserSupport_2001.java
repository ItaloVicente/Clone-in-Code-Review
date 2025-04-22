package org.eclipse.ui.browser;

public abstract class AbstractWebBrowser implements IWebBrowser {
	private String id;

	public AbstractWebBrowser(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public boolean close() {
		return false;
	}
}
