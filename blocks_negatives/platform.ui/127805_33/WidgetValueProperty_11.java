		return new WidgetListener(this, listener, changeEvents, staleEvents);
	}

	@Override
	public IObservableValue observe(Object source) {
		if (source instanceof Widget) {
			return observe((Widget) source);
		}
		return super.observe(source);
