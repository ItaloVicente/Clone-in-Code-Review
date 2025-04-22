			if (!isDisposed()) {
				updateStats();
				if (hasChanged) {
					if (updateTooltip) {
						updateToolTip();
					}
					redraw();
					hasChanged = false;
				}
				getDisplay().timerExec(updateInterval, this);
			}
		}
	};

	private final IPropertyChangeListener prefListener = event -> {
