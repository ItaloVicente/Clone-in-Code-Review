                    if (!event.top)
                        return;
                    if (combo != null) {
                            combo.setText(event.location);
                            addToHistory(event.location);
                            updateHistory();
                    }
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
            });
        }
