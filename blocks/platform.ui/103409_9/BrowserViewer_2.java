	private void toggleAutoRefresh() {
        File temp = getFile(browser.getUrl());
        if (temp != null && temp.exists() && autoRefresh.getSelection()) {
			refresh();
			fileChangedWatchService(temp);
        } else if (watcher != null) {
            try {
                watcher.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

