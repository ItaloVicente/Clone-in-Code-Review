	public ObjectChecker setSkipList(@Nullable ObjectIdSet objects) {
		skipList = objects;
		return this;
	}

	public ObjectChecker setIgnore(@Nullable Set<ErrorType> ids) {
		errors = EnumSet.allOf(ErrorType.class);
		if (ids != null) {
			errors.removeAll(ids);
		}
		return this;
	}

	public ObjectChecker setIgnore(ErrorType id
		if (ignore) {
			errors.remove(id);
		} else {
			errors.add(id);
		}
		return this;
	}

