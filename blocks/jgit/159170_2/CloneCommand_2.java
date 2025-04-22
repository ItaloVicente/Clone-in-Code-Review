	public CloneCommand setTagOption(TagOpt tagOption) {
		this.tagOption = tagOption;
		return this;
	}

	public CloneCommand setNoTags() {
		this.tagOption = TagOpt.NO_TAGS;
		return this;
	}

