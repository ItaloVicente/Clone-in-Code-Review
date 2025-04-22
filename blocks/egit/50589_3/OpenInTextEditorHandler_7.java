			if (map != null) {
				gitPath = map.getRepoRelativePath(resource);
				Iterator<?> it = selection.iterator();
				while (it.hasNext()) {
					RevCommit commit = (RevCommit) it.next();
					String commitPath = getRenamedPath(gitPath, commit);
					IFileRevision rev = null;
