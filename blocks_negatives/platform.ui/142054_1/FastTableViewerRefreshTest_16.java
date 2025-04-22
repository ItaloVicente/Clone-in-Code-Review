		exercise(
				new TestRunnable() {
					@Override
					public void run() {
						startMeasuring();
						for (int i = 0; i < 10; i++) {
							TableItem[] items = viewer.getTable().getItems();
							for (int j = 0; j < items.length; j++) {
								TableItem item = items[j];
								Object element = RefreshTestContentProvider.allElements[j];
								viewer.testUpdateItem(item, element);
							}
							processEvents();
						}
