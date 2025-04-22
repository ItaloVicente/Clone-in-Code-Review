	private void addNonResourceActions(IMenuManager menuMgr,
			final IStructuredSelection selection) {
		menuMgr.add(new Action(UIText.StagingView_replaceWithFileInGitIndex) {
			@Override
			public void run() {
				boolean performAction = MessageDialog.openConfirm(form.getShell(),
						UIText.DiscardChangesAction_confirmActionTitle,
						UIText.DiscardChangesAction_confirmActionMessage);
				if (!performAction)
					return ;
				String[] files = getSelectedFiles(selection);
				replaceWithFileInGitIndex(files);
			}
		});

	}

	private void replaceWithFileInGitIndex(String[] files) {
		if (files == null || files.length == 0)
			return;
		CheckoutCommand checkoutCommand = new Git(currentRepository).checkout();
		for (String path : files)
			checkoutCommand.addPath(path);
		try {
			checkoutCommand.call();
		} catch (Exception e) {
			Activator.handleError(UIText.StagingView_checkoutFailed, e, true);
		}
	}

	private String[] getSelectedFiles(IStructuredSelection selection) {
		List<String> result = new ArrayList<String>();
		Iterator iterator = selection.iterator();
		while (iterator.hasNext()) {
			StagingEntry stagingEntry = (StagingEntry) iterator.next();
			result.add(stagingEntry.getPath());
		}
		return result.toArray(new String[result.size()]);
	}

	private boolean isNonResourceSelection(ISelection selection) {
		if (!(selection instanceof IStructuredSelection))
			return false;
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		Iterator iterator = structuredSelection.iterator();
		while (iterator.hasNext()) {
			Object selectedObject = iterator.next();
			if (!(selectedObject instanceof StagingEntry))
				return false;
			StagingEntry stagingEntry = (StagingEntry) selectedObject;
			String path = currentRepository.getWorkTree() + "/" + stagingEntry.getPath(); //$NON-NLS-1$
			if (getResource(path) != null)
				return false;
		}
		return true;
	}

	private IFile getResource(String path) {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = root.getFileForLocation(new Path(path));
		if (file == null)
			return null;
		if (file.getProject().isAccessible())
			return file;
		return null;
	}

