		NameConflictTreeWalk tw = new NameConflictTreeWalk(db);
		tw.setRecursive(true);
		tw.reset();
		tw.addTree(new DirCacheIterator(dc));
		while (tw.next()) {
			DirCacheIterator dcIt = tw.getTree(0, DirCacheIterator.class);
			sb.append("["+tw.getPathString()+", mode:" + dcIt.getEntryFileMode());
			int stage = dcIt.getDirCacheEntry().getStage();
