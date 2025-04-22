		if (adaptableObject instanceof IHistoryView
				&& IShowInSource.class == adapterType) {
			IHistoryView historyView = (IHistoryView) adaptableObject;
			IHistoryPage historyPage = historyView.getHistoryPage();
			if (historyPage instanceof GitHistoryPage)
				return historyPage;
		}

