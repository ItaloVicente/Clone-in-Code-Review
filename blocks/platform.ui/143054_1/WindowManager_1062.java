	public boolean close() {
		List<Window> t = new ArrayList<>(windows); // make iteration robust
		Iterator<Window> e = t.iterator();
		while (e.hasNext()) {
			Window window = e.next();
			boolean closed = window.close();
			if (!closed) {
