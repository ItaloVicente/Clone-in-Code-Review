		AtomicFileCreation ret;
		try {
			ret = getAtomicFileCreationSupportOption(
					GlobalConfigCache.getInstance().getUserConfig());
		} catch (IOException | ConfigInvalidException e) {
			ret = AtomicFileCreation.UNDEFINED;
		}
