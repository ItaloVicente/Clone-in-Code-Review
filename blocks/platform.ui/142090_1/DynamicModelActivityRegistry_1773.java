			if (currentCategory.getId().equals(categoryId)) {
				categoryDefinitions.remove(currentCategory);
				categoryDefinitions.add(new CategoryDefinition(categoryId,
						currentCategory.getName(), currentCategory
								.getSourceId(), categoryDescription));
				fireActivityRegistryChanged();
				return;
			}
		}
	}
