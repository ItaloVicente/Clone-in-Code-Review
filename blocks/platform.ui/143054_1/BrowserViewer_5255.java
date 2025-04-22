					if (!event.top)
						return;
					if (combo != null) {
						if (!"about:blank".equals(event.location)) { //$NON-NLS-1$
							combo.setText(event.location);
							addToHistory(event.location);
							updateHistory();
						}// else
					}
					if (showToolbar) {
						File temp = getFile(browser.getUrl());
						if (temp != null && temp.exists()) {
							autoRefresh.setEnabled(true);
							if (autoRefresh.getSelection()) {
								fileChangedWatchService(temp);
							}
						} else {
							autoRefresh.setSelection(false);
							toggleAutoRefresh();
							autoRefresh.setEnabled(false);
						}
					}
				}
			});
		}
