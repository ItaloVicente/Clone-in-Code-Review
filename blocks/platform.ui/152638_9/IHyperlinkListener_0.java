
	static IHyperlinkListener linkEnteredAdapter(Consumer<HyperlinkEvent> consumer) {
		return new HyperlinkAdapter() {
			@Override
			public void linkEntered(HyperlinkEvent e) {
				consumer.accept(e);
			}
		};
	}

	static IHyperlinkListener linkExitedAdapter(Consumer<HyperlinkEvent> consumer) {
		return new HyperlinkAdapter() {
			@Override
			public void linkExited(HyperlinkEvent e) {
				consumer.accept(e);
			}
		};
	}

	static IHyperlinkListener linkActivatedAdapter(Consumer<HyperlinkEvent> c) {
		return new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				c.accept(e);
			}
		};
	}
