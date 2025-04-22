	public boolean forward() {
		if (browser==null)
			return false;
		return browser.forward();
	}

	public boolean back() {
		if (browser==null)
			return false;
		return browser.back();
	}

	public boolean isBackEnabled() {
		if (browser==null)
			return false;
		return browser.isBackEnabled();
	}

	public boolean isForwardEnabled() {
		if (browser==null)
			return false;
		return browser.isForwardEnabled();
	}

	public void stop() {
		if (browser!=null)
			browser.stop();
	}

	private boolean navigate(String url) {
		Trace.trace(Trace.FINER, "Navigate: " + url); //$NON-NLS-1$
		if (url != null && url.equals(getURL())) {
			refresh();
			return true;
		}
		if (browser!=null)
			return browser.setUrl(url, null, new String[] {"Cache-Control: no-cache"}); //$NON-NLS-1$
		return text.setUrl(url);
	}

	public void refresh() {
		if (browser!=null)
			browser.refresh();
		else
			text.refresh();
		try {
			Thread.sleep(50);
		} catch (Exception e) {
		}
	}
