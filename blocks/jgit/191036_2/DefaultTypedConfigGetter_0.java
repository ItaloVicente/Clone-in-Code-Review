	@Override
	public int getIntInRange(Config config
			String name
		int val = getInt(config
		if ((val >= minValue && val <= maxValue) || val == UNSET_INT) {
			return val;
		}
		throw new IllegalArgumentException(MessageFormat
				.format(JGitText.get().integerValueNotInRange
						subsection
						Integer.valueOf(minValue)
						Integer.valueOf(maxValue)));
	}

