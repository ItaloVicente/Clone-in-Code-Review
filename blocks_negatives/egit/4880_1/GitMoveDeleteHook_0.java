		final DirCacheEditor sEdit = sCache.editor();
		sEdit.add(new DirCacheEditor.DeleteTree(sPath));
		final int sPathLen = sPath.length() == 0 ? sPath.length() : sPath
				.length() + 1;
		for (final DirCacheEntry se : sEnt) {
			final String p = se.getPathString().substring(sPathLen);
			sEdit.add(new DirCacheEditor.PathEdit(dPath + p) {
				@Override
				public void apply(final DirCacheEntry dEnt) {
					dEnt.copyMetaData(se);
				}
			});
