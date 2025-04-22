			postSelectionChangedListener = new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					MultiPageEditorSite.this.handlePostSelectionChanged(event);
				}
			};
