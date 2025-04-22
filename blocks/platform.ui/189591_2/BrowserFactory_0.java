package org.eclipse.jface.widgets;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;

public final class BrowserFactory extends AbstractCompositeFactory<BrowserFactory, Browser> {

	private BrowserFactory(int style) {
		super(BrowserFactory.class, (Composite parent) -> new Browser(parent, style));
	}

	public static BrowserFactory newBrowser(int style) {
		return new BrowserFactory(style);
	}

	public BrowserFactory html(String html) {
		addProperty(g -> g.setText(html));
		return this;
	}

	public BrowserFactory html(String html, boolean trusted) {
		addProperty(g -> g.setText(html, trusted));
		return this;
	}

	public BrowserFactory url(String url) {
		addProperty(g -> g.setUrl(url));
		return this;
	}

	public BrowserFactory url(String url, String postData, String[] headers) {
		addProperty(g -> g.setUrl(url, postData, headers));
		return this;
	}

	public BrowserFactory disableJS() {
		addProperty(g -> g.setJavascriptEnabled(false));
		return this;
	}
}
