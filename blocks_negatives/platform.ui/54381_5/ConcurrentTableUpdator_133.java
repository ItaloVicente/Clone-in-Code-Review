    Runnable uiRunnable = new Runnable() {
        @Override
		public void run() {
            updateScheduled = false;
            if(!table.getControl().isDisposed()) {
				updateTable();
			}
        }
    };
