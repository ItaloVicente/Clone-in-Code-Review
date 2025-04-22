
	public String indexState(TreeSet<Long> modTimes)
			throws IllegalStateException
			IncorrectObjectTypeException
		DirCache dc = db.readDirCache();
		Map lookup = new HashMap();
		List ret = new ArrayList(dc.getEntryCount());
		NameConflictTreeWalk tw = new NameConflictTreeWalk(db);
		tw.reset();
		tw.addTree(new FileTreeIteratorWithTimeControl(db
		tw.addTree(new DirCacheIterator(dc));
		boolean smudgedBefore;
		while (tw.next()) {
			List entry = new ArrayList(4);
			FileTreeIteratorWithTimeControl fIt = tw.getTree(0
					FileTreeIteratorWithTimeControl.class);
			DirCacheIterator dcIt = tw.getTree(1
			entry.add(tw.getPathString());
			entry.add("modTime(index/file): "
					+ ((dcIt == null) ? "null" : lookup(Long.valueOf(dcIt
							.getDirCacheEntry().getLastModified())
							lookup))
					+ "/"
					+ ((fIt == null) ? "null" : lookup(
							Long.valueOf(fIt.getEntryLastModified())
							lookup)));
			smudgedBefore = (dcIt == null) ? false : dcIt.getDirCacheEntry()
					.isSmudged();
			if (fIt != null
					&& dcIt != null
					&& fIt.isModified(dcIt.getDirCacheEntry()
							db.getFS()))
				entry.add("dirty");
			if (dcIt != null && dcIt.getDirCacheEntry().isSmudged())
				entry.add("smudged");
			else if (smudgedBefore)
				entry.add("unsmudged");
			ret.add(entry);
		}
		return ret.toString();
	}

	public static String lookup(Object l
			Map<Object
		String name = lookupTable.get(l);
		if (name == null) {
			name = nameTemplate.replaceAll("%n"
					Integer.toString(lookupTable.size()));
			lookupTable.put(l
		}
		return name;
	}
