		}

		if (widget instanceof Widget) {
			Widget swtWidget = (Widget) widget;
			if (swtWidget.isDisposed()) {
				return;
			}
		}
