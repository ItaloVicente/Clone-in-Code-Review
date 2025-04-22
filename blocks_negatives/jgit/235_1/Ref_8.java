	public Storage getStorage() {
		return storage;
	}

	public String toString() {
		String o = "";
		if (!origName.equals(name))
			o = "(" + origName + ")";
		return "Ref[" + o + name + "=" + ObjectId.toString(getObjectId()) + "]";
	}

	void setPeeledObjectId(final ObjectId id) {
		peeledObjectId = id;
	}
