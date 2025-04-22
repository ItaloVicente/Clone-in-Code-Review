		if (watcher != null) {
			try {
				watcher.close();
			} catch (IOException e) {
				WebBrowserUIPlugin.logError(e.getMessage(), e);
			}
		}
		watcher = null;

