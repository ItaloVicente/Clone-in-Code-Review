			List<DiffEntry> xentries = new LinkedList<DiffEntry>(entries);
			RenameDetector detector = new RenameDetector(repository);
			detector.addAll(entries);
			List<DiffEntry> renames = detector.compute(walk.getObjectReader(),
					org.eclipse.jgit.lib.NullProgressMonitor.INSTANCE);
			for (DiffEntry m : renames) {
				final FileDiff d = new FileDiff(commit, m);
				r.add(d);
				for (Iterator<DiffEntry> i = xentries.iterator(); i.hasNext();) {
					DiffEntry n = i.next();
					if (m.getOldPath().equals(n.getOldPath())) {
						i.remove();
					} else if (m.getNewPath().equals(n.getNewPath())) {
						i.remove();
					}
				}
			}
			for (DiffEntry m : xentries) {
				final FileDiff d = new FileDiff(commit, m);
