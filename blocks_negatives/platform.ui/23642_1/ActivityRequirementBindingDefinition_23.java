        final ActivityRequirementBindingDefinition castedObject = (ActivityRequirementBindingDefinition) object;
        if (!Util.equals(requiredActivityId,
                castedObject.requiredActivityId)) {
            return false;
        }
        
        if (!Util.equals(activityId, castedObject.activityId)) {
            return false;
        }
        
        return Util.equals(sourceId, castedObject.sourceId);
    }

    public String getRequiredActivityId() {
        return requiredActivityId;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public int hashCode() {
        if (hashCode == HASH_INITIAL) {
            hashCode = hashCode * HASH_FACTOR
                    + Util.hashCode(requiredActivityId);
            hashCode = hashCode * HASH_FACTOR + Util.hashCode(activityId);
            hashCode = hashCode * HASH_FACTOR + Util.hashCode(sourceId);
            if (hashCode == HASH_INITIAL) {
