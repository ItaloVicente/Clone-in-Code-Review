	private void initActions() {
		IAction findAction = createFindToolbarAction();
		IAction refreshAction = new Action(
				UIText.GitHistoryPage_RefreshMenuLabel, UIIcons.ELCL16_REFRESH) {
			@Override
			public void run() {
				refresh();
			}
		};
