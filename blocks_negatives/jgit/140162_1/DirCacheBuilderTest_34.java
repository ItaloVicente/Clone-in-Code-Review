		listener = new IndexChangedListener() {

			@Override
			public void onIndexChanged(IndexChangedEvent event) {
				throw new ReceivedEventMarkerException();
			}
		};
