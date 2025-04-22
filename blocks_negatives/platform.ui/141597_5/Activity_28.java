
		if (!Util.equals(activityRequirementBindings, castedObject.activityRequirementBindings)) {
			return false;
		}

		if (!Util.equals(activityPatternBindings, castedObject.activityPatternBindings)) {
			return false;
		}

		if (!Util.equals(defined, castedObject.defined)) {
			return false;
		}

		if (!Util.equals(enabled, castedObject.enabled)) {
			return false;
		}

		if (!Util.equals(id, castedObject.id)) {
			return false;
		}

		return Util.equals(name, castedObject.name);
