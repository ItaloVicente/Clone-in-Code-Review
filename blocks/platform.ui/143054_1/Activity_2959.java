		final Activity castedObject = (Activity) object;
		return Objects.equals(activityRequirementBindings, castedObject.activityRequirementBindings)
				&& Objects.equals(activityPatternBindings, castedObject.activityPatternBindings)
				&& defined == castedObject.defined && enabled == castedObject.enabled
				&& Objects.equals(id, castedObject.id) && Objects.equals(name, castedObject.name);
	}
