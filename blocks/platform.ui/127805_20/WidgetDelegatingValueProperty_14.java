	@SuppressWarnings("unchecked")
	@Override
	public ISWTObservableValue<T> observe(Widget widget) {
		return (ISWTObservableValue<T>) observe(DisplayRealm.getRealm(widget.getDisplay()), (S) widget);
	}

