		return Objects.hash(Long.valueOf(lastModified)
				fileKey);
	}

	boolean wasSizeChanged() {
		return sizeChanged;
	}

	boolean wasFileKeyChanged() {
		return fileKeyChanged;
	}

	boolean wasLastModifiedChanged() {
		return lastModifiedChanged;
	}

	boolean wasLastModifiedRacilyClean() {
		return wasRacyClean;
