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
					}
					retries++;
					continue;
				}
				throw e;
			} finally {
				br.close();
			}
