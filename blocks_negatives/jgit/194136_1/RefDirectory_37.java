		int maxStaleRetries = 5;
		int retries = 0;
		while (true) {
			final FileSnapshot snapshot = FileSnapshot.save(packedRefsFile);
			final MessageDigest digest = Constants.newMessageDigest();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(
					new DigestInputStream(new FileInputStream(packedRefsFile),
							digest),
					UTF_8))) {
				try {
					return new PackedRefList(parsePackedRefs(br), snapshot,
							ObjectId.fromRaw(digest.digest()));
				} catch (IOException e) {
					if (FileUtils.isStaleFileHandleInCausalChain(e)
							&& retries < maxStaleRetries) {
						if (LOG.isDebugEnabled()) {
							LOG.debug(MessageFormat.format(
									JGitText.get().packedRefsHandleIsStale,
									Integer.valueOf(retries)), e);
