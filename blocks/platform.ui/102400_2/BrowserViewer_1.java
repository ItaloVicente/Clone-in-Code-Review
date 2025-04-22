	private void autoRefresh() {
		if (autoRefresh.getSelection()) {
			fileChangedWatchService();
		} else {
			if (watcher != null) {
				try {
					watcher.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

