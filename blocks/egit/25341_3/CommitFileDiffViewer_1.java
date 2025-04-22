		boolean deleteSelected = sel.size() == 1
				&& ((FileDiff) sel.getFirstElement()).getChange() == ChangeType.DELETE;
		if (deleteSelected)
			open.setText(UIText.CommitFileDiffViewer_OpenPreviousInEditorMenuLabel);
		else
			open.setText(UIText.CommitFileDiffViewer_OpenInEditorMenuLabel);

