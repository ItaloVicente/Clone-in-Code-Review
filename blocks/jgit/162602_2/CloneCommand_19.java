	public CloneCommand setTagOption(TagOpt tagOption) {
		this.tagOption = tagOption;
		return this;
	}

	public CloneCommand setNoTags() {
		return setTagOption(TagOpt.NO_TAGS);
	}

