	/*
	 * Process the content of "filter" line from the protocol. It has a shape
	 * like "blob:none" or "blob:limit=N", with limit a positive number.
	 *
	 * @param blobLine
	 *            the content of the "filter" line in the protocol
	 * @return N, the limit, defaulting to 0 if "none"
	 * @throws PackProtocolException
	 *             invalid filter because due to unrecognized format or
	 *             negative/non-numeric filter.
	 */
	static long filterLine(String blobLine) throws PackProtocolException {
		long blobLimit = -1;

			blobLimit = 0;
			try {
				blobLimit = Long
			} catch (NumberFormatException e) {
				throw new PackProtocolException(MessageFormat
						.format(JGitText.get().invalidFilter, blobLine));
			}
		}
		/*
		 * We must have (1) either "blob:none" or "blob:limit=" set (because we
		 * only support blob size limits for now), and (2) if the latter, then
		 * it must be nonnegative. Throw if (1) or (2) is not met.
		 */
		if (blobLimit < 0) {
			throw new PackProtocolException(
					MessageFormat.format(JGitText.get().invalidFilter, blobLine));
		}

		return blobLimit;
	}

