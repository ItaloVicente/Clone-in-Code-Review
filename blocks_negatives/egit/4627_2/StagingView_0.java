		unstagedTableViewer.addDragSupport(DND.DROP_MOVE,
				new Transfer[] { LocalSelectionTransfer.getTransfer() },
				new DragSourceAdapter() {
					public void dragStart(DragSourceEvent event) {
						IStructuredSelection selection = (IStructuredSelection) unstagedTableViewer
								.getSelection();
						event.doit = !selection.isEmpty();
					}
				});
