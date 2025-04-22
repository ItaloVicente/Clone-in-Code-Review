					public final void selectionChanged(
							final SelectionChangedEvent event) {
						BusyIndicator.showWhile(fFilteredTree.getViewer()
								.getTree().getDisplay(), new Runnable() {
							@Override
							public void run() {
								SchemeElement scheme = (SchemeElement) ((IStructuredSelection) event
										.getSelection()).getFirstElement();
								keyController.getSchemeModel()
										.setSelectedElement(scheme);
							}
						});
