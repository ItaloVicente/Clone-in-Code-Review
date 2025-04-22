		List<String> configuredKeys = getConfiguredKeys();

		boolean effectiveMode = false;

		ActionContributionItem item = (ActionContributionItem) myPage.getSite()
				.getActionBars().getToolBarManager().find(
						modeToggleAction.getId());
		if (item != null) {
			effectiveMode = ((Action) item.getAction()).isChecked();
		}

		if (effectiveMode) {
			for (String key : configuredKeys) {

				for (String sub : getSubSections(effectiveConfig, key)) {
					PropertyDescriptor desc = new PropertyDescriptor(
							EFFECTIVE_ID_PREFIX + sub, sub);

					desc
							.setCategory(UIText.RepositoryPropertySource_EffectiveConfigurationCategory);
					resultList.add(desc);
				}

