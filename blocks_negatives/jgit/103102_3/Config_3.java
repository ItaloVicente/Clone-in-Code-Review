		String valueString = getString(section, subsection, name);

		if (valueString == null) {
			return defaultValue;
		}

		String s = valueString.trim();
		if (s.length() == 0) {
			return defaultValue;
		}

			throw notTimeUnit(section, subsection, name, valueString);
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

		} else if (match(unitName, "ms", "milliseconds")) { //$NON-NLS-1$ //$NON-NLS-2$
			inputUnit = TimeUnit.MILLISECONDS;
			inputMul = 1;

		} else if (match(unitName, "s", "sec", "second", "seconds")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			inputUnit = TimeUnit.SECONDS;
			inputMul = 1;

		} else if (match(unitName, "m", "min", "minute", "minutes")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			inputUnit = TimeUnit.MINUTES;
			inputMul = 1;

		} else if (match(unitName, "h", "hr", "hour", "hours")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			inputUnit = TimeUnit.HOURS;
			inputMul = 1;

		} else if (match(unitName, "d", "day", "days")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			inputUnit = TimeUnit.DAYS;
			inputMul = 1;

		} else if (match(unitName, "w", "week", "weeks")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			inputUnit = TimeUnit.DAYS;
			inputMul = 7;

		} else if (match(unitName, "mon", "month", "months")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			inputUnit = TimeUnit.DAYS;
			inputMul = 30;

		} else if (match(unitName, "y", "year", "years")) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			inputUnit = TimeUnit.DAYS;
			inputMul = 365;

		} else {
			throw notTimeUnit(section, subsection, name, valueString);
		}

		try {
			return wantUnit.convert(Long.parseLong(digits) * inputMul,
					inputUnit);
		} catch (NumberFormatException nfe) {
			throw notTimeUnit(section, subsection, unitName, valueString);
		}
	}

	private static boolean match(final String a, final String... cases) {
		for (final String b : cases) {
			if (b != null && b.equalsIgnoreCase(a)) {
				return true;
			}
		}
		return false;
	}

	private IllegalArgumentException notTimeUnit(String section,
			String subsection, String name, String valueString) {
		if (subsection != null) {
			return new IllegalArgumentException(
					MessageFormat.format(JGitText.get().invalidTimeUnitValue3,
							section, subsection, name, valueString));
		}
		return new IllegalArgumentException(
				MessageFormat.format(JGitText.get().invalidTimeUnitValue2,
						section, name, valueString));
