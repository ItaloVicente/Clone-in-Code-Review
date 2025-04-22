        final Activity castedObject = (Activity) object;

        if (!Util.equals(activityRequirementBindings,
                castedObject.activityRequirementBindings)) {
            return false;
        }

        if (!Util.equals(activityPatternBindings,
                castedObject.activityPatternBindings)) {
            return false;
        }

        if (!Util.equals(defined, castedObject.defined)) {
            return false;
        }

        if (!Util.equals(enabled, castedObject.enabled)) {
            return false;
        }
