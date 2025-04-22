		int maxStaleRetries = 5;
		int retries = 0;
		while (true) {
			final FileSnapshot snapshot = FileSnapshot.save(packedRefsFile);
			final BufferedReader br;
			final MessageDigest digest = Constants.newMessageDigest();
			try {
				br = new BufferedReader(new InputStreamReader(
						new DigestInputStream(new FileInputStream(packedRefsFile)
								digest)
			} catch (FileNotFoundException noPackedRefs) {
				return PackedRefList.NO_PACKED_REFS;
			}
			try {
				return new PackedRefList(parsePackedRefs(br)
						ObjectId.fromRaw(digest.digest()));
			} catch (IOException e) {
				if (FileUtils.isStaleFileHandle(e) && retries < maxStaleRetries) {
					if (LOG.isDebugEnabled()) {
						LOG.debug(MessageFormat.format(
								JGitText.get().packedRefsHandleIsStale
								Integer.valueOf(retries))
					}
					retries++;
					continue;
				}
				throw e;
			} finally {
				br.close();
			}
