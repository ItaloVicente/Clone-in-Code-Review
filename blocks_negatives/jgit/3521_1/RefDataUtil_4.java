	public static final RefData NONE = RefData.newBuilder().build();

	static RefData symbolic(String target) {
		RefData.Builder b = RefData.newBuilder();
		b.setSymref(target);
		return b.build();
	}

	static RefData id(AnyObjectId id) {
		RefData.Builder b = RefData.newBuilder();
		b.setTarget(toRefData(id));
		return b.build();
	}

	static RefData fromRef(Ref ref) {
		if (ref.isSymbolic())
			return symbolic(ref.getTarget().getName());

		if (ref.getObjectId() == null)
			return NONE;

		RefData.Builder b = RefData.newBuilder();
		b.setTarget(toRefData(ref.getObjectId()));
		if (ref.isPeeled()) {
			b.setIsPeeled(true);
			if (ref.getPeeledObjectId() != null)
				b.setPeeled(toRefData(ref.getPeeledObjectId()));
		}
		return b.build();
	}

	static RefData peeled(ObjectId targetId, ObjectId peeledId) {
		RefData.Builder b = RefData.newBuilder();
		b.setTarget(toRefData(targetId));
		b.setIsPeeled(true);
		if (peeledId != null)
			b.setPeeled(toRefData(peeledId));
		return b.build();
	}

	private static RefData.Id toRefData(AnyObjectId id) {
		RefData.Id.Builder r = RefData.Id.newBuilder();
		r.setObjectName(id.name());
		if (id instanceof IdWithChunk)
			r.setChunkKey(((IdWithChunk) id).getChunkKey().asString());
		return r.build();
	}
