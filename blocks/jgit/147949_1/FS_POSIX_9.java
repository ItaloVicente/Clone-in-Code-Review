		if (supportsAtomicFileCreation == AtomicFileCreation.UNDEFINED) {
			try {
				StoredConfig config = SystemReader.getInstance().getUserConfig();
				String value = config.getString(CONFIG_CORE_SECTION
						CONFIG_KEY_SUPPORTSATOMICFILECREATION);
				if (value != null) {
					supportsAtomicFileCreation = StringUtils.toBoolean(value)
							? AtomicFileCreation.SUPPORTED
							: AtomicFileCreation.NOT_SUPPORTED;
				} else {
					supportsAtomicFileCreation = AtomicFileCreation.SUPPORTED;
				}
			} catch (IOException | ConfigInvalidException e) {
				LOG.warn(JGitText.get().assumeAtomicCreateNewFile
				supportsAtomicFileCreation = AtomicFileCreation.SUPPORTED;
			}
