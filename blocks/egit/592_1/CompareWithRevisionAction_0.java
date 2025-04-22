		IHistoryView view = TeamUI.showHistoryFor(TeamUIPlugin.getActivePage(), getSelectedResources()[0], null);
		IHistoryPage page = view.getHistoryPage();
		if (page instanceof GitHistoryPage){
			GitHistoryPage gitHistoryPage = (GitHistoryPage) page;
			gitHistoryPage.setCompareMode(true);
		}
