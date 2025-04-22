	synchronized public void setEnabledActivityIds(Set enabledActivityIds) {
        enabledActivityIds = new HashSet(enabledActivityIds);
        Set requiredActivityIds = new HashSet(enabledActivityIds);
        getRequiredActivityIds(enabledActivityIds, requiredActivityIds);
        enabledActivityIds = requiredActivityIds;
        Set deltaActivityIds = null;
        boolean activityManagerChanged = false;
        Map activityEventsByActivityId = null;

        Set previouslyEnabledActivityIds = null;
        if (!this.enabledActivityIds.equals(enabledActivityIds)) {
            previouslyEnabledActivityIds = this.enabledActivityIds;
            activityManagerChanged = true;

            Set additions = new HashSet(enabledActivityIds);
            additions.removeAll(previouslyEnabledActivityIds);

            Set removals = new HashSet(previouslyEnabledActivityIds);
            removals.removeAll(enabledActivityIds);

            removeExpressionControlledActivities(additions);
            removeExpressionControlledActivities(removals);

