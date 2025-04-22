		@Override
		public byte[] toByteArray(int limit) throws IOException {
			if (onDiskFile == null) {
				return super.toByteArray(limit);
			}
			final long len = Math.min(length()
			if (Integer.MAX_VALUE < len) {
				throw new OutOfMemoryError(
						JGitText.get().lengthExceedsMaximumArraySize);
			}
			final byte[] out = new byte[(int) len];
			try (FileInputStream in = new FileInputStream(onDiskFile)) {
				int read = 0;
				int chunk;
				while ((chunk = in.read(out
					read += chunk;
					if (read == out.length) {
						break;
					}
				}
			}
			return out;
		}

