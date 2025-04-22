		if (active) {
			if (handler instanceof IHandler2) {
				((IHandler2) handler).setEnabled(context);
			}
			if (!handler.isEnabled()) {
				active = false;
			}
		}
