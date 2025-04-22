	public ISWTObservableList<E> observe(Widget widget) {
		return (ISWTObservableList<E>) observe(DisplayRealm.getRealm(widget.getDisplay()), (S) widget);
	}

	@Override
	public ISWTObservableList<E> observe(S widget) {
		return (ISWTObservableList<E>) super.observe(widget);
