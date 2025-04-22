				if (CompareUtils.canDirectlyOpenInCompare(file)) {
					final RepositoryMapping mapping = RepositoryMapping
							.getMapping(file.getProject());
					final String gitPath = mapping.getRepoRelativePath(file);
					ITypedElement right = CompareUtils.getFileRevisionTypedElement(
							gitPath, commit, mapping.getRepository());
					final ITypedElement ancestor = CompareUtils.
							getFileRevisionTypedElementForCommonAncestor(
							gitPath, headCommit, commit, repo);

					final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
							SaveableCompareEditorInput.createFileElement(file),
							right, ancestor, null);
					openInCompare(event, in);
				} else {
					try {
						GitModelSynchronize.synchronizeModelWithWorkspace(file,
								repo, commit.getName());
					} catch (IOException e) {
						throw new ExecutionException(e.getMessage(), e);
					}
				}
			}
			if (input instanceof File) {
				File file = (File) input;
				RevCommit leftCommit;
				RevWalk walk = new RevWalk(repo);
				try {
					leftCommit = walk.parseCommit(headCommit);
				} catch (Exception e) {
					throw new ExecutionException(e.getMessage(), e);
				} finally {
					walk.release();
				}
				final String gitPath = getRepoRelativePath(repo, file);
				ITypedElement left = CompareUtils.getFileRevisionTypedElement(
						gitPath, leftCommit, repo);
				ITypedElement right = CompareUtils.getFileRevisionTypedElement(
						gitPath, commit, repo);
				final ITypedElement ancestor = CompareUtils.
						getFileRevisionTypedElementForCommonAncestor(
						gitPath, headCommit, commit, repo);
				final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
						left, right, ancestor, null);
				openInCompare(event, in);
				return null;
			}
