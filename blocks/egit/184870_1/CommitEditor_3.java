			visibilityListener.runWhenVisible(() -> {
				runRefresh.set(true);
				if (getActivePageInstance() == commitPage) {
					refreshCommitPage();
				}
			});
		}
	}

	private void refreshCommitPage() {
		if (!runRefresh.getAndSet(false) || getContainer().isDisposed()) {
			return;
		}
		UIJob job = new UIJob("Refreshing editor") { //$NON-NLS-1$
