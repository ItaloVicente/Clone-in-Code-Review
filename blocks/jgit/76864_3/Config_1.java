	public long getTimeUnit(String section
			long defaultValue
		String valueString = getString(section

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

	private IllegalArgumentException notTimeUnit(String section
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

