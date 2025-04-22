
	static IExpansionListener expansionStateChangingAdapter(Consumer<ExpansionEvent> c) {
		return new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				c.accept(e);
			}
		};
	}

	static IExpansionListener expansionStateChangedAdapter(Consumer<ExpansionEvent> c) {
		return new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				c.accept(e);
			}
		};
	}
}
