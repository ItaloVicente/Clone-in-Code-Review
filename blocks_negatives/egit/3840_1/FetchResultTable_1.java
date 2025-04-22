		tableViewer.setContentProvider(new TrackingRefUpdateContentProvider());
		tableViewer.setInput(null);

		createTableColumns();
	}

	void setData(final Repository db, final FetchResult fetchResult) {
		tableViewer.setInput(null);
		this.reader = db.newObjectReader();
		this.abbrevations = new HashMap<ObjectId, String>();
		tableViewer.setInput(fetchResult);
	}

	Control getControl() {
		return tablePanel;
	}
