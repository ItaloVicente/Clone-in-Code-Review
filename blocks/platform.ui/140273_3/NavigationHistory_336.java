					}

					if (!isEntryDisposed && page.getActiveEditor() == null && activeEntry < history.size())
						activeEntry++;

					updateActions();
				}
			}
		});
	}

	private Display getDisplay() {
		return page.getWorkbenchWindow().getShell().getDisplay();
	}

	private boolean isPerTabHistoryEnabled() {
