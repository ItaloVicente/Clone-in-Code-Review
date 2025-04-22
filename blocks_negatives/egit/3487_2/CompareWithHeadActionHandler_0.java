			final IFile baseFile = (IFile) resources[0];
			final String gitPath = RepositoryMapping.getMapping(
					baseFile.getProject()).getRepoRelativePath(baseFile);
			final ITypedElement base = SaveableCompareEditorInput
					.createFileElement(baseFile);

			ITypedElement next;
			try {
				Ref head = repository.getRef(Constants.HEAD);
				RevWalk rw = new RevWalk(repository);
				RevCommit commit = rw.parseCommit(head.getObjectId());

				next = CompareUtils.getFileRevisionTypedElement(gitPath,
						commit, repository);
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
				return null;
			}

			final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
					base, next, null);
			CompareUI.openCompareEditor(in);
