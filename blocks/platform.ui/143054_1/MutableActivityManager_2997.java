		}

		return categoryEventsByCategoryId;
	}

	private CategoryEvent updateCategory(Category category) {
		Set<ICategoryActivityBinding> categoryActivityBindings = categoryActivityBindingsByCategoryId
				.get(category.getId());
		boolean categoryActivityBindingsChanged = category.setCategoryActivityBindings(
				categoryActivityBindings != null ? categoryActivityBindings : Collections.emptySet());
		CategoryDefinition categoryDefinition = categoryDefinitionsById.get(category.getId());
		boolean definedChanged = category.setDefined(categoryDefinition != null);
		boolean nameChanged = category.setName(categoryDefinition != null ? categoryDefinition.getName() : null);
		boolean descriptionChanged = category
				.setDescription(categoryDefinition != null ? categoryDefinition.getDescription() : null);

		if (categoryActivityBindingsChanged || definedChanged || nameChanged || descriptionChanged) {
			return new CategoryEvent(category, categoryActivityBindingsChanged, definedChanged, descriptionChanged,
					nameChanged);
		}

		return null;
	}

	private IdentifierEvent updateIdentifier(Identifier identifier) {
		return updateIdentifier(identifier, definedActivityIds);
	}

	private IdentifierEvent updateIdentifier(Identifier identifier, Set<String> changedActivityIds) {
		String id = identifier.getId();
		Set<String> activityIds = new HashSet<>();

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
				return new IdentifierEvent(identifier, activityIdsChanged, enabledChanged);
			}
		} else {
			Set<String> activityIdsToUpdate = new HashSet<>(changedActivityIds);
			if (identifier.getActivityIds() != null) {
				activityIdsToUpdate.addAll(identifier.getActivityIds());
			}
			for (Iterator<String> iterator = activityIdsToUpdate.iterator(); iterator.hasNext();) {
				String activityId = iterator.next();
				Activity activity = (Activity) getActivity(activityId);

				if (activity.isMatch(id)) {
					activityIds.add(activityId);
				}
			}

			activityIdsChanged = identifier.setActivityIds(activityIds);

			if (advisor != null) {
				enabled = advisor.computeEnablement(this, identifier);
			}
			enabledChanged = identifier.setEnabled(enabled);

			if (activityIdsChanged || enabledChanged) {
				return new IdentifierEvent(identifier, activityIdsChanged, enabledChanged);
