		synchronized (sortedEditorsFromOSSynchronizer) {
			if (sortedEditorsFromOS == null) {
				loadEditorsFromOS();
			}
			return sortedEditorsFromOS;
		}
	}

	public void loadEditorsFromOS() {
		synchronized (sortedEditorsFromOSSynchronizer) {
			sortedEditorsFromOS = getStaticSortedEditorsFromOS();
		}
