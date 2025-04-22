
	static IHyperlinkListener linkEnteredAdapter(Consumer<HyperlinkEvent> c) {
		return new HyperlinkAdapter() {
			@Override
			public void linkEntered(HyperlinkEvent e) {
				c.accept(e);
			}
		};
	}

	static IHyperlinkListener linkExitedAdapter(Consumer<HyperlinkEvent> c) {
		return new HyperlinkAdapter() {
			@Override
			public void linkExited(HyperlinkEvent e) {
				c.accept(e);
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
