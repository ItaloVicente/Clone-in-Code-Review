		while (tw.next()) {
			if (!processEntry(
					tw.getTree(T_BASE, CanonicalTreeParser.class),
					tw.getTree(T_OURS, CanonicalTreeParser.class),
					tw.getTree(T_THEIRS, CanonicalTreeParser.class),
					tw.getTree(T_INDEX, DirCacheBuildIterator.class),
					(workingTreeIterator == null) ? null : tw.getTree(T_FILE,
							WorkingTreeIterator.class))) {
				cleanUp();
				return false;
			}
			if (tw.isSubtree() && enterSubtree)
				tw.enterSubtree();
