				stats.reusedPacks = Collections.unmodifiableList(cachedPacks);
				for (CachedPack pack : unwrittenCachedPacks) {
					long deltaCnt = pack.getDeltaCount();
					stats.reusedObjects += pack.getObjectCount();
					stats.reusedDeltas += deltaCnt;
					stats.totalDeltas += deltaCnt;
					reuseSupport.copyPackAsIs(out
				}
				writeChecksum(out);
				out.flush();
			} catch (IOException ioe) {
				if (FileUtils.isStaleFileHandleInCausalChain(ioe) &&
						writePackAttempts < MAX_WRITE_PACK_ATTEMPTS) {
					requireNonNull(reuseSupport).getCachedPacksAndUpdate(haveObjects);
					writePackAttempts++;
					continue WRITEPACK;
				}
			} finally {
				stats.timeWriting = System.currentTimeMillis() - writeStart;
				stats.depth = depth;
