	@Override
	public int getIntInRange(Config config
			String name
		int val = getInt(config
		if ((val >= minValue && val <= maxValue) || val == UNSET_INT) {
			return val;
		}
		if (subsection == null) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().integerValueNotInRange
					Integer.valueOf(val)
					Integer.valueOf(maxValue)));
		}
		throw new IllegalArgumentException(MessageFormat.format(
				JGitText.get().integerValueNotInRangeSubSection
				subsection
				Integer.valueOf(minValue)
	}

