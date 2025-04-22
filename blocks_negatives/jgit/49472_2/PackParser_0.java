		try {
			final ObjectLoader ldr = readCurs.open(id, type);
			final byte[] existingData = ldr.getCachedBytes(data.length);
			if (!Arrays.equals(data, existingData)) {
				throw new IOException(MessageFormat.format(
						JGitText.get().collisionOn, id.name()));
