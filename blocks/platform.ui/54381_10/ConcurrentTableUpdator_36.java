    Runnable uiRunnable = () -> {
	    updateScheduled = false;
	    if(!table.getControl().isDisposed()) {
			updateTable();
		}
	};
