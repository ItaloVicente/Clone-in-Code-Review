	private void createCompareModeAction() {
		final IToolBarManager barManager = getSite().getActionBars()
				.getToolBarManager();
		compareModeAction = new Action(UIText.GitHistoryPage_compareMode,
				IAction.AS_CHECK_BOX) {
			public void run() {
				compareMode = !compareMode;
				setChecked(compareMode);
			}
		};
		compareModeAction.setImageDescriptor(UIIcons.ELCL16_COMPARE_VIEW);
		compareModeAction.setChecked(compareMode);
		compareModeAction.setToolTipText(UIText.GitHistoryPage_compareMode);
		barManager.add(compareModeAction);
	}

	public void setCompareMode(boolean compareMode) {
		if (compareModeAction!=null) {
			this.compareMode = compareMode;
			compareModeAction.setChecked(compareMode);
		}
	}

