			}

			new TableUpdater(table, list) {
				@Override
				protected void updateItem(int rowIndex, TableItem item, Object element) {
					if (DEBUG_LEVEL >= 1) {
						System.out.println("updating row " + rowIndex);
					}
