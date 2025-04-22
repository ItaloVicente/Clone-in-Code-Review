		allRefs = getBranches();
		if (refsChangedListener != null)
			refsChangedListener.remove();
		refsChangedListener = db.getListenerList().addRefsChangedListener(new RefsChangedListener() {

			public void onRefsChanged(RefsChangedEvent event) {
				allRefs = getBranches();
			}
		});
