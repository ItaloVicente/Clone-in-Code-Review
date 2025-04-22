		exercise(() -> {
			contentProvider.refreshElements();
			startMeasuring();
			contentProvider.cloneElements();
			contentProvider.preSortElements(viewer, sorter);
			viewer.refresh();
			processEvents();
			stopMeasuring();
