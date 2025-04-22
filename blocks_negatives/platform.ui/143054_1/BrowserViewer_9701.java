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

    /**
     * Returns the underlying SWT browser widget.
     *
     * @return the underlying browser
     */
    public Browser getBrowser() {
        return browser;
    }

    /**
     * Navigate to the home URL.
     */
    public void home() {
    }

    /**
     * Loads a URL.
     *
     * @param url
     *            the URL to be loaded
     * @return true if the operation was successful and false otherwise.
     * @exception IllegalArgumentException
     *                <ul>
     *                <li>ERROR_NULL_ARGUMENT - if the url is null</li>
     *                </ul>
     * @exception SWTException
     *                <ul>
     *                <li>ERROR_THREAD_INVALID_ACCESS when called from the
     *                wrong thread</li>
     *                <li>ERROR_WIDGET_DISPOSED when the widget has been
     *                disposed</li>
     *                </ul>
     * @see #getURL()
     */
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

    /**
     *
     */
    private void addBrowserListeners() {
        if (browser==null) return;
