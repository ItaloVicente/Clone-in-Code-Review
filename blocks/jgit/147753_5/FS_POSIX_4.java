			try {
				ret = getAtomicFileCreationSupportOption(
						SystemReader.getInstance().getSystemConfig());
			} catch (IOException | ConfigInvalidException e) {
				ret = AtomicFileCreation.UNDEFINED;
			}
