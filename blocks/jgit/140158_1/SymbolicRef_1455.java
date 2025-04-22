
package org.eclipse.jgit.lib;

import org.eclipse.jgit.util.StringUtils;

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
			if (StringUtils.isEmptyOrNull(s)) {
				return false;
			}
			s = s.replace('-'
			return name().equalsIgnoreCase(s)
					|| configValue.equalsIgnoreCase(s);
		}
	}
}
