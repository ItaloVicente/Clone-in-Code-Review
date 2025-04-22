				config.setLeftLabel(UIText.GitMergeEditorInput_WorkspaceHeader);

			if (ancestorCommit != null)
				config.setAncestorLabel(NLS.bind(LABELPATTERN, ancestorCommit
						.getShortMessage(), CompareUtils.truncatedRevision(ancestorCommit.name())));

			setTitle(NLS.bind(UIText.GitMergeEditorInput_MergeEditorTitle,
					new Object[] {
							Activator.getDefault().getRepositoryUtil()
									.getRepositoryName(repo),
							rightCommit.getShortMessage(), fullBranch }));

			try {
				return buildDiffContainer(repo, headCommit,
						ancestorCommit, filterPaths, rw, monitor);
			} catch (IOException e) {
				throw new InvocationTargetException(e);
			}
		} finally {
			if (rw != null)
				rw.dispose();
			monitor.done();
