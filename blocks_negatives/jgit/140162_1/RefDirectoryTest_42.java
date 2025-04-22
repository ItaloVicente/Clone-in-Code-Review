		newRepo.getListenerList().addRefsChangedListener(
				new RefsChangedListener() {

					@Override
					public void onRefsChanged(RefsChangedEvent event) {
						try {
							refDb.getRefsByPrefix("ref");
							changeCount.incrementAndGet();
						} catch (StackOverflowError soe) {
							error.set(soe);
						} catch (IOException ioe) {
							exception.set(ioe);
						}
					}
				});
