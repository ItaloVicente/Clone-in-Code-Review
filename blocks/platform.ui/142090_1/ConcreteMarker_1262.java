		return description;
	}

	public CollationKey getDescriptionKey() {
		if (descriptionKey == null) {
			descriptionKey = Collator.getInstance()
					.getCollationKey(description);
		}

		return descriptionKey;
	}

	public String getResourceName() {
		return resourceName;
	}

	public CollationKey getResourceNameKey() {
		if (resourceNameKey == null) {
			resourceNameKey = Collator.getInstance().getCollationKey(
					resourceName);
		}
		return resourceNameKey;
	}

	public int getLine() {
		return line;
	}

	public String getFolder() {
		return inFolder;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public long getId() {
		return id;
	}

	public IMarker getMarker() {
		return marker;
	}

	@Override
