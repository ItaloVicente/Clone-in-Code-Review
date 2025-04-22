
package org.eclipse.jgit.transport;

import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.jgit.util.SystemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConfig {

	private static final Logger LOG = LoggerFactory.getLogger(HttpConfig.class);








	private static final int DEFAULT_MAX_REDIRECTS = 5;

	private static final int MAX_REDIRECTS = (new Supplier<Integer>() {

		@Override
		public Integer get() {
			String rawValue = SystemReader.getInstance()
					.getProperty(MAX_REDIRECT_SYSTEM_PROPERTY);
			Integer value = Integer.valueOf(DEFAULT_MAX_REDIRECTS);
			if (rawValue != null) {
				try {
					value = Integer.valueOf(Integer.parseUnsignedInt(rawValue));
				} catch (NumberFormatException e) {
					LOG.warn(MessageFormat.format(
							JGitText.get().invalidSystemProperty
							MAX_REDIRECT_SYSTEM_PROPERTY
				}
			}
			return value;
		}
	}).get().intValue();

	public enum HttpRedirectMode implements Config.ConfigEnum {

		TRUE("true")
		INITIAL("initial")

		private final String configValue;

		private HttpRedirectMode(String configValue) {
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

	private final boolean sslVerify;

	private final HttpRedirectMode followRedirects;

	private final int maxRedirects;

	public int getPostBuffer() {
		return postBuffer;
	}

	public boolean isSslVerify() {
		return sslVerify;
	}

	public HttpRedirectMode getFollowRedirects() {
		return followRedirects;
	}

	public int getMaxRedirects() {
		return maxRedirects;
	}

	public HttpConfig(Config config
		int postBufferSize = config.getInt(HTTP
				1 * 1024 * 1024);
		boolean sslVerifyFlag = config.getBoolean(HTTP
		HttpRedirectMode followRedirectsMode = config.getEnum(
				HttpRedirectMode.values()
				FOLLOW_REDIRECTS_KEY
		int redirectLimit = config.getInt(HTTP
				MAX_REDIRECTS);
		if (redirectLimit < 0) {
			redirectLimit = MAX_REDIRECTS;
		}
		String match = findMatch(config.getSubsections(HTTP)
		if (match != null) {
			postBufferSize = config.getInt(HTTP
					postBufferSize);
			sslVerifyFlag = config.getBoolean(HTTP
					sslVerifyFlag);
			followRedirectsMode = config.getEnum(HttpRedirectMode.values()
					HTTP
			int newMaxRedirects = config.getInt(HTTP
					redirectLimit);
			if (newMaxRedirects >= 0) {
				redirectLimit = newMaxRedirects;
			}
		}
		postBuffer = postBufferSize;
		sslVerify = sslVerifyFlag;
		followRedirects = followRedirectsMode;
		maxRedirects = redirectLimit;
	}

	public HttpConfig(URIish uri) {
		this(SystemReader.getInstance().openUserConfig(null
	}

	private String findMatch(Set<String> names
		String bestMatch = null;
		int bestMatchLength = -1;
		boolean withUser = false;
		String uPath = uri.getPath();
		boolean hasPath = !StringUtils.isEmptyOrNull(uPath);
		if (hasPath) {
			uPath = normalize(uPath);
			if (uPath == null) {
				return null;
			}
		}
		for (String s : names) {
			try {
				URIish candidate = new URIish(s);
				if (!compare(uri.getScheme()
						|| !compare(uri.getHost()
					continue;
				}
				if (defaultedPort(uri.getPort()
						uri.getScheme()) != defaultedPort(candidate.getPort()
								candidate.getScheme())) {
					continue;
				}
				boolean hasUser = false;
				if (candidate.getUser() != null) {
					if (!candidate.getUser().equals(uri.getUser())) {
						continue;
					}
					hasUser = true;
				}
				String cPath = candidate.getPath();
				int matchLength = -1;
				if (StringUtils.isEmptyOrNull(cPath)) {
					matchLength = 0;
				} else {
					if (!hasPath) {
						continue;
					}
					matchLength = segmentCompare(uPath
					if (matchLength < 0) {
						continue;
					}
				}
				if (matchLength > bestMatchLength || !withUser && hasUser
						&& matchLength >= 0 && matchLength == bestMatchLength) {
					bestMatch = s;
					bestMatchLength = matchLength;
					withUser = hasUser;
				}
			} catch (URISyntaxException e) {
				LOG.warn(MessageFormat
						.format(JGitText.get().httpConfigInvalidURL
			}
		}
		return bestMatch;
	}

	private boolean compare(String a
		if (a == null) {
			return b == null;
		}
		return a.equalsIgnoreCase(b);
	}

	private int defaultedPort(int port
		if (port >= 0) {
			return port;
		}
		if (FTP.equalsIgnoreCase(scheme)) {
			return 21;
		} else if (HTTP.equalsIgnoreCase(scheme)) {
			return 80;
		} else {
		}
	}

	static int segmentCompare(String uriPath
		String matchPath = normalize(m);
		if (matchPath == null || !uriPath.startsWith(matchPath)) {
			return -1;
		}
		int uLength = uriPath.length();
		int mLength = matchPath.length();
		if (mLength == uLength || matchPath.charAt(mLength - 1) == '/'
				|| mLength < uLength && uriPath.charAt(mLength) == '/') {
			return mLength;
		}
		return -1;
	}

	static String normalize(String path) {
		int i = 0;
		int length = path.length();
		StringBuilder builder = new StringBuilder(length);
		builder.append('/');
		if (length > 0 && path.charAt(0) == '/') {
			i = 1;
		}
		while (i < length) {
			int slash = path.indexOf('/'
			if (slash < 0) {
				slash = length;
			}
			if (slash == i || slash == i + 1 && path.charAt(i) == '.') {
			} else if (slash == i + 2 && path.charAt(i) == '.'
					&& path.charAt(i + 1) == '.') {
				while (l >= 0 && builder.charAt(l) != '/') {
					l--;
				}
				if (l < 0) {
					LOG.warn(MessageFormat.format(
							JGitText.get().httpConfigCannotNormalizeURL
					return null;
				}
				builder.setLength(l + 1);
			} else {
				builder.append(path
			}
			i = slash + 1;
		}
		if (builder.length() > 1 && builder.charAt(builder.length() - 1) == '/'
				&& length > 0 && path.charAt(length - 1) != '/') {
			builder.setLength(builder.length() - 1);
		}
		return builder.toString();
	}
}
