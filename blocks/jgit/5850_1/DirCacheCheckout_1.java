	private void keep(DirCacheEntry e
		if (e == null || FileMode.TREE.equals(e.getRawMode()))
			return;

		if (e.isSmudged() && i != null && f != null && i.idEqual(f)) {
			e.setLength(f.getEntryLength());
			e.setLastModified(f.getEntryLastModified());
		}

		builder.add(e);
