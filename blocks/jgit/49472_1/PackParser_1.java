		if (isCheckObjectCollisions()) {
			try {
				final ObjectLoader ldr = readCurs.open(id
				final byte[] existingData = ldr.getCachedBytes(data.length);
				if (!Arrays.equals(data
					throw new IOException(MessageFormat.format(
							JGitText.get().collisionOn
				}
			} catch (MissingObjectException notLocal) {
