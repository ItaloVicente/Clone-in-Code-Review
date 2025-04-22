
package org.eclipse.jgit.storage.dht;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.util.StringUtils;

public class Timeout {
	public static Timeout milliseconds(int millis) {
		return new Timeout(millis
	}

	public static Timeout seconds(int sec) {
		return new Timeout(sec
	}

	public static Timeout seconds(double sec) {
		return new Timeout((long) (sec * 1000)
	}

	public static Timeout getTimeout(Config cfg
			String subsection
		String valStr = cfg.getString(section
		if (valStr == null)
			return defaultValue;

		valStr = valStr.trim();
		if (valStr.length() == 0)
			return defaultValue;

		Matcher m = matcher("^([1-9][0-9]*(?:\\.[0-9]*)?)\\s*(.*)$"
		if (!m.matches())
			throw notTimeUnit(section

		String digits = m.group(1);
		String unitName = m.group(2).trim();

		TimeUnit inputUnit;
		if ("".equals(unitName))
			inputUnit = TimeUnit.MILLISECONDS;

		else if (anyOf(unitName
			inputUnit = TimeUnit.MILLISECONDS;

		else if (anyOf(unitName
			inputUnit = TimeUnit.SECONDS;

		else if (anyOf(unitName
			inputUnit = TimeUnit.MINUTES;

		else if (anyOf(unitName
			inputUnit = TimeUnit.HOURS;

		else
			throw notTimeUnit(section

		if (digits.indexOf('.') == -1) {
			try {
				return new Timeout(Long.parseLong(digits)
			} catch (NumberFormatException nfe) {
				throw notTimeUnit(section
			}
		} else {
			double inputTime;
			try {
				inputTime = Double.parseDouble(digits);
			} catch (NumberFormatException nfe) {
				throw notTimeUnit(section
			}

			TimeUnit newUnit;
			switch (inputUnit) {
			case MILLISECONDS:
				newUnit = TimeUnit.NANOSECONDS;
				break;
			case SECONDS:
				newUnit = TimeUnit.MILLISECONDS;
				break;
			case MINUTES:
				newUnit = TimeUnit.SECONDS;
				break;
			case HOURS:
				newUnit = TimeUnit.MINUTES;
				break;
			default:
				throw notTimeUnit(section
			}

			long newTime = (long) (inputTime * newUnit.convert(1
			return new Timeout(newTime
		}
	}

	private static Matcher matcher(String pattern
		return Pattern.compile(pattern).matcher(valStr);
	}

	private static boolean anyOf(String a
		for (String b : cases) {
			if (StringUtils.equalsIgnoreCase(a
				return true;
		}
		return false;
	}

	private static IllegalArgumentException notTimeUnit(String section
			String subsection
		String key = section + (subsection != null ? "." + subsection : "")
				+ "." + name;
		return new IllegalArgumentException(MessageFormat.format(
				DhtText.get().notTimeUnit
	}

	private final long time;

	private final TimeUnit unit;

	public Timeout(long time
		this.time = time;
		this.unit = unit;
	}

	public long getTime() {
		return time;
	}

	public TimeUnit getUnit() {
		return unit;
	}

	@Override
	public int hashCode() {
		return (int) time;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Timeout)
			return getTime() == ((Timeout) other).getTime()
					&& getUnit().equals(((Timeout) other).getUnit());
		return false;
	}

	@Override
	public String toString() {
		return getTime() + " " + getUnit();
	}
}
