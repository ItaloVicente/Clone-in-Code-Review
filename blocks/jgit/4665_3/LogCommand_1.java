	public LogCommand all() throws IOException {
		for (Ref ref : getRepository().getAllRefs().values()) {
			ObjectId objectId = ref.getPeeledObjectId();
			if (objectId == null)
				objectId = ref.getObjectId();
			add(objectId);
		}
		return this;
	}

