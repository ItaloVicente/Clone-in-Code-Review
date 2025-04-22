		compareModeAction = new Action(UIText.GitHistoryPage_compareMode,
				IAction.AS_CHECK_BOX) {
			public void run() {
				compareMode = !compareMode;
				setChecked(compareMode);
				fileViewer.setCompareMode(compareMode);
