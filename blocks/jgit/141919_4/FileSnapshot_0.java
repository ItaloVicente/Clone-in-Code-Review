	static class ModificationReason {
		final boolean sizeChanged

		ModificationReason(boolean sizeChanged
				boolean lastModifiedChanged) {
			this.sizeChanged = sizeChanged;
			this.fileKeyChanged = fileKeyChanged;
			this.lastModifiedChanged = lastModifiedChanged;
		}
	}

