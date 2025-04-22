	/**
	 * Get the pack files stored in this cache.
	 *
	 * @return a collection of pack files, some of which may not actually be
	 *             present; the caller should check the pack's cached size.
	 */
	public Collection<DfsPackFile> getPackFiles() {
		return packFiles;
	}

	DfsPackFile getOrCreate(DfsPackDescription dsc, DfsStreamKey key) {

		DfsPackFile pack = packCache.get(dsc);
		if (pack != null && !pack.invalid()) {
			return pack;
		}

		return packCache.compute(dsc, (k, v) -> {
			} else {
				return new DfsPackFile(
						this, dsc, key != null ? key : new DfsStreamKey());
			}
		});
	}

