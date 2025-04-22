	private synchronized PackIndex idx() throws IOException {
		if (loadedIdx == null) {
			if (invalid)
				throw new PackInvalidException(packFile);

			try {
				final PackIndex idx = PackIndex.open(extFile(INDEX));

				if (packChecksum == null) {
					packChecksum = idx.packChecksum;
				} else if (!Arrays.equals(packChecksum, idx.packChecksum)) {
					throw new PackMismatchException(MessageFormat.format(
							JGitText.get().packChecksumMismatch,
							packFile.getPath(),
							ObjectId.fromRaw(packChecksum).name(),
							ObjectId.fromRaw(idx.packChecksum).name()));
