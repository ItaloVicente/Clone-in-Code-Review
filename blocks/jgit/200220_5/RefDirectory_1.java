		return refreshPackedRefs(curList);
	}

	PackedRefList refreshPackedRefs() throws IOException {
		return refreshPackedRefs(packedRefs.get());
	}

	private PackedRefList refreshPackedRefs(PackedRefList curList)
			throws IOException {
