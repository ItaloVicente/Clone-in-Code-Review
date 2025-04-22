	void copyPackAsIs(DfsPackFile pack, long length, boolean validate,
			PackOutputStream out) throws IOException {
		MessageDigest md = null;
		if (validate) {
			md = Constants.newMessageDigest();
			byte[] buf = out.getCopyBuffer();
			pin(pack, 0);
			if (block.copy(0, buf, 0, 12) != 12) {
				pack.setInvalid();
				throw new IOException(MessageFormat.format(
						JGitText.get().packfileIsTruncated, pack.getPackName()));
			}
			md.update(buf, 0, 12);
		}

		long position = 12;
		long remaining = length - (12 + 20);
		while (0 < remaining) {
			pin(pack, position);

			int ptr = (int) (position - block.start);
			int n = (int) Math.min(block.size() - ptr, remaining);
			block.write(out, position, n, md);
			position += n;
			remaining -= n;
		}

		if (md != null) {
			byte[] buf = new byte[20];
			byte[] actHash = md.digest();

			pin(pack, position);
			if (block.copy(position, buf, 0, 20) != 20) {
				pack.setInvalid();
				throw new IOException(MessageFormat.format(
						JGitText.get().packfileIsTruncated, pack.getPackName()));
			}
			if (!Arrays.equals(actHash, buf)) {
				pack.setInvalid();
				throw new IOException(MessageFormat.format(
						JGitText.get().packfileCorruptionDetected,
						pack.getPackDescription().getFileName(PACK)));
			}
		}
	}

