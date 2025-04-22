			try {
				ret = getAtomicFileCreationSupportOption(
						GlobalConfigCache.getInstance().getSystemConfig());
			} catch (IOException | ConfigInvalidException e) {
				ret = AtomicFileCreation.UNDEFINED;
			}
