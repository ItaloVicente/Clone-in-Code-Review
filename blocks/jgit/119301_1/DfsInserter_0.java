	@Nullable
	private TemporaryBuffer.Heap maybeGetTemporaryBuffer(
			List<PackedObjectInfo> list) {
		if (list.size() <= 58000) {
			return new TemporaryBuffer.Heap(2 << 20);
		}
		return null;
	}

