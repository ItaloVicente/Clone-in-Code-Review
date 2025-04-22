
package org.eclipse.jgit.internal.ketch;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.eclipse.jgit.internal.ketch.KetchConstants.CONFIG_KEY_COMMIT;
import static org.eclipse.jgit.internal.ketch.KetchConstants.CONFIG_KEY_SPEED;
import static org.eclipse.jgit.internal.ketch.KetchConstants.CONFIG_KEY_TYPE;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_REMOTE;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.internal.ketch.KetchReplica.CommitMethod;
import org.eclipse.jgit.internal.ketch.KetchReplica.CommitSpeed;
import org.eclipse.jgit.internal.ketch.KetchReplica.Type;
import org.eclipse.jgit.lib.Config;

public class ReplicaConfig {
	private Type type = Type.NONE;
	private CommitMethod commitMethod = CommitMethod.ALL_REFS;
	private CommitSpeed commitSpeed = CommitSpeed.BATCHED;
	private long minRetry = SECONDS.toMillis(5);
	private long maxRetry = MINUTES.toMillis(1);

	public Type getType() {
		return type;
	}

	public CommitMethod getCommitMethod() {
		return commitMethod;
	}

	public CommitSpeed getCommitSpeed() {
		return commitSpeed;
	}

	public long getMinRetry(TimeUnit unit) {
		return unit.convert(minRetry
	}

	public long getMaxRetry(TimeUnit unit) {
		return unit.convert(maxRetry
	}

	public ReplicaConfig fromConfig(Config cfg
		type = cfg.getEnum(CONFIG_KEY_REMOTE
		commitMethod = cfg.getEnum(
				CONFIG_KEY_REMOTE
				commitMethod);
		commitSpeed = cfg.getEnum(
				CONFIG_KEY_REMOTE
				commitSpeed);
		minRetry = getMillis(cfg
		maxRetry = getMillis(cfg
		return this;
	}

	private static long getMillis(Config cfg
			long defaultValue) {
		String valStr = cfg.getString(CONFIG_KEY_REMOTE
		if (valStr == null) {
			return defaultValue;
		}

		valStr = valStr.trim();
		if (valStr.isEmpty()) {
			return defaultValue;
		}

		Matcher m = matcher("^([1-9][0-9]*(?:\\.[0-9]*)?)\\s*(.*)$"
		if (!m.matches()) {
			return defaultValue;
		}

		String digits = m.group(1);
		String unitName = m.group(2).trim();
		TimeUnit unit = UnitMap.UNITS.get(unitName);
		if (unit == null) {
			return defaultValue;
		}

		try {
			if (digits.indexOf('.') == -1) {
				return unit.toMillis(Long.parseLong(digits));
			}

			double val = Double.parseDouble(digits);
			return (long) (val * unit.toMillis(1));
		} catch (NumberFormatException nfe) {
			return defaultValue;
		}
	}

	private static Matcher matcher(String pattern
		return Pattern.compile(pattern).matcher(valStr);
	}

	static class UnitMap {
		static final Map<String

		static {
			Map<String
			TimeUnit u = MILLISECONDS;
			m.put(""
			m.put("ms"
			m.put("millis"
			m.put("millisecond"
			m.put("milliseconds"

			u = SECONDS;
			m.put("s"
			m.put("sec"
			m.put("second"
			m.put("seconds"

			u = MINUTES;
			m.put("m"
			m.put("min"
			m.put("minute"
			m.put("minutes"

			u = HOURS;
			m.put("h"
			m.put("hr"
			m.put("hour"
			m.put("hours"

			u = DAYS;
			m.put("d"
			m.put("day"
			m.put("days"

			UNITS = Collections.unmodifiableMap(m);
		}

		private UnitMap() {
		}
	}
}
