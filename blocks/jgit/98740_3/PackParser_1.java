				sz -= n;
			}
		} catch (MissingObjectException notLocal) {
		} finally {
			pck.close();
			cur.close();
		}
	}

	private void checkObjectCollision(AnyObjectId obj
			throws IOException {
		try {
			final ObjectLoader ldr = readCurs.open(obj
			final byte[] existingData = ldr.getCachedBytes(data.length);
			if (!Arrays.equals(data
				throw new IOException(MessageFormat.format(
						JGitText.get().collisionOn
