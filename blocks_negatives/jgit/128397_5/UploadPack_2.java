	private long parseFilter(String arg) throws PackProtocolException {
		long blobLimit = -1;

			blobLimit = 0;
			try {
				blobLimit = Long.parseLong(
			} catch (NumberFormatException e) {
				throw new PackProtocolException(
						MessageFormat.format(JGitText.get().invalidFilter,
								arg));
			}
		}
		/*
		 * We must have (1) either "blob:none" or
		 * "blob:limit=" set (because we only support
		 * blob size limits for now), and (2) if the
		 * latter, then it must be nonnegative. Throw
		 * if (1) or (2) is not met.
		 */
		if (blobLimit < 0) {
			throw new PackProtocolException(
					MessageFormat.format(JGitText.get().invalidFilter,
							arg));
		}

		return blobLimit;
	}

