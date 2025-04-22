
	static IHyperlinkListener linkEnteredAdapter(Consumer<HyperlinkEvent> consumer) {
		Objects.requireNonNull(consumer);
		return new HyperlinkAdapter() {
			@Override
			public void linkEntered(HyperlinkEvent e) {
				consumer.accept(e);
			}
		};
	}

	static IHyperlinkListener linkExitedAdapter(Consumer<HyperlinkEvent> consumer) {
		Objects.requireNonNull(consumer);
		return new HyperlinkAdapter() {
			@Override
			public void linkExited(HyperlinkEvent e) {
				consumer.accept(e);
			}
		};
	}

	static IHyperlinkListener linkActivatedAdapter(Consumer<HyperlinkEvent> consumer) {
		Objects.requireNonNull(consumer);
		return new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				consumer.accept(e);
			}
		};
	}
}
