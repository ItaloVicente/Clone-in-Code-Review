		if (d.getBlobs().length <= 2) {
			DiffViewer.showTwoWayFileDiff(d);
		} else {
			MessageDialog.openInformation(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getShell(),
					UIText.CommitFileDiffViewer_CanNotOpenCompareEditorTitle,
					UIText.CommitFileDiffViewer_MergeCommitMultiAncestorMessage);
		}
