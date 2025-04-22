		final String str = getString(section, subsection, name);
		if (str == null)
			return defaultValue;

		String n = str.trim();
		if (n.length() == 0)
			return defaultValue;

		long mul = 1;
		switch (StringUtils.toLowerCase(n.charAt(n.length() - 1))) {
		case 'g':
			mul = GiB;
			break;
		case 'm':
			mul = MiB;
			break;
		case 'k':
			mul = KiB;
			break;
		}
		if (mul > 1)
			n = n.substring(0, n.length() - 1).trim();
		if (n.length() == 0)
			return defaultValue;

		try {
			return mul * Long.parseLong(n);
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException(MessageFormat.format(JGitText.get().invalidIntegerValue
					, section, name, str));
		}
