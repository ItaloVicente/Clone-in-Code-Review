				newTree = new DirCacheIterator(db.readDirCache());
			} else if (oldTree == null) {
				oldTree = new DirCacheIterator(db.readDirCache());
				newTree = new FileTreeIterator(db);
			} else if (newTree == null) {
				newTree = new FileTreeIterator(db);
			}

			TextProgressMonitor pm = new TextProgressMonitor(errw);
			pm.setDelayStart(2, TimeUnit.SECONDS);
			diffFmt.setProgressMonitor(pm);
			diffFmt.setPathFilter(pathFilter);

			List<DiffEntry> files = diffFmt.scan(oldTree, newTree);
			ContentSource.Pair sourcePair = new ContentSource.Pair(
					source(oldTree), source(newTree));

			/*
			 * TODO: this only is for prototyping and will be removed with next
			 * commit:
			 */
			for (DiffEntry ent : files) {
				switch (ent.getChangeType()) {
				case MODIFY:

					ObjectStream newFileStream = sourcePair.open(Side.NEW, ent)
							.openStream();
					showStream(newFileStream);
					ObjectStream oldFileStream = sourcePair.open(Side.OLD, ent)
							.openStream();
					showStream(oldFileStream);

					diffToolMgr.compare(ent.getNewPath(),
							ent.getOldPath(), ent.getNewId().name(),
							ent.getOldId().name(), toolName, prompt, gui,
							trustExitCode);
					break;
				default:
					break;
