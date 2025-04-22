			@Override
			public void beforeEditorActivated(
					ColumnViewerEditorActivationEvent event) {
				ViewerCell cell = (ViewerCell) event.getSource();
				v.getTable().showColumn(v.getTable().getColumn(cell.getColumnIndex()));
			}
