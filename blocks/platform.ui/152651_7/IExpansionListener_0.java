
	static IExpansionListener expansionStateChangingAdapter(Consumer<ExpansionEvent> consumer) {
		return new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				consumer.accept(e);
			}
		};
	}

	static IExpansionListener expansionStateChangedAdapter(Consumer<ExpansionEvent> consumer) {
		return new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(ExpansionEvent e) {
				consumer.accept(e);
			}
		};
	}
}
