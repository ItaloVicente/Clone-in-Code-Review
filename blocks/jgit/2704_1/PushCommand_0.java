	public PushCommand setPushAll() {
		refSpecs.add(Transport.REFSPEC_PUSH_ALL);
		return this;
	}

	public PushCommand setPushTags() {
		refSpecs.add(Transport.REFSPEC_TAGS);
		return this;
	}

