		if (browser!=null) {
			browser.setFocus();
			updateHistory();
			return true;
		}
		return super.setFocus();
	}

	protected void updateHistory() {
		if (combo == null || combo.isDisposed())
			return;

		String temp = combo.getText();
		if (history == null)
			history = WebBrowserPreference.getInternalWebBrowserHistory();

		String[] historyList = new String[history.size()];
		history.toArray(historyList);
		combo.setItems(historyList);

		combo.setText(temp);
	}

	public IBrowserViewerContainer getContainer() {
		return container;
	}

	public void setContainer(IBrowserViewerContainer container) {
		if (container==null && this.container!=null) {
			IStatusLineManager manager = this.container.getActionBars().getStatusLineManager();
			if (manager!=null)
				manager.getProgressMonitor().done();
		}
		this.container = container;
	}

	protected File file;
	protected long timestamp;
	protected Thread fileListenerThread;
	protected LocationListener locationListener2;
	protected Object syncObject = new Object();

	protected void addSynchronizationListener() {
