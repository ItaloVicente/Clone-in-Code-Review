		if (db != null) {
			allRefs = getBranches(db);
			refsChangedListener = db.getListenerList().addRefsChangedListener(
					new RefsChangedListener() {

						public void onRefsChanged(RefsChangedEvent event) {
							allRefs = getBranches(db);
						}
					});
		}
