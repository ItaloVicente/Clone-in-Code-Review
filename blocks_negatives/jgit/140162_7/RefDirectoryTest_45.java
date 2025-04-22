				.addRefsChangedListener(new RefsChangedListener() {

					@Override
					public void onRefsChanged(RefsChangedEvent event) {
						count[0]++;
					}
