	void setWarningTextInUIThread(final Job j) {
		graph.getControl().getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (!graph.getControl().isDisposed() && job == j) {
					setWarningText(UIText.GitHistoryPage_ListIncompleteWarningMessage);
				}
			}
		});
	}

