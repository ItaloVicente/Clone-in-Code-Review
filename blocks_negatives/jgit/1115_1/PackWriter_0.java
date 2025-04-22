	private void searchForDeltas(ProgressMonitor monitor,
			ObjectToPack[] list, int cnt) throws MissingObjectException,
			IncorrectObjectTypeException, LargeObjectException, IOException {
		DeltaCache dc = new DeltaCache(this);
		DeltaWindow dw = new DeltaWindow(this, dc, reader);
		dw.search(monitor, list, 0, cnt);
