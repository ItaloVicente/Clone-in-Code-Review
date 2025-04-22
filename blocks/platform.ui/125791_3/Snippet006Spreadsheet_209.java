					protected void updateItem(int rowIndex, TableItem item, Object element) {
						if (DEBUG_LEVEL >= 1) {
							System.out.println("updating row " + rowIndex);
						}
						for (int j = 0; j < NUM_COLUMNS; j++) {
							item.setText(j, (String) cellValues[rowIndex][j]
									.getValue());
						}
