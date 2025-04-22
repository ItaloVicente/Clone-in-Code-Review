
		createUnstageMenu();
		createStagedMenu();
	}

	private void createUnstageMenu() {
		final MenuManager mgr = new MenuManager();
		Control c = unstagedTableViewer.getControl();
		c.setMenu(mgr.createContextMenu(c));
		mgr.add(new CommandAction(getSite(), ActionCommands.DISCARD_CHANGES_ACTION));
		mgr.add(new CommandAction(getSite(), ActionCommands.ADD_TO_INDEX));
		mgr.add(new CommandAction(getSite(), ActionCommands.COMPARE_WITH_INDEX_ACTION));
		mgr.add(new CommandAction(getSite(), ActionCommands.SHOW_HISTORY));
		mgr.add(mergeToolAction = new CommandAction(getSite(), ActionCommands.MERGE_TOOL_ACTION));
		mgr.add(new Action(
				UIText.CommitFileDiffViewer_OpenWorkingTreeVersionInEditorMenuLabel) {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				final ISelection s = unstagedTableViewer.getSelection();
				if (s.isEmpty() || !(s instanceof IStructuredSelection))
					return;
				final IStructuredSelection iss = (IStructuredSelection) s;
				for (Iterator<StagingEntry> it = iss.iterator(); it.hasNext();) {
					String relativePath = it.next().getPath();
					String path = new Path(currentRepository.getWorkTree()
							.getAbsolutePath()).append(relativePath)
							.toOSString();
					openFileInEditor(path);
				}
			}
		});

		unstagedTableViewer.addPostSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection.isEmpty())
					return;
				StagingEntry stagingEntry = (StagingEntry) selection.getFirstElement();
				mergeToolAction.setEnabled(stagingEntry.getState() == StagingEntry.State.CONFLICTING);
			}
		});
	}

	private void openFileInEditor(String filePath) {
		IWorkbenchWindow window = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow();
		File file = new File(filePath);
		if (!file.exists()) {
			String message = NLS.bind(UIText.CommitFileDiffViewer_FileDoesNotExist, filePath);
			Activator.showError(message, null);
		}
		IWorkbenchPage page = window.getActivePage();
		EgitUiEditorUtils.openEditor(file, page);
	}

	private void createStagedMenu() {
		final MenuManager mgr = new MenuManager();
		Control c = stagedTableViewer.getControl();
		c.setMenu(mgr.createContextMenu(c));
		mgr.add(new CommandAction(getSite(), ActionCommands.DISCARD_CHANGES_ACTION));	// reset?
		mgr.add(new CommandAction(getSite(), ActionCommands.COMPARE_WITH_HEAD_ACTION));
		mgr.add(new CommandAction(getSite(), ActionCommands.SHOW_HISTORY));
		mgr.add(new Action(
				UIText.CommitFileDiffViewer_OpenWorkingTreeVersionInEditorMenuLabel) {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				final ISelection s = stagedTableViewer.getSelection();
				if (s.isEmpty() || !(s instanceof IStructuredSelection))
					return;
				final IStructuredSelection iss = (IStructuredSelection) s;
				for (Iterator<StagingEntry> it = iss.iterator(); it.hasNext();) {
					String relativePath = it.next().getPath();
					String path = new Path(currentRepository.getWorkTree()
							.getAbsolutePath()).append(relativePath)
							.toOSString();
					openFileInEditor(path);
				}
			}
		});
