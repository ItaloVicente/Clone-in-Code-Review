
		String v = rc.getString(
				CONFIG_CORE_SECTION
				CONFIG_DFS_SECTION
				CONFIG_KEY_STREAM_RATIO);
		if (v != null) {
			try {
				setStreamRatio(Double.parseDouble(v));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(MessageFormat.format(
						JGitText.get().enumValueNotSupported3
						CONFIG_CORE_SECTION
						CONFIG_DFS_SECTION
						CONFIG_KEY_STREAM_RATIO
			}
		}
