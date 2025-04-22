		return compareTo;
	}

	public boolean equals(Object object) {
		if (!(object instanceof CategoryDefinition)) {
			return false;
		}

		final CategoryDefinition castedObject = (CategoryDefinition) object;
		if (!Util.equals(id, castedObject.id)) {
			return false;
		}
