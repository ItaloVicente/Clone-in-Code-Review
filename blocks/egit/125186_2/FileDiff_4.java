					jgitProgress);
			if (!progress.isCanceled()) {
				progress.setWorkRemaining(renames.size());
				for (DiffEntry m : renames) {
					final FileDiff d = new FileDiff(repository, commit, m);
					r.add(d);
					for (Iterator<DiffEntry> i = xentries.iterator(); i
							.hasNext();) {
						DiffEntry n = i.next();
						if (m.getOldPath().equals(n.getOldPath()))
							i.remove();
						else if (m.getNewPath().equals(n.getNewPath()))
							i.remove();
					}
					progress.worked(1);
