		} else {

			String categoryString = UIText.RepositoryPropertySource_GlobalConfigurationCategory
					+ userHomeConfig.getFile().getAbsolutePath();
			for (String key : configuredKeys) {

					continue;

				for (String sub : getSubSections(effectiveConfig, key)) {
					TextPropertyDescriptor desc = new TextPropertyDescriptor(
							USER_ID_PREFIX + sub, sub);
					desc.setCategory(categoryString);
					resultList.add(desc);
				}
