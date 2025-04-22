			try (BufferedReader br = new BufferedReader(new InputStreamReader(
					new DigestInputStream(new FileInputStream(packedRefsFile)
							digest)
					CHARSET))) {
				try {
					return new PackedRefList(parsePackedRefs(br)
							ObjectId.fromRaw(digest.digest()));
				} catch (IOException e) {
					if (FileUtils.isStaleFileHandleInCausalChain(e)
							&& retries < maxStaleRetries) {
						if (LOG.isDebugEnabled()) {
							LOG.debug(MessageFormat.format(
									JGitText.get().packedRefsHandleIsStale
									Integer.valueOf(retries))
						}
						retries++;
						continue;
					}
					throw e;
				}
