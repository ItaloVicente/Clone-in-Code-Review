
	enum DirCacheVersion implements ConfigEnum {

		DIRC_VERSION_MINIMUM(2)
		DIRC_VERSION_EXTENDED(3)
		DIRC_VERSION_PATHCOMPRESS(4);

		private int version;

		private DirCacheVersion(int versionCode) {
			this.version = versionCode;
		}

		public int getVersionCode() {
			return version;
		}

		@Override
		public String toConfigValue() {
			return Integer.toString(version);
		}

		@Override
		public boolean matchConfigValue(String in) {
			try {
				return version == Integer.parseInt(in);
			} catch (NumberFormatException e) {
				return false;
			}
		}

		public static DirCacheVersion fromInt(int val) {
			for (DirCacheVersion v : DirCacheVersion.values()) {
				if (val == v.getVersionCode()) {
					return v;
				}
			}
			return null;
		}
	}

	private static class DirCacheConfig {

		private final DirCacheVersion indexVersion;

		public DirCacheConfig(Config cfg) {
			boolean manyFiles = cfg.getBoolean(
					ConfigConstants.CONFIG_FEATURE_SECTION
					ConfigConstants.CONFIG_KEY_MANYFILES
			indexVersion = cfg.getEnum(DirCacheVersion.values()
					ConfigConstants.CONFIG_INDEX_SECTION
					ConfigConstants.CONFIG_KEY_VERSION
					manyFiles ? DirCacheVersion.DIRC_VERSION_PATHCOMPRESS
							: DirCacheVersion.DIRC_VERSION_EXTENDED);
		}

		public DirCacheVersion getIndexVersion() {
			return indexVersion;
		}

	}
