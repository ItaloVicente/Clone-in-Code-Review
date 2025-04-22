				final IStructuredSelection iss = (IStructuredSelection) s;
				final FileDiff d = (FileDiff) iss.getFirstElement();
				if (d.getBlobs().length <= 2)
					showTwoWayFileDiff(d);
				else
					MessageDialog
							.openInformation(
									PlatformUI.getWorkbench()
											.getActiveWorkbenchWindow()
											.getShell(),
									UIText.CommitFileDiffViewer_CanNotOpenCompareEditorTitle,
									UIText.CommitFileDiffViewer_MergeCommitMultiAncestorMessage);
