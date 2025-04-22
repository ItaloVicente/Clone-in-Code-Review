		indexChangedListenerHandle = repository.getListenerList().addIndexChangedListener(
				new IndexChangedListener() {
					@Override
					public void onIndexChanged(IndexChangedEvent event) {
						refreshIndexDelta();
					}
				});
		refsChangedListenerHandle = repository.getListenerList().addRefsChangedListener(
				new RefsChangedListener() {
					@Override
					public void onRefsChanged(RefsChangedEvent event) {
					}
				});

