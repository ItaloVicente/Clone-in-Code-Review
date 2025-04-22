	private void parseFilter(String arg) throws PackProtocolException {
			filterBlobLimit = 0;
			try {
				filterBlobLimit = Long.parseLong(
			} catch (NumberFormatException e) {
				throw new PackProtocolException(
						MessageFormat.format(JGitText.get().invalidFilter
								arg));
			}
		}
		if (filterBlobLimit < 0) {
			throw new PackProtocolException(
					MessageFormat.format(JGitText.get().invalidFilter
							arg));
		}
	}

