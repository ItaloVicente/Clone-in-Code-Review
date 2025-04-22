	private void createPopupMenu(final TableViewer tableViewer) {
		final MenuManager menuMgr = new MenuManager();
		Control control = tableViewer.getControl();
		control.setMenu(menuMgr.createContextMenu(control));
		control.addMenuDetectListener(new MenuDetectListener() {
			public void menuDetected(MenuDetectEvent e) {
				menuMgr.removeAll();

				IStructuredSelection selection = (IStructuredSelection) tableViewer.getSelection();
				if (selection.isEmpty())
					return;

				menuMgr.add(new Action(
						UIText.CommitFileDiffViewer_OpenWorkingTreeVersionInEditorMenuLabel) {
							@Override
							public void run() {
								openSelectionInEditor(tableViewer.getSelection());
							}
						});

				StagingEntry stagingEntry = (StagingEntry) selection.getFirstElement();
				switch (stagingEntry.getState()) {
				case ADDED:
				case CHANGED:
				case REMOVED:
					menuMgr.add(new Action(UIText.StagingView_UnstageItemMenuLabel) {
						@Override
						public void run() {
							unstage((IStructuredSelection) tableViewer.getSelection());
						}
					});
					break;

				case CONFLICTING:
					menuMgr.add(createItem(ActionCommands.MERGE_TOOL_ACTION, tableViewer));
				case MISSING:
				case MODIFIED:
				case PARTIALLY_MODIFIED:
				case UNTRACKED:
				default:
					menuMgr.add(createItem(ActionCommands.DISCARD_CHANGES_ACTION, tableViewer));	// replace with index
					menuMgr.add(createItem(ActionCommands.ADD_TO_INDEX, tableViewer));
				}
			}
		});

	}

	@SuppressWarnings("unchecked")
	private void openSelectionInEditor(ISelection s) {
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

	private CommandContributionItem createItem(String itemAction, final TableViewer tableViewer) {
		IWorkbench workbench = PlatformUI.getWorkbench();
		CommandContributionItemParameter itemParam = new CommandContributionItemParameter(
				workbench, null, itemAction, STYLE_PUSH);

		IWorkbenchWindow activeWorkbenchWindow = workbench
				.getActiveWorkbenchWindow();
		IHandlerService hsr = (IHandlerService) activeWorkbenchWindow
				.getService(IHandlerService.class);
		IEvaluationContext ctx = hsr.getCurrentState();
		ctx.addVariable(ACTIVE_MENU_SELECTION_NAME, tableViewer.getSelection());
