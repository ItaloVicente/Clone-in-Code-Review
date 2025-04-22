				if (path.length() > 0) {
					TreeWalk walk = new TreeWalk(rw.getObjectReader());
					walk.addTree(previousCommit.getTree());
					walk.addTree(headCommit.getTree());
					List<DiffEntry> entries = DiffEntry.scan(walk);
					boolean isEdit = false;
					for (DiffEntry diff : entries)
						if (diff.getChangeType() == ChangeType.MODIFY
								&& path.equals(diff.getNewPath())) {
							isEdit = true;
							break;
						}
					if (!isEdit && entries.size() >= 2) {
						RenameDetector detector = new RenameDetector(repository);
						detector.addAll(entries);
						List<DiffEntry> renames = detector.compute(
								walk.getObjectReader(),
								NullProgressMonitor.INSTANCE);
						for (DiffEntry diff : renames)
							if (diff.getChangeType() == ChangeType.RENAME
									&& path.equals(diff.getNewPath())) {
								path = diff.getOldPath();
								break;
							}
					}
				}

				return new PreviousCommit(previousCommit, path);
