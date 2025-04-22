            }
        }

        return categoryEventsByCategoryId;
    }

    private CategoryEvent updateCategory(Category category) {
        Set categoryActivityBindings = (Set) categoryActivityBindingsByCategoryId
                .get(category.getId());
        boolean categoryActivityBindingsChanged = category
                .setCategoryActivityBindings(categoryActivityBindings != null ? categoryActivityBindings
                        : Collections.EMPTY_SET);
        CategoryDefinition categoryDefinition = (CategoryDefinition) categoryDefinitionsById
                .get(category.getId());
        boolean definedChanged = category
                .setDefined(categoryDefinition != null);
        boolean nameChanged = category
                .setName(categoryDefinition != null ? categoryDefinition
                        .getName() : null);
        boolean descriptionChanged = category
                .setDescription(categoryDefinition != null ? categoryDefinition
                        .getDescription() : null);

        if (categoryActivityBindingsChanged || definedChanged || nameChanged
                || descriptionChanged) {
			return new CategoryEvent(category, categoryActivityBindingsChanged,
                    definedChanged, descriptionChanged, nameChanged);
		}

        return null;
    }

    private IdentifierEvent updateIdentifier(Identifier identifier) {
        return updateIdentifier(identifier, definedActivityIds);
    }

    private IdentifierEvent updateIdentifier(Identifier identifier, Set changedActivityIds) {
        String id = identifier.getId();
        Set activityIds = new HashSet();

        boolean enabled = false;

        boolean activityIdsChanged = false;

        boolean enabledChanged = false;

        if (enabledActivityIds.size() == definedActivityIds.size()) {
            enabled = true;
            enabledChanged = identifier.setEnabled(enabled);
            identifier.setActivityIds(Collections.EMPTY_SET);
            deferredIdentifiers.add(identifier);
            getUpdateJob().schedule();
            if (enabledChanged) {
				return new IdentifierEvent(identifier, activityIdsChanged,
                        enabledChanged);
