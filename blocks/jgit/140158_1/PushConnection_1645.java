
package org.eclipse.jgit.transport;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.util.StringUtils;

public class PushConfig {
	public enum PushRecurseSubmodulesMode implements Config.ConfigEnum {
		CHECK("check")

		ON_DEMAND("on-demand")


		private final String configValue;

		private PushRecurseSubmodulesMode(String configValue) {
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
