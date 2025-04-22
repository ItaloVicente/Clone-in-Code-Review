		String value = getString(section, subsection, name);
		if (value == null)
			return defaultValue;

		if (all[0] instanceof ConfigEnum) {
			for (T t : all) {
				if (((ConfigEnum) t).matchConfigValue(value))
					return t;
			}
		}

		String n = value.replace(' ', '_');

		n = n.replace('-', '_');

		T trueState = null;
		T falseState = null;
		for (T e : all) {
			if (StringUtils.equalsIgnoreCase(e.name(), n))
				return e;
			else if (StringUtils.equalsIgnoreCase(e.name(), "TRUE")) //$NON-NLS-1$
				trueState = e;
			else if (StringUtils.equalsIgnoreCase(e.name(), "FALSE")) //$NON-NLS-1$
				falseState = e;
		}

		if (trueState != null && falseState != null) {
			try {
				return StringUtils.toBoolean(n) ? trueState : falseState;
			} catch (IllegalArgumentException err) {
			}
		}

		if (subsection != null)
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().enumValueNotSupported3, section, subsection,
					name, value));
		else
			throw new IllegalArgumentException(
					MessageFormat.format(JGitText.get().enumValueNotSupported2,
							section, name, value));
