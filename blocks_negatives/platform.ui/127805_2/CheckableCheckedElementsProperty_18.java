	public IObservableSet observe(Object source) {
		if (source instanceof Viewer) {
			return observe(DisplayRealm.getRealm(((Viewer) source)
					.getControl().getDisplay()), source);
		}
		return super.observe(source);
