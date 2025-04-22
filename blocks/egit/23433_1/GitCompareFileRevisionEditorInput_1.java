
	@Override
	public void registerContextMenu(MenuManager menu,
			final ISelectionProvider selectionProvider) {
		super.registerContextMenu(menu, selectionProvider);
		registerOpenWorkspaceVersion(menu, selectionProvider);
	}

	private void registerOpenWorkspaceVersion(MenuManager menu,
			final ISelectionProvider selectionProvider) {
		FileRevisionTypedElement leftRevision = getLeftRevision();
		if (leftRevision != null) {
			IFileRevision fileRevision = leftRevision.getFileRevision();
			if (fileRevision instanceof CommitFileRevision) {
				CommitFileRevision revision = (CommitFileRevision) fileRevision;
				final File workspaceFile = new File(revision.getRepository()
						.getWorkTree(), revision.getGitPath());
				if (workspaceFile.exists())
					menu.addMenuListener(new IMenuListener() {
						public void menuAboutToShow(IMenuManager manager) {
							Action action = new OpenWorkspaceVersionAction(
									UIText.CommitFileDiffViewer_OpenWorkingTreeVersionInEditorMenuLabel,
									selectionProvider, workspaceFile);
							manager.insertAfter("save", new Separator()); //$NON-NLS-1$
							manager.insertAfter("save", action); //$NON-NLS-1$
						}
					});
			}
		}
	}

	private class OpenWorkspaceVersionAction extends Action {

		private final ISelectionProvider selectionProvider;

		private final File workspaceFile;

		private OpenWorkspaceVersionAction(String text,
				ISelectionProvider selectionProvider, File workspaceFile) {
			super(text);
			this.selectionProvider = selectionProvider;
			this.workspaceFile = workspaceFile;
		}

		@Override
		public void run() {
			int selectedLine = 0;
			if (selectionProvider.getSelection() instanceof ITextSelection) {
				ITextSelection selection = (ITextSelection) selectionProvider
						.getSelection();
				selectedLine = selection.getStartLine();
			}

			IWorkbenchWindow window = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			IEditorPart editor = EgitUiEditorUtils.openEditor(workspaceFile,
					page);
			selectLine(editor, selectedLine);
		}

		private void selectLine(IEditorPart editorPart, int selectedLine) {
			if (editorPart instanceof ITextEditor) {
				ITextEditor editor = (ITextEditor) editorPart;
				IDocument document = editor.getDocumentProvider().getDocument(
						editor.getEditorInput());
				if (document != null)
					try {
						IRegion line = document
								.getLineInformation(selectedLine);
						editor.selectAndReveal(line.getOffset(), 0);
					} catch (BadLocationException e) {
					}
			}
		}
	}

