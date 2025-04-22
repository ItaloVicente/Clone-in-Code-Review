
			Object input = getPage(event).getInputInternal().getSingleItem();
			Repository repo = getRepository(event);
			IWorkbenchPage workBenchPage = HandlerUtil
					.getActiveWorkbenchWindowChecked(event).getActivePage();
			if (input instanceof IFile) {
				IResource[] resources = new IResource[] { (IFile) input, };
				try {
					CompareUtils.compare(resources, repo, commit1.getName(),
							commit2.getName(), false, workBenchPage);
				} catch (IOException e) {
					Activator.handleError(
							UIText.CompareWithRefAction_errorOnSynchronize, e,
							true);
				}
			} else if (input instanceof File) {
				File fileInput = (File) input;
				IPath location = new Path(fileInput.getAbsolutePath());
				try {
					CompareUtils.compare(location, repo, commit1.getName(),
							commit2.getName(), false, workBenchPage);
				} catch (IOException e) {
					Activator.handleError(
							UIText.CompareWithRefAction_errorOnSynchronize, e,
							true);
				}
			} else if (input instanceof IResource) {
				GitCompareEditorInput compareInput = new GitCompareEditorInput(
						commit1.name(), commit2.name(), (IResource) input);
				CompareUtils.openInCompare(workBenchPage, compareInput);
			} else if (input == null) {
				GitCompareEditorInput compareInput = new GitCompareEditorInput(
						commit1.name(), commit2.name(), repo);
				CompareUtils.openInCompare(workBenchPage, compareInput);
			}
