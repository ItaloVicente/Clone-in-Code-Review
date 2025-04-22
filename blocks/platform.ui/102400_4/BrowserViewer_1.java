
					File temp = getFile(browser.getUrl());
					if (temp != null && temp.exists()) {
						autoRefresh.setEnabled(true);
						if (autoRefresh.getSelection()) {
							fileChangedWatchService(temp);
						}
					} else {
						autoRefresh.setSelection(false);
						autoRefresh.setEnabled(false);
					}
