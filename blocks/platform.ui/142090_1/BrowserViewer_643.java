	public String getURL() {
		if (browser!=null)
			return browser.getUrl();
		return text.getUrl();
	}

	@Override
