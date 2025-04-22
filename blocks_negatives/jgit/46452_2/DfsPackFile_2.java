	private void verifyPackChecksum(long position, MessageDigest md,
			DfsReader ctx) throws IOException {
		if (md != null) {
			byte[] buf = new byte[20];
			byte[] actHash = md.digest();
			if (ctx.copy(this, position, buf, 0, 20) != 20)
				throw packfileIsTruncated();
			if (!Arrays.equals(actHash, buf)) {
				invalid = true;
				throw new IOException(MessageFormat.format(
						JGitText.get().packfileCorruptionDetected,
						getPackName()));
			}
		}
	}

