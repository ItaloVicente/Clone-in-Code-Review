		if(detectRenames) {
			renameResolver = new RenameResolver(db

			tw = new RenameProcessingTreeWalk(db
		} else {
			tw = new NameConflictTreeWalk(db
		}
		tw.addTree(baseTree == null? new EmptyTreeIterator(): openTree(baseTree));
