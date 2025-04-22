
		if (md != null) {
			byte[] buf = new byte[20];
			byte[] actHash = md.digest();

			pin(pack, position);
			if (window.copy(position, buf, 0, 20) != 20) {
				pack.setInvalid();
				throw new IOException(MessageFormat.format(
						JGitText.get().packfileIsTruncated, pack.getPackFile()
								.getPath()));
			}
			if (!Arrays.equals(actHash, buf)) {
				pack.setInvalid();
				throw new IOException(MessageFormat.format(
						JGitText.get().packfileCorruptionDetected, pack
								.getPackFile().getPath()));
			}
		}
