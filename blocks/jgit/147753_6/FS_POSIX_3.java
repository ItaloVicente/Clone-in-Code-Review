		AtomicFileCreation ret;
		try {
			ret = getAtomicFileCreationSupportOption(
					SystemReader.getInstance().getUserConfig());
		} catch (IOException | ConfigInvalidException e) {
			ret = AtomicFileCreation.UNDEFINED;
		}
