
	public String indexState(TreeSet<Long> modTimes)
			throws IllegalStateException
			IncorrectObjectTypeException
		DirCache dc = DirCache.read(db);
		Map lookup = new HashMap();
		List ret = new ArrayList(dc.getEntryCount());
		NameConflictTreeWalk tw = new NameConflictTreeWalk(db);
		tw.reset();
		tw.addTree(new FileTreeIteratorWithTimeControl(db
		tw.addTree(new DirCacheIterator(dc));
		FileTreeIteratorWithTimeControl fIt;
		DirCacheIterator dcIt;
		List entry;
		boolean smudgedBefore;
		while (tw.next()) {
			entry = new ArrayList(4);
			fIt = tw.getTree(0
			dcIt = tw.getTree(1
			entry.add(tw.getPathString());
			entry.add("modTime(index/file): "
					+ lookup(Long.valueOf(fIt.getEntryLastModified())
							lookup)
					+ "/"
					+ lookup(Long.valueOf(dcIt.getDirCacheEntry()
							.getLastModified())
			smudgedBefore = dcIt.getDirCacheEntry().isSmudged();
			if (fIt.isModified(dcIt.getDirCacheEntry()
				entry.add("dirty");
			if (dcIt.getDirCacheEntry().isSmudged())
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
