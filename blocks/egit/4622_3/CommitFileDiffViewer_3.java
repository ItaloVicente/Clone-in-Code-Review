		blame = new Action(
				UIText.CommitFileDiffViewer_ShowAnnotationsMenuLabel,
				UIIcons.ANNOTATE) {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				final ISelection s = getSelection();
				if (s.isEmpty() || !(s instanceof IStructuredSelection))
					return;
				final IStructuredSelection iss = (IStructuredSelection) s;
				for (Iterator<FileDiff> it = iss.iterator(); it.hasNext();)
					showAnnotations(it.next());
			}
		};

