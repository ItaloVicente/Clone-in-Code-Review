			IEditorDescriptor editor = null;
			Shell shell = ProgressManagerUtil.getDefaultParent();
			LargeFileEditorSelectionDialog dialog = new LargeFileEditorSelectionDialog(shell, inputPath.getFileExtension(), fileLimit.fileSize);
			dialog.setMessage(WorkbenchMessages.EditorManager_largeDocumentWarning);
			if (dialog.open() == Window.OK) {
				editor = dialog.getSelectedEditor();
			}
