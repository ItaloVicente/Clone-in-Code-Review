		String sourceId = sourceIdOverride != null ? sourceIdOverride : memento.getString(TAG_SOURCE_ID);
		return new ActivityRequirementBindingDefinition(childActivityId, parentActivityId, sourceId);
	}

	static String readDefaultEnablement(IMemento memento) {
		return memento.getString(TAG_ID);
	}

	static ActivityDefinition readActivityDefinition(IMemento memento, String sourceIdOverride) {

		String id = memento.getString(TAG_ID);
		if (id == null) {
			log(memento, ACTIVITY_DESC, "missing a unique identifier"); //$NON-NLS-1$
