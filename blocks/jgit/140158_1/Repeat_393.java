
package org.eclipse.jgit.junit;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.SystemReader;
import org.eclipse.jgit.util.time.MonotonicClock;
import org.eclipse.jgit.util.time.ProposedTimestamp;

public class MockSystemReader extends SystemReader {
	private static final class MockConfig extends FileBasedConfig {
		private MockConfig(File cfgLocation
			super(cfgLocation
		}

		@Override
		public void load() throws IOException
		}

		@Override
		public boolean isOutdated() {
			return false;
		}
	}


	final Map<String

	FileBasedConfig userGitConfig;

	FileBasedConfig systemGitConfig;

	public MockSystemReader() {
		init(Constants.OS_USER_NAME_KEY);
		init(Constants.GIT_AUTHOR_NAME_KEY);
		init(Constants.GIT_AUTHOR_EMAIL_KEY);
		init(Constants.GIT_COMMITTER_NAME_KEY);
		init(Constants.GIT_COMMITTER_EMAIL_KEY);
		setProperty(Constants.OS_USER_DIR
		userGitConfig = new MockConfig(null
		systemGitConfig = new MockConfig(null
		setCurrentPlatform();
	}

	private void init(String n) {
		setProperty(n
	}

	public void clearProperties() {
		values.clear();
	}

	public void setProperty(String key
		values.put(key
	}

	@Override
	public String getenv(String variable) {
		return values.get(variable);
	}

	@Override
	public String getProperty(String key) {
		return values.get(key);
	}

	@Override
	public FileBasedConfig openUserConfig(Config parent
		assert parent == null || parent == systemGitConfig;
		return userGitConfig;
	}

	@Override
	public FileBasedConfig openSystemConfig(Config parent
		assert parent == null;
		return systemGitConfig;
	}

	@Override
	public String getHostname() {
		return "fake.host.example.com";
	}

	@Override
	public long getCurrentTime() {
		return now;
	}

	@Override
	public MonotonicClock getClock() {
		return new MonotonicClock() {
			@Override
			public ProposedTimestamp propose() {
				long t = getCurrentTime();
				return new ProposedTimestamp() {
					@Override
					public long read(TimeUnit unit) {
						return unit.convert(t
					}

					@Override
					public void blockUntil(Duration maxWait) {
					}
				};
			}
		};
	}

	public void tick(int secDelta) {
		now += secDelta * 1000L;
	}

	@Override
	public int getTimezone(long when) {
		return getTimeZone().getOffset(when) / (60 * 1000);
	}

	@Override
	public TimeZone getTimeZone() {
		return TimeZone.getTimeZone("GMT-03:30");
	}

	@Override
	public Locale getLocale() {
		return Locale.US;
	}

	@Override
	public SimpleDateFormat getSimpleDateFormat(String pattern) {
		return new SimpleDateFormat(pattern
	}

	@Override
	public DateFormat getDateTimeInstance(int dateStyle
		return DateFormat
				.getDateTimeInstance(dateStyle
	}

	public void setCurrentPlatform() {
		resetOsNames();
		setProperty("os.name"
		setProperty("file.separator"
		setProperty("path.separator"
		setProperty("line.separator"
	}

	public void setWindows() {
		resetOsNames();
		setProperty("os.name"
		setProperty("file.separator"
		setProperty("path.separator"
		setProperty("line.separator"
		setPlatformChecker();
	}

	public void setUnix() {
		resetOsNames();
		setProperty("os.name"
		setProperty("file.separator"
		setProperty("path.separator"
		setProperty("line.separator"
		setPlatformChecker();
	}

	private void resetOsNames() {
		Field field;
		try {
			field = SystemReader.class.getDeclaredField("isWindows");
			field.setAccessible(true);
			field.set(null
			field = SystemReader.class.getDeclaredField("isMacOS");
			field.setAccessible(true);
			field.set(null
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
