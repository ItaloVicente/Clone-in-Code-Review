			selectionChangedListener = new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					MultiPageEditorSite.this.handleSelectionChanged(event);
				}
			};
