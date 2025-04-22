	private ISetChangeListener listener = new ISetChangeListener() {
		@Override
		public void handleSetChange(SetChangeEvent event) {
			for (Iterator it = event.diff.getAdditions().iterator(); it.hasNext();) {
				addListenerTo(it.next());
			}
			for (Iterator it = event.diff.getRemovals().iterator(); it.hasNext();) {
				removeListenerFrom(it.next());
			}
