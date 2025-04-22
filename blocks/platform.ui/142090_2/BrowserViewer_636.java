		try {
			this.browser = new Browser(this, SWT.NONE);
		}
		catch (SWTError e) {
			if (e.code!=SWT.ERROR_NO_HANDLES) {
				WebBrowserUtil.openError(Messages.errorCouldNotLaunchInternalWebBrowser);
				return;
			}
			text = new BrowserText(this, this, e);
		}

		if (showURLbar)
			updateHistory();
		if (showToolbar)
			updateBackNextBusy();

		if (browser!=null) {
			browser.setLayoutData(new GridData(GridData.FILL_BOTH));
			PlatformUI.getWorkbench().getHelpSystem().setHelp(browser,
					ContextIds.WEB_BROWSER);
		}
		else
			text.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));

		addBrowserListeners();
	}

	public Browser getBrowser() {
		return browser;
	}

	public void home() {
		browser.setText(""); //$NON-NLS-1$
	}

	public void setURL(String url) {
		setURL(url, true);
	}

	protected void updateBackNextBusy() {
		if (!back.isDisposed()) {
			back.setEnabled(isBackEnabled());
		}
		if (!forward.isDisposed()) {
			forward.setEnabled(isForwardEnabled());
		}
		if (!busy.isDisposed()) {
			busy.setBusy(loading);
		}

		if (backNextListener != null)
			backNextListener.updateBackNextBusy();
	}

	protected void updateLocation() {
		if (locationListener != null)
			locationListener.historyChanged(null);

		if (locationListener != null)
			locationListener.locationChanged(null);
	}

	private void addBrowserListeners() {
		if (browser==null) return;
