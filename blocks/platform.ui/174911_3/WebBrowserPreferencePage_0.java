	protected void addDefaults() {
		internal.setSelection(WebBrowserPreference
				.isDefaultUseInternalBrowser());
		external.setSelection(!WebBrowserPreference.
				isDefaultUseInternalBrowser());

		BrowserManager.getInstance().currentBrowser = null;
		BrowserManager.getInstance().addDefaultBrowsers();
		tableViewer.refresh();

		checkedBrowser = BrowserManager.getInstance().getCurrentWebBrowser();
		if (checkedBrowser != null)
			tableViewer.setChecked(checkedBrowser, true);

		super.updateApplyButton();
	}

