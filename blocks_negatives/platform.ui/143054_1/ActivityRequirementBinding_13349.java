        final ActivityRequirementBinding castedObject = (ActivityRequirementBinding) object;
        if (!Util.equals(requiredActivityId, castedObject.requiredActivityId)) {
            return false;
        }

        return Util.equals(activityId, castedObject.activityId);
    }
