		if (adaptableObject instanceof IHistoryView
				&& IShowInSource.class == adapterType) {
			IHistoryView historyView = (IHistoryView) adaptableObject;
			if (historyView.getHistoryPage() instanceof GitHistoryPage) {
				GitHistoryPage page = (GitHistoryPage) historyView.getHistoryPage();
				return page;
			}
		}

