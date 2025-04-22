		return new FilterSpec(blobLimit
	}

	private static long parsePositive(String filterSpec
			throws PackProtocolException {
		long value;
		try {
			value = Long.parseLong(filterSpec.substring(offset));
			if (value < 0) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().invalidFilter
			}

			return value;
		} catch (NumberFormatException e) {
			throw new PackProtocolException(MessageFormat
					.format(JGitText.get().invalidFilter
		}
