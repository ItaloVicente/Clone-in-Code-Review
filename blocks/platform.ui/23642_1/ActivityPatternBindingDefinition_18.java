		return compareTo;
	}

	public boolean equals(Object object) {
		if (!(object instanceof ActivityPatternBindingDefinition)) {
			return false;
		}

		final ActivityPatternBindingDefinition castedObject = (ActivityPatternBindingDefinition) object;
		if (!Util.equals(activityId, castedObject.activityId)) {
			return false;
		}

		if (!Util.equals(pattern, castedObject.pattern)) {
			return false;
		}
