	private void createCompareModeAction() {
		final IToolBarManager barManager = getSite().getActionBars()
				.getToolBarManager();
		compareModeAction = new Action(UIText.GitHistoryPage_compareMode,
				UIIcons.ELCL16_COMPARE_VIEW) {
			public void run() {
				compareMode = !compareMode;
				setChecked(compareMode);
			}
		};
		compareModeAction.setChecked(compareMode);
		compareModeAction.setToolTipText(UIText.GitHistoryPage_compareMode);
		barManager.add(compareModeAction);
	}

	public void setCompareMode(boolean compareMode) {
		if (compareModeAction!=null) {
			this.compareMode = !compareMode;
			compareModeAction.run();
		}
	}

