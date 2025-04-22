		fileViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				List<FileDiff> diffs = new ArrayList<FileDiff>();
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection sel = (IStructuredSelection) selection;
					for (Object obj : sel.toList())
						if (obj instanceof FileDiff)
							diffs.add((FileDiff) obj);
				}
				formatDiffs(diffs);
			}
		});

