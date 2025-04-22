		if (!data.hasTarget())
			return new Unpeeled(LOOSE

		ObjectId oId = IdWithChunk.create(data.getTarget());
		if (data.getIsPeeled() && data.hasPeeled()) {
			ObjectId pId = IdWithChunk.create(data.getPeeled());
