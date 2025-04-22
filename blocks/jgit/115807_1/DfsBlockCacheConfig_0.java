				getBlockSize());
		if (cfgBlockLimit % cfgBlockSize != 0) {
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().blockLimitNotMultipleOfBlockSize
					Long.valueOf(cfgBlockLimit)
					Long.valueOf(cfgBlockSize)));
		}

		setBlockLimit(cfgBlockLimit);
		setBlockSize(cfgBlockSize);
