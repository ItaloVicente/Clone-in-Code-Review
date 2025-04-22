		repository.getListenerList().addRefsChangedListener(
				new RefsChangedListener() {
					public void onRefsChanged(RefsChangedEvent event) {
						scheduleReloadJob();
					}
				});
