        if (definedActivityIdsChanged || definedCategoryIdsChanged
                || enabledActivityIdsChanged) {
			fireActivityManagerChanged(new ActivityManagerEvent(this,
                    definedActivityIdsChanged, definedCategoryIdsChanged,
                    enabledActivityIdsChanged, previouslyDefinedActivityIds,
                    previouslyDefinedCategoryIds, previouslyEnabledActivityIds));
