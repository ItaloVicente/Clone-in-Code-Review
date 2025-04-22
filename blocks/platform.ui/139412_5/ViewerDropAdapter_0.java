	private int getThreshold() {
		if (!selectFeedbackEnabled) {
			if (getViewer().getControl() instanceof Table)
				return ((Table) getViewer().getControl()).getItemHeight() / 2;

			if (getViewer().getControl() instanceof Tree)
				return ((Tree) getViewer().getControl()).getItemHeight() / 2;

			if (getViewer().getControl() instanceof List)
				return ((List) getViewer().getControl()).getItemHeight() / 2;
		}

		return 5;
	}

