			final byte[] data
		if (0 < maxObjectSizeLimit && maxObjectSizeLimit < size) {
			throw new IOException(MessageFormat.format(
					JGitText.get().receivePackObjectTooLarge
		}

		if (data == null)
			return;

