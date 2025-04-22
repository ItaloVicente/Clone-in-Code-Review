
package org.eclipse.jgit.lib;

import org.eclipse.jgit.util.StringUtils;

class ConfigLine {
	String prefix;

	String section;

	String subsection;

	String name;

	String value;

	String suffix;

	String includedFrom;

	ConfigLine forValue(String newValue) {
		final ConfigLine e = new ConfigLine();
		e.prefix = prefix;
		e.section = section;
		e.subsection = subsection;
		e.name = name;
		e.value = newValue;
		e.suffix = suffix;
		e.includedFrom = includedFrom;
		return e;
	}

	boolean match(final String aSection
			final String aKey) {
		return eqIgnoreCase(section
				&& eqSameCase(subsection
				&& eqIgnoreCase(name
	}

	boolean match(String aSection
		return eqIgnoreCase(section
				&& eqSameCase(subsection
	}

	private static boolean eqIgnoreCase(String a
		if (a == null && b == null)
			return true;
		if (a == null || b == null)
			return false;
		return StringUtils.equalsIgnoreCase(a
	}

	private static boolean eqSameCase(String a
		if (a == null && b == null)
			return true;
		if (a == null || b == null)
			return false;
		return a.equals(b);
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		if (section == null)
			return "<empty>";
		StringBuilder b = new StringBuilder(section);
		if (subsection != null)
			b.append(".").append(subsection);
		if (name != null)
			b.append(".").append(name);
		if (value != null)
			b.append("=").append(value);
		return b.toString();
	}
}
