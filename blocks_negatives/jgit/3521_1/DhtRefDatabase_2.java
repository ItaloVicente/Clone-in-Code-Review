		ObjectId oId = IdWithChunk.create(data.getTarget());
		if (data.getIsPeeled() && data.hasPeeled()) {
			ObjectId pId = IdWithChunk.create(data.getPeeled());
			return new PeeledTag(LOOSE, name, oId, pId);
		}
		if (data.getIsPeeled())
			return new PeeledNonTag(LOOSE, name, oId);
		return new Unpeeled(LOOSE, name, oId);
