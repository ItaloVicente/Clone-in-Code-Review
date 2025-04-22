		if (resources.length == 1 && resources[0] instanceof IFile) {
			final IFile baseFile = (IFile) resources[0];
			final String gitPath = RepositoryMapping.getMapping(
					baseFile.getProject()).getRepoRelativePath(baseFile);
			final ITypedElement base = SaveableCompareEditorInput
					.createFileElement(baseFile);

			final ITypedElement next = CompareUtils.getHeadTypedElement(repository, gitPath);
			if (next == null)
				return null;

			final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
					base, next, null);
			CompareUI.openCompareEditor(in);
			return null;

		} else {
			CompareTreeView view;
			try {
				view = (CompareTreeView) PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage().showView(
								CompareTreeView.ID);
				try {
					Ref head = repository.getRef(Constants.HEAD);
					if (head == null || head.getObjectId() == null) {
						Shell shell = HandlerUtil.getActiveShell(event);
						MessageDialog.openInformation(shell,
								UIText.CompareWithHeadActionHandler_NoHeadTitle,
								UIText.CompareWithHeadActionHandler_NoHeadMessage);
					} else
						view.setInput(resources, Repository.shortenRefName(head.getTarget().getName()));
				} catch (IOException e) {
					Activator.handleError(e.getMessage(), e, true);
					return null;
				}
			} catch (PartInitException e) {
				Activator.handleError(e.getMessage(), e, true);
				return null;
			}
			return null;
