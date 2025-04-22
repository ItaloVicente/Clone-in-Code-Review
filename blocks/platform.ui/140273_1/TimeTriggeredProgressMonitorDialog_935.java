		BusyIndicator.showWhile(display, dialogWaitRunnable);
		if (invokes[0] != null) {
			throw invokes[0];
		}
		if (interrupt[0] != null) {
			throw interrupt[0];
		}
	}
