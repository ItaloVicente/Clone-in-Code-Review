	private PackIndex idx() throws IOException {
		PackIndex idx = loadedIdx;
		if (idx == null) {
			synchronized (this) {
				idx = loadedIdx;
				if (idx == null) {
					if (invalid) {
						throw new PackInvalidException(packFile);
					}
					try {
						idx = PackIndex.open(extFile(INDEX));

						if (packChecksum == null) {
							packChecksum = idx.packChecksum;
						} else if (!Arrays.equals(packChecksum
								idx.packChecksum)) {
							throw new PackMismatchException(MessageFormat
									.format(JGitText.get().packChecksumMismatch
											packFile.getPath()
											ObjectId.fromRaw(packChecksum)
													.name()
											ObjectId.fromRaw(idx.packChecksum)
													.name()));
						}
						loadedIdx = idx;
					} catch (InterruptedIOException e) {
						throw e;
					} catch (IOException e) {
						invalid = true;
						throw e;
					}
