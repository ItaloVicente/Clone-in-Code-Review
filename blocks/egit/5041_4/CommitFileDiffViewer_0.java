		compareWorkingTreeVersion = new Action(
				UIText.CommitFileDiffViewer_CompareWorkingDirectoryMenuLabel) {
			@Override
			public void run() {
				final ISelection s = getSelection();
				if (s.isEmpty() || !(s instanceof IStructuredSelection))
					return;
				final IStructuredSelection iss = (IStructuredSelection) s;
				showWorkingDirectoryFileDiff((FileDiff) iss.getFirstElement());
			}
		};

