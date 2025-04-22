		sizeChanged = isSizeChanged(currSize);
		if (sizeChanged) {
			return true;
		}
		fileKeyChanged = isFileKeyChanged(currFileKey);
		if (fileKeyChanged) {
			return true;
		}
		lastModifiedChanged = isModified(currLastModified);
		if (lastModifiedChanged) {
			return true;
		}
		return false;
