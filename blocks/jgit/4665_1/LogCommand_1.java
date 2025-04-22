	public LogCommand all() throws IOException {
		Map<String
				Constants.R_REFS);
		for (Ref ref : refs.values()) {
			ObjectId objectId = ref.getPeeledObjectId();
			if (objectId == null)
				objectId = ref.getObjectId();
			add(objectId);
		}
		return this;
	}

