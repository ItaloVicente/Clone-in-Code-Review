            }
        }

        this.activityRequirementBindingsByActivityId = activityRequirementBindingsByActivityId;
        this.activityDefinitionsById = activityDefinitionsById;
        this.activityPatternBindingsByActivityId = activityPatternBindingsByActivityId;
        this.categoryActivityBindingsByCategoryId = categoryActivityBindingsByCategoryId;
        this.categoryDefinitionsById = categoryDefinitionsById;
        boolean definedActivityIdsChanged = false;
        Set definedActivityIds = new HashSet(activityDefinitionsById.keySet());

        Set previouslyDefinedActivityIds = null;
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

        Set enabledActivityIds = new HashSet(this.enabledActivityIds);
        getRequiredActivityIds(this.enabledActivityIds, enabledActivityIds);
        boolean enabledActivityIdsChanged = false;

        Set previouslyEnabledActivityIds = null;
        if (!this.enabledActivityIds.equals(enabledActivityIds)) {
            previouslyEnabledActivityIds = this.enabledActivityIds;
            this.enabledActivityIds = enabledActivityIds;
            enabledActivityIdsChanged = true;
        }

        Map activityEventsByActivityId = updateActivities(activitiesById
                .keySet());

        Map categoryEventsByCategoryId = updateCategories(categoriesById
                .keySet());

        Map identifierEventsByIdentifierId = updateIdentifiers(identifiersById
                .keySet());

        if (definedActivityIdsChanged || definedCategoryIdsChanged
                || enabledActivityIdsChanged) {
			fireActivityManagerChanged(new ActivityManagerEvent(this,
                    definedActivityIdsChanged, definedCategoryIdsChanged,
                    enabledActivityIdsChanged, previouslyDefinedActivityIds,
                    previouslyDefinedCategoryIds, previouslyEnabledActivityIds));
		}

        if (activityEventsByActivityId != null) {
