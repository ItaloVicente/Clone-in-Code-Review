        sourceViewer.addSelectionChangedListener(new ISelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						if (cutAction != null)
							cutAction.update();
						copyAction.update();
					}

				});
