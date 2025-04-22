		TreeViewerEditor.create(viewer,
				new ColumnViewerEditorActivationStrategy(viewer) {
					@Override
					protected boolean isEditorActivationEvent(
							ColumnViewerEditorActivationEvent event) {
						return event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
					}
				}, ColumnViewerEditor.DEFAULT);
