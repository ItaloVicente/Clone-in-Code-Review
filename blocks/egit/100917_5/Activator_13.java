					if (!repo.isBare()) {
						ListenerHandle handle = null;
						try {
							handle = repo.getListenerList()
									.addIndexChangedListener(listener);
							repo.scanForRepoChanges();
						} finally {
							if (handle != null) {
								handle.remove();
							}
						}
					}
