		IPageChangedListener changedListener = new IPageChangedListener() {
			@Override
			public void pageChanged(PageChangedEvent event) {
				pageChanged = true;
			}

		};

		IPageChangingListener changingListener = new IPageChangingListener() {
			@Override
			public void handlePageChanging(PageChangingEvent event) {
				assertEquals("Page should not have changed yet", false, pageChanged);
				pageChangingFired = true;
			}
