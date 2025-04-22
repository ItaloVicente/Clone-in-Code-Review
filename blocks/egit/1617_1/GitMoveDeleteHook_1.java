	private boolean moveIndexContent(String dPath,
			final RepositoryMapping srcm, final String sPath) throws IOException {
		final DirCache sCache = srcm.getRepository().lockDirCache();
		final DirCacheEntry[] sEnt = sCache.getEntriesWithin(sPath);
		if (sEnt.length == 0) {
			sCache.unlock();
			return false;
		}

		final DirCacheEditor sEdit = sCache.editor();
		sEdit.add(new DirCacheEditor.DeleteTree(sPath));
		final int sPathLen = sPath.length() + 1;
		for (final DirCacheEntry se : sEnt) {
			final String p = se.getPathString().substring(sPathLen);
			sEdit.add(new DirCacheEditor.PathEdit(dPath + p) {
				@Override
				public void apply(final DirCacheEntry dEnt) {
					dEnt.copyMetaData(se);
				}
			});
		}
		return sEdit.commit();
	}

