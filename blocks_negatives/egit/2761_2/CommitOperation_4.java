
			IProject project = file.getProject();
			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(project);
			Repository repository = repositoryMapping.getRepository();
			Tree projTree = treeMap.get(repository);
			if (projTree == null) {
				projTree = mapTree(repository, Constants.HEAD);
				if (projTree == null)
					projTree = new Tree(repository);
				treeMap.put(repository, projTree);
				if (GitTraceLocation.CORE.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.CORE.getLocation(),
			}
			GitIndex index = repository.getIndex();
			String repoRelativePath = repositoryMapping
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

			if (idxEntry != null) {
				projTree.addFile(repoRelativePath);
				TreeEntry newMember = projTree.findBlobMember(repoRelativePath);

				newMember.setId(idxEntry.getObjectId());
				if (newMember instanceof FileTreeEntry)
					((FileTreeEntry) newMember).setExecutable(
							(idxEntry.getModeBits() &
									FileMode.EXECUTABLE_FILE.getBits())
							== FileMode.EXECUTABLE_FILE.getBits());

				if (GitTraceLocation.CORE.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.CORE.getLocation(),
									+ idxEntry.getObjectId());
			}
