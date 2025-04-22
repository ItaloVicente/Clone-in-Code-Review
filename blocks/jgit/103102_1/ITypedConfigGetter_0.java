
package org.eclipse.jgit.lib;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Config.ConfigEnum;
import org.eclipse.jgit.util.StringUtils;

public class DefaultTypedConfigGetter implements ITypedConfigGetter {

	@Override
	public boolean getBoolean(Config config
			String subsection
		String n = config.getRawString(section
		if (n == null)
			return defaultValue;
		if (Config.MAGIC_EMPTY_VALUE == n)
			return true;
		try {
			return StringUtils.toBoolean(n);
		} catch (IllegalArgumentException err) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().invalidBooleanValue
		}
	}

	@Override
	public <T extends Enum<?>> T getEnum(Config config
			String section
			T defaultValue) {
		String value = config.getString(section
		if (value == null) {
			return defaultValue;
		}
		if (all[0] instanceof ConfigEnum) {
			for (T t : all) {
				if (((ConfigEnum) t).matchConfigValue(value)) {
					return t;
				}
			}
		}

		String n = value.replace(' '

		n = n.replace('-'

		T trueState = null;
		T falseState = null;
		for (T e : all) {
			if (StringUtils.equalsIgnoreCase(e.name()
				return e;
			} else if (StringUtils.equalsIgnoreCase(e.name()
				trueState = e;
			} else if (StringUtils.equalsIgnoreCase(e.name()
				falseState = e;
			}
		}

		if (trueState != null && falseState != null) {
			try {
				return StringUtils.toBoolean(n) ? trueState : falseState;
			} catch (IllegalArgumentException err) {
			}
		}

		if (subsection != null) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().enumValueNotSupported3
					subsection
		} else {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().enumValueNotSupported2
					value));
		}
	}

	@Override
	public int getInt(Config config
			String name
		long val = config.getLong(section
		if (Integer.MIN_VALUE <= val && val <= Integer.MAX_VALUE) {
			return (int) val;
		}
		throw new IllegalArgumentException(MessageFormat
				.format(JGitText.get().integerValueOutOfRange
	}

	@Override
	public long getLong(Config config
			String name
		final String str = config.getString(section
		if (str == null) {
			return defaultValue;
		}
		String n = str.trim();
		if (n.length() == 0) {
			return defaultValue;
		}
		long mul = 1;
		switch (StringUtils.toLowerCase(n.charAt(n.length() - 1))) {
		case 'g':
			mul = Config.GiB;
			break;
		case 'm':
			mul = Config.MiB;
			break;
		case 'k':
			mul = Config.KiB;
			break;
		}
		if (mul > 1) {
			n = n.substring(0
		}
		if (n.length() == 0) {
			return defaultValue;
		}
		try {
			return mul * Long.parseLong(n);
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().invalidIntegerValue
		}
	}

	@Override
	public long getTimeUnit(Config config
			String name
		String valueString = config.getString(section

		if (valueString == null) {
			return defaultValue;
		}

		String s = valueString.trim();
		if (s.length() == 0) {
			return defaultValue;
		}

			throw notTimeUnit(section
		}

				.matcher(valueString);
		if (!m.matches()) {
			return defaultValue;
		}

		String digits = m.group(1);
		String unitName = m.group(2).trim();

		TimeUnit inputUnit;
		int inputMul;

		if (unitName.isEmpty()) {
			inputUnit = wantUnit;
			inputMul = 1;

		} else if (match(unitName
			inputUnit = TimeUnit.MILLISECONDS;
			inputMul = 1;

		} else if (match(unitName
			inputUnit = TimeUnit.SECONDS;
			inputMul = 1;

		} else if (match(unitName
			inputUnit = TimeUnit.MINUTES;
			inputMul = 1;

		} else if (match(unitName
			inputUnit = TimeUnit.HOURS;
			inputMul = 1;

		} else if (match(unitName
			inputUnit = TimeUnit.DAYS;
			inputMul = 1;

		} else if (match(unitName
			inputUnit = TimeUnit.DAYS;
			inputMul = 7;

		} else if (match(unitName
			inputUnit = TimeUnit.DAYS;
			inputMul = 30;

		} else if (match(unitName
			inputUnit = TimeUnit.DAYS;
			inputMul = 365;

		} else {
			throw notTimeUnit(section
		}

		try {
			return wantUnit.convert(Long.parseLong(digits) * inputMul
					inputUnit);
		} catch (NumberFormatException nfe) {
			throw notTimeUnit(section
		}
	}

	private static boolean match(final String a
		for (final String b : cases) {
			if (b != null && b.equalsIgnoreCase(a)) {
				return true;
			}
		}
		return false;
	}

	private static IllegalArgumentException notTimeUnit(String section
			String subsection
		if (subsection != null) {
			return new IllegalArgumentException(
					MessageFormat.format(JGitText.get().invalidTimeUnitValue3
							section
		}
		return new IllegalArgumentException(
				MessageFormat.format(JGitText.get().invalidTimeUnitValue2
						section
	}
}
