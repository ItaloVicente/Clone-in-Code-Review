		public void activityManagerChanged(ActivityManagerEvent activityManagerEvent) {
			if (activityManagerEvent.haveEnabledActivityIdsChanged()) {
				updateCategoryCheckState();
				fireLabelProviderChanged(new LabelProviderChangedEvent(this));
			}
		}
	}

	private class CategoryContentProvider implements IStructuredContentProvider {
