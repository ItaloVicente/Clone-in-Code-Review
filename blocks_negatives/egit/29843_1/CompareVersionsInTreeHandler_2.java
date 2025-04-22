			HistoryPageInput pageInput = getPage(event).getInputInternal();
			Object input = pageInput.getSingleItem();
			Repository repository = pageInput.getRepository();
			IWorkbenchPage workBenchPage = HandlerUtil
					.getActiveWorkbenchWindowChecked(event).getActivePage();
			if (input instanceof IFile) {
				IFile resource = (IFile) input;
				final RepositoryMapping map = RepositoryMapping
						.getMapping(resource);
				final String gitPath = map.getRepoRelativePath(resource);

				final ITypedElement base = CompareUtils
						.getFileRevisionTypedElement(gitPath, commit1, map
								.getRepository());
				final ITypedElement next = CompareUtils
						.getFileRevisionTypedElement(gitPath, commit2, map
								.getRepository());
				CompareEditorInput in = new GitCompareFileRevisionEditorInput(
						base, next, null);
				CompareUtils.openInCompare(workBenchPage, in);
			} else if (input instanceof File) {
				File fileInput = (File) input;
				Repository repo = getRepository(event);
				final String gitPath = getRepoRelativePath(repo, fileInput);

				final ITypedElement base = CompareUtils
						.getFileRevisionTypedElement(gitPath, commit1, repo);
				final ITypedElement next = CompareUtils
						.getFileRevisionTypedElement(gitPath, commit2, repo);
				CompareEditorInput in = new GitCompareFileRevisionEditorInput(
						base, next, null);
				CompareUtils.openInCompare(workBenchPage, in);
			} else if (input instanceof IResource) {
				CompareTreeView view;
				try {
					view = (CompareTreeView) PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage()
							.showView(CompareTreeView.ID);
					view.setInput(new IResource[] { (IResource) input },
							commit1.getId().name(), commit2.getId().name());
				} catch (PartInitException e) {
					Activator.handleError(e.getMessage(), e, true);
				}
			} else if (input == null) {
				CompareTreeView view;
				try {
					view = (CompareTreeView) PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage()
							.showView(CompareTreeView.ID);
					view.setInput(repository, commit1.getId().name(), commit2
							.getId().name());
				} catch (PartInitException e) {
					Activator.handleError(e.getMessage(), e, true);
				}
			}
