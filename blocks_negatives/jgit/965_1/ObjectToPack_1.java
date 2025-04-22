	boolean isCopyable() {
		return copyFromPack != null;
	}

	PackedObjectLoader getCopyLoader(WindowCursor curs) throws IOException {
		return copyFromPack.resolveBase(curs, copyOffset);
	}

	void setCopyFromPack(PackedObjectLoader loader) {
		this.copyFromPack = loader.pack;
		this.copyOffset = loader.objectOffset;
	}

	void clearSourcePack() {
		copyFromPack = null;
	}

