
package org.eclipse.jgit.util;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.time.MonotonicClock;
import org.eclipse.jgit.util.time.MonotonicSystemClock;

public abstract class SystemReader {
	private static final SystemReader DEFAULT;

	private static Boolean isMacOS;

	private static Boolean isWindows;

	static {
		SystemReader r = new Default();
		r.init();
		DEFAULT = r;
	}

	private static class Default extends SystemReader {
		private volatile String hostname;

		@Override
		public String getenv(String variable) {
			return System.getenv(variable);
		}

		@Override
		public String getProperty(String key) {
			return System.getProperty(key);
		}

		@Override
		public FileBasedConfig openSystemConfig(Config parent
			File configFile = fs.getGitSystemConfig();
			if (configFile == null) {
				return new FileBasedConfig(null
					@Override
					public void load() {
					}

					@Override
					public boolean isOutdated() {
						return false;
					}
				};
			}
			return new FileBasedConfig(parent
		}

		@Override
		public FileBasedConfig openUserConfig(Config parent
			final File home = fs.userHome();
			return new FileBasedConfig(parent
		}

		@Override
		public String getHostname() {
			if (hostname == null) {
				try {
					InetAddress localMachine = InetAddress.getLocalHost();
					hostname = localMachine.getCanonicalHostName();
				} catch (UnknownHostException e) {
				}
				assert hostname != null;
			}
			return hostname;
		}

		@Override
		public long getCurrentTime() {
			return System.currentTimeMillis();
		}

		@Override
		public int getTimezone(long when) {
			return getTimeZone().getOffset(when) / (60 * 1000);
		}
	}

	private static SystemReader INSTANCE = DEFAULT;

	public static SystemReader getInstance() {
		return INSTANCE;
	}

	public static void setInstance(SystemReader newReader) {
		isMacOS = null;
		isWindows = null;
		if (newReader == null)
			INSTANCE = DEFAULT;
		else {
			newReader.init();
			INSTANCE = newReader;
		}
	}

	private ObjectChecker platformChecker;

	private void init() {
		if (platformChecker == null)
			setPlatformChecker();
	}

	protected final void setPlatformChecker() {
		platformChecker = new ObjectChecker()
			.setSafeForWindows(isWindows())
			.setSafeForMacOS(isMacOS());
	}

	public abstract String getHostname();

	public abstract String getenv(String variable);

	public abstract String getProperty(String key);

	public abstract FileBasedConfig openUserConfig(Config parent

	public abstract FileBasedConfig openSystemConfig(Config parent

	public abstract long getCurrentTime();

	public MonotonicClock getClock() {
		return new MonotonicSystemClock();
	}

	public abstract int getTimezone(long when);

	public TimeZone getTimeZone() {
		return TimeZone.getDefault();
	}

	public Locale getLocale() {
		return Locale.getDefault();
	}

	public SimpleDateFormat getSimpleDateFormat(String pattern) {
		return new SimpleDateFormat(pattern);
	}

	public SimpleDateFormat getSimpleDateFormat(String pattern
		return new SimpleDateFormat(pattern
	}

	public DateFormat getDateTimeInstance(int dateStyle
		return DateFormat.getDateTimeInstance(dateStyle
	}

	public boolean isWindows() {
		if (isWindows == null) {
			String osDotName = getOsName();
		}
		return isWindows.booleanValue();
	}

	public boolean isMacOS() {
		if (isMacOS == null) {
			String osDotName = getOsName();
			isMacOS = Boolean.valueOf(
		}
		return isMacOS.booleanValue();
	}

	private String getOsName() {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
			}
		});
	}

	public void checkPath(String path) throws CorruptObjectException {
		platformChecker.checkPath(path);
	}

	public void checkPath(byte[] path) throws CorruptObjectException {
		platformChecker.checkPath(path
	}
}
