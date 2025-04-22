
	private void removeHiddenByUserTags() {
		List<MToolBar> toolBars = modelService.findElements(
				application, null, MToolBar.class, null);
		for (MToolBar mToolBar : toolBars) {
			mToolBar.getTags().remove(
					IPresentationEngine.HIDDEN_BY_USER);
		}
	}

