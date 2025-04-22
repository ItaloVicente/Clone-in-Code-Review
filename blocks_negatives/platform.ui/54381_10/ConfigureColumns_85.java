			});
			text.addListener(SWT.Modify, new Listener() {
				@Override
				public void handleEvent(Event event) {
					ColumnObject columnObject = columnObjects[table.getSelectionIndex()];
					if (!columnObject.resizable) {
						return;
					}
					try {
						int width = Integer.parseInt(text.getText());
						columnObject.width = width;
					} catch (NumberFormatException ex) {
					}
