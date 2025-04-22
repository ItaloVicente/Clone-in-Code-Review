		open = new Action(UIText.CommitFileDiffViewer_OpenInEditorMenuLabel) {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				final ISelection s = getSelection();
				if (s.isEmpty() || !(s instanceof IStructuredSelection))
					return;
				final IStructuredSelection iss = (IStructuredSelection) s;
				for (Iterator<FileDiff> it = iss.iterator();; it.hasNext()) {
					openFileInEditor(it.next());
				}
			}
		};

		compare = new Action(UIText.CommitFileDiffViewer_CompareMenuLabel) {
			@Override
			public void run() {
				final ISelection s = getSelection();
				if (s.isEmpty() || !(s instanceof IStructuredSelection))
					return;
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
			}
		};

		mgr.add(open);
		mgr.add(compare);

