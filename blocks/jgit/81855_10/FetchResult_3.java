
package org.eclipse.jgit.lib;

public class SubmoduleConfig {

	public enum FetchRecurseSubmodulesMode implements Config.ConfigEnum {
		YES("true")

		ON_DEMAND("on-demand")


		private final String configValue;

		private FetchRecurseSubmodulesMode(String configValue) {
			this.configValue = configValue;
		}

		@Override
		public String toConfigValue() {
			return configValue;
		}

		@Override
		public boolean matchConfigValue(String s) {
			return configValue.equals(s);
		}
	}
}
