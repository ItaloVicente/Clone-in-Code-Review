	private static Sha1Implementation sha1Implementation() {
		try {
			return SystemReader.getInstance().getUserConfig().getEnum(
					ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.SHA1_IMPLEMENTATION
					Sha1Implementation.JAVA);
		} catch (ConfigInvalidException | IOException e) {
			return Sha1Implementation.JAVA;
		}
	}
