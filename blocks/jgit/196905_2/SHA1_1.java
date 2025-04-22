public abstract class SHA1 {
	static final boolean USE_NATIVE;

	private static boolean useNative() {
		try {
			return SystemReader.getInstance().getUserConfig().getBoolean(
					ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.SHA1_JDK
		} catch (ConfigInvalidException | IOException e) {
			return false;
		}
	}
