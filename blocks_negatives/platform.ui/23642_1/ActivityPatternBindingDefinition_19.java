        final ActivityPatternBindingDefinition castedObject = (ActivityPatternBindingDefinition) object;
        if (!Util.equals(activityId, castedObject.activityId)) {
            return false;
        }

        if (!Util.equals(pattern, castedObject.pattern)) {
            return false;
        }
        
        if (!Util.equals(isEqualityPattern, castedObject.isEqualityPattern)) {
            return false;
        }
        
        return Util.equals(sourceId, castedObject.sourceId);
    }

    public String getActivityId() {
        return activityId;
    }

    public String getPattern() {
        return pattern;
    }

    public String getSourceId() {
        return sourceId;
    }
    
    public boolean isEqualityPattern() {
    	return isEqualityPattern;
    }

    public int hashCode() {
        if (hashCode == HASH_INITIAL) {
            hashCode = hashCode * HASH_FACTOR + Util.hashCode(activityId);
            hashCode = hashCode * HASH_FACTOR + Util.hashCode(pattern);
            hashCode = hashCode * HASH_FACTOR + Util.hashCode(sourceId);
            if (hashCode == HASH_INITIAL) {
