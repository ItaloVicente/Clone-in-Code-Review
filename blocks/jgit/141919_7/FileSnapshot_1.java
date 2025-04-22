		r = new ModificationReason(isSizeChanged(currSize)
				isFileKeyChanged(currFileKey)
		return r.sizeChanged || r.fileKeyChanged || r.lastModifiedChanged;
	}

	ModificationReason getModificationReason() {
		return r;
