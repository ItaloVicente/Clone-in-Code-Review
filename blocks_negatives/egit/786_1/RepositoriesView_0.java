
	public void indexChanged(IndexChangedEvent e) {
		scheduleRefresh();
	}

	public void refsChanged(RefsChangedEvent e) {
		scheduleRefresh();
	}

