			List entry = new ArrayList(4);
			FileTreeIteratorWithTimeControl fIt = tw.getTree(0,
					FileTreeIteratorWithTimeControl.class);
			DirCacheIterator dcIt = tw.getTree(1, DirCacheIterator.class);
			entry.add(tw.getPathString());
			entry.add("modTime(index/file): "
					+ ((dcIt == null) ? "null" : lookup(Long.valueOf(dcIt
							.getDirCacheEntry().getLastModified()), "t%n",
							lookup))
					+ "/"
					+ ((fIt == null) ? "null" : lookup(
							Long.valueOf(fIt.getEntryLastModified()), "t%n",
							lookup)));
			smudgedBefore = (dcIt == null) ? false : dcIt.getDirCacheEntry()
					.isSmudged();
			if (fIt != null
					&& dcIt != null
					&& fIt.isModified(dcIt.getDirCacheEntry(), true, true,
							db.getFS()))
				entry.add("dirty");
			if (dcIt != null && dcIt.getDirCacheEntry().isSmudged())
				entry.add("smudged");
			else if (smudgedBefore)
				entry.add("unsmudged");
			ret.add(entry);
