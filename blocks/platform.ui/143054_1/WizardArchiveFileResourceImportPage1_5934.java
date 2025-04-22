		if (ArchiveFileManipulations.isTarFile(sourceNameField.getText())) {
			return ensureTarSourceIsValid();
		}
		return ensureZipSourceIsValid();
	}

	@Override
