					.getRepoRelativePath(file);
			String string = repoRelativePath;

			TreeEntry treeMember = projTree.findBlobMember(repoRelativePath);
			Tree treeWithDeletedEntry = null;
			if (treeMember != null) {
				treeWithDeletedEntry = treeMember.getParent();
				treeMember.delete();
			}

			Entry idxEntry = index.getEntry(string);
			if (notIndexed.contains(file)) {
				File thisfile = new File(repositoryMapping.getWorkTree(),
						string);
				if (!thisfile.isFile()) {
					index.remove(repositoryMapping.getWorkTree(), thisfile);
					if (GitTraceLocation.CORE.isActive())
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.CORE.getLocation(),
								"Phantom file, so removing from index"); //$NON-NLS-1$
					while (treeWithDeletedEntry != null && treeWithDeletedEntry.memberCount() == 0) {
						Tree toDelete = treeWithDeletedEntry;
						treeWithDeletedEntry = treeWithDeletedEntry.getParent();
						toDelete.delete();
					}
					continue;
				} else {
					idxEntry.update(thisfile);
				}
			}
			if (notTracked.contains(file)) {
				idxEntry = index.add(repositoryMapping.getWorkTree(), new File(
						repositoryMapping.getWorkTree(), repoRelativePath));

			}
