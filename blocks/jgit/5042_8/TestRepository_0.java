	public ObjectId lightweightTag(String name
		if (!name.startsWith(Constants.R_TAGS)) {
			name = Constants.R_TAGS + name;
		}
		return update(name
	}

