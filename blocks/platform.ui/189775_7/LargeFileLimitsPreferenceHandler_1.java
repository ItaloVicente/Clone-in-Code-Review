	public interface PromptForEditor {

		void prompt(IPath inputPath, FileLimit fileLimit);
		IEditorDescriptor getSelectedEditor();
		boolean shouldRememberSelectedEditor();
	}

	private static class DialogPromptForEditor implements PromptForEditor {

		IEditorDescriptor selectedEditor = null;
		boolean rememberSelection = false;

		@Override
		public void prompt(IPath inputPath, FileLimit fileLimit) {
			Shell shell = ProgressManagerUtil.getDefaultParent();
			LargeFileEditorSelectionDialog dialog =
					new LargeFileEditorSelectionDialog(shell, inputPath.getFileExtension(), fileLimit.fileSize);
			dialog.setMessage(WorkbenchMessages.EditorManager_largeDocumentWarning);
			if (dialog.open() == Window.OK) {
				selectedEditor = dialog.getSelectedEditor();
				rememberSelection = dialog.shouldRememberSelectedEditor();
			}
		}

		@Override
		public IEditorDescriptor getSelectedEditor() {
			return selectedEditor;
		}

		@Override
		public boolean shouldRememberSelectedEditor() {
			return rememberSelection;
		}
	}

	public static class LargeFileEditorSelectionDialog extends EditorSelectionDialog {
