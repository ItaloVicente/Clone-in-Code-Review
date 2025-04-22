
	private void removeHiddenByUserTags(MToolBar toolbarModel) {
		MWindow mWindow = modelService.getTopLevelWindowFor(toolbarModel);
		List<MToolBar> toolBars = modelService.findElements(mWindow, null,
				MToolBar.class, null);
		for (MToolBar mToolBar : toolBars) {
			mToolBar.getTags().remove(HIDDEN_BY_USER);
		}
	}

