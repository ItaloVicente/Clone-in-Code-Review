	public boolean equals(Object object) {
		if (!(object instanceof ActivityRequirementBindingDefinition)) {
			return false;
		}

		final ActivityRequirementBindingDefinition castedObject = (ActivityRequirementBindingDefinition) object;
		if (!Util.equals(requiredActivityId, castedObject.requiredActivityId)) {
			return false;
		}
