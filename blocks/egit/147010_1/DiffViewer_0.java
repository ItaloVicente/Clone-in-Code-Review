		ObjectId[] blobs = d.getBlobs();
		if (blobs.length > 2) {
			MessageDialog.openInformation(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getShell(),
					UIText.CommitFileDiffViewer_CanNotOpenCompareEditorTitle,
					UIText.CommitFileDiffViewer_MergeCommitMultiAncestorMessage);
			return;
		}

