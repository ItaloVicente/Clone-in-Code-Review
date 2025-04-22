				for (Object element : iss.toList())
					openThisVersionInEditor((FileDiff) element);
			}
		};

		openPreviousVersion = new Action(
				UIText.CommitFileDiffViewer_OpenPreviousInEditorMenuLabel) {
			@Override
			public void run() {
				final ISelection s = getSelection();
				if (s.isEmpty() || !(s instanceof IStructuredSelection))
					return;
				final IStructuredSelection iss = (IStructuredSelection) s;
				for (Object element : iss.toList())
					openPreviousVersionInEditor((FileDiff) element);
