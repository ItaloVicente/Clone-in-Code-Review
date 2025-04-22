		String n = getRawString(section, subsection, name);
		if (n == null)
			return defaultValue;
		if (MAGIC_EMPTY_VALUE == n)
			return true;
		try {
			return StringUtils.toBoolean(n);
		} catch (IllegalArgumentException err) {
			throw new IllegalArgumentException(MessageFormat.format(JGitText.get().invalidBooleanValue
					, section, name, n));
		}
