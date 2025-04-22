		} else if (watcher != null) {
			try {
				watcher.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void setURL(String url, boolean browse) {
		Trace.trace(Trace.FINEST, "setURL: " + url + " " + browse); //$NON-NLS-1$ //$NON-NLS-2$
		if (url == null) {
			home();
			return;
		}

		if ("eclipse".equalsIgnoreCase(url)) //$NON-NLS-1$
			url = "http://www.eclipse.org"; //$NON-NLS-1$
		else if ("wtp".equalsIgnoreCase(url)) //$NON-NLS-1$
			url = "http://www.eclipse.org/webtools/"; //$NON-NLS-1$

		if (browse)
			navigate(url);

		addToHistory(url);
		updateHistory();
	}

	protected void addToHistory(String url) {
		if (history == null)
			history = WebBrowserPreference.getInternalWebBrowserHistory();
		int found = -1;
		int size = history.size();
		for (int i = 0; i < size; i++) {
			String s = history.get(i);
			if (s.equals(url)) {
				found = i;
				break;
			}
		}

		if (found == -1) {
			if (size >= MAX_HISTORY)
				history.remove(size - 1);
			history.add(0, url);
			WebBrowserPreference.setInternalWebBrowserHistory(history);
		} else if (found != 0) {
			history.remove(found);
			history.add(0, url);
			WebBrowserPreference.setInternalWebBrowserHistory(history);
		}
	}

	@Override
