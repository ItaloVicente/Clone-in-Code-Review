	void setCopyFromPack(PackedObjectLoader loader) {
		this.copyFromPack = loader.pack;
		this.copyOffset = loader.objectOffset;
	}

	void clearSourcePack() {
		copyFromPack = null;
