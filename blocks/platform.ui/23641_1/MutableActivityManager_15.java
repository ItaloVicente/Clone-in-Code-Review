			}
		}

		this.activityRequirementBindingsByActivityId = activityRequirementBindingsByActivityId;
		this.activityDefinitionsById = activityDefinitionsById;
		this.activityPatternBindingsByActivityId = activityPatternBindingsByActivityId;
		this.categoryActivityBindingsByCategoryId = categoryActivityBindingsByCategoryId;
		this.categoryDefinitionsById = categoryDefinitionsById;
		boolean definedActivityIdsChanged = false;
		Set<String> definedActivityIds = new HashSet<String>(activityDefinitionsById.keySet());

		Set<String> previouslyDefinedActivityIds = null;
		if (!definedActivityIds.equals(this.definedActivityIds)) {
			previouslyDefinedActivityIds = this.definedActivityIds;
			this.definedActivityIds = definedActivityIds;
			definedActivityIdsChanged = true;
		}

		boolean definedCategoryIdsChanged = false;
		Set definedCategoryIds = new HashSet(categoryDefinitionsById.keySet());

		Set previouslyDefinedCategoryIds = null;
		if (!definedCategoryIds.equals(this.definedCategoryIds)) {
			previouslyDefinedCategoryIds = this.definedCategoryIds;
			this.definedCategoryIds = definedCategoryIds;
			definedCategoryIdsChanged = true;
		}

		Set<String> enabledActivityIds = new HashSet<String>(this.enabledActivityIds);
		getRequiredActivityIds(this.enabledActivityIds, enabledActivityIds);
		boolean enabledActivityIdsChanged = false;

		Set<String> previouslyEnabledActivityIds = null;
		if (!this.enabledActivityIds.equals(enabledActivityIds)) {
			previouslyEnabledActivityIds = this.enabledActivityIds;
			this.enabledActivityIds = enabledActivityIds;
			enabledActivityIdsChanged = true;
		}

		Map<String, ActivityEvent> activityEventsByActivityId = updateActivities(activitiesById
				.keySet());

		Map<String, CategoryEvent> categoryEventsByCategoryId = updateCategories(categoriesById
				.keySet());

		Map<String, IdentifierEvent> identifierEventsByIdentifierId = updateIdentifiers(identifiersById
				.keySet());

		if (definedActivityIdsChanged || definedCategoryIdsChanged || enabledActivityIdsChanged) {
			fireActivityManagerChanged(new ActivityManagerEvent(this, definedActivityIdsChanged,
					definedCategoryIdsChanged, enabledActivityIdsChanged,
					previouslyDefinedActivityIds, previouslyDefinedCategoryIds,
					previouslyEnabledActivityIds));
		}

		if (activityEventsByActivityId != null) {
