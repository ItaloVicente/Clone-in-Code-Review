		TemporaryBuffer buf;
		if (inCore) {
			buf = new TemporaryBuffer.Heap(inCoreLimit);
		} else {
			buf = new TemporaryBuffer.LocalFile(
					db != null ? nonNullRepo().getDirectory() : null
		}
