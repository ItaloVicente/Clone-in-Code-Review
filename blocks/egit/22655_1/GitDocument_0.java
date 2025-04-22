				DiffConfig diffConfig = repository.getConfig().get(
						DiffConfig.KEY);
				if (diffConfig.getRenameDetectionType() != RenameDetectionType.FALSE) {
					TreeWalk walk = new TreeWalk(repository);
					CanonicalTreeParser baseLineIterator = new CanonicalTreeParser();
					baseLineIterator.reset(reader, baselineCommit.getTree());
					walk.addTree(baseLineIterator);
					walk.addTree(new DirCacheIterator(repository.readDirCache()));
					List<DiffEntry> diffs = DiffEntry.scan(walk, true);
					RenameDetector renameDetector = new RenameDetector(
							repository);
					renameDetector.addAll(diffs);
					List<DiffEntry> renames = renameDetector.compute();
					for (DiffEntry e : renames) {
						if (e.getNewPath().equals(gitPath)) {
							oldPath = e.getOldPath();
							break;
						}
