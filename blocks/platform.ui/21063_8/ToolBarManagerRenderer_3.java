
	private void removeHiddenByUserTags() {
		List<MToolBar> toolBars = modelService.findElements(
				application, null, MToolBar.class, null);
		for (MToolBar mToolBar : toolBars) {
			mToolBar.getTags().remove(HIDDEN_BY_USER);
		}
	}

