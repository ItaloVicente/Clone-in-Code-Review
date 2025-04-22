package org.eclipse.jgit.transport.http;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;

public class HttpConfig {
	public enum HttpRedirectEnum implements Config.ConfigEnum {

		INITIAL("initial")
		TRUE("true")

		private final String configValue;

		private HttpRedirectEnum(String configValue) {
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

	private final int postBuffer;

	public int getPostBuffer() {
		return postBuffer;
	}

	public boolean isSslVerify() {
		return sslVerify;
	}

	public HttpRedirectEnum getRedirect() {
		return redirect;
	}

	private final boolean sslVerify;

	private final HttpRedirectEnum redirect;

	public HttpConfig(final Config rc) {
		postBuffer = rc.getInt(ConfigConstants.CONFIG_HTTP_SECTION
				ConfigConstants.CONFIG_KEY_POSTBUFFER
		sslVerify = rc.getBoolean(ConfigConstants.CONFIG_HTTP_SECTION
				ConfigConstants.CONFIG_KEY_SSLVERIFY
		redirect = rc.getEnum(HttpRedirectEnum.values()
				ConfigConstants.CONFIG_HTTP_SECTION
				ConfigConstants.CONFIG_KEY_FOLLOWREDIRECTS
				HttpRedirectEnum.INITIAL);
	}

	public HttpConfig() {
		this(new Config());
	}
}
