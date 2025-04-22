	private ISetChangeListener listener = event -> {
		for (Iterator it1 = event.diff.getAdditions().iterator(); it1.hasNext();) {
			addListenerTo(it1.next());
		}
		for (Iterator it2 = event.diff.getRemovals().iterator(); it2.hasNext();) {
			removeListenerFrom(it2.next());
