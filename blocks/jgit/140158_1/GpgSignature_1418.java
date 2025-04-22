package org.eclipse.jgit.lib;

public class GpgConfig {

	public enum GpgFormat implements Config.ConfigEnum {

		OPENPGP("openpgp")

		private final String configValue;

		private GpgFormat(String configValue) {
			this.configValue = configValue;
		}

		@Override
		public boolean matchConfigValue(String s) {
			return configValue.equals(s);
		}

		@Override
		public String toConfigValue() {
			return configValue;
		}
	}

	private final Config config;

	public GpgConfig(Config config) {
		this.config = config;
	}

	public GpgFormat getKeyFormat() {
		return config.getEnum(GpgFormat.values()
				ConfigConstants.CONFIG_GPG_SECTION
				ConfigConstants.CONFIG_KEY_FORMAT
	}

	public String getSigningKey() {
		return config.getString(ConfigConstants.CONFIG_USER_SECTION
				ConfigConstants.CONFIG_KEY_SIGNINGKEY);
	}

	public boolean isSignCommits() {
		return config.getBoolean(ConfigConstants.CONFIG_COMMIT_SECTION
				ConfigConstants.CONFIG_KEY_GPGSIGN
	}
}
