		if (keyEvent.character == '\r') { // Return key
			if (text != null && !text.isDisposed()
					&& (text.getStyle() & SWT.MULTI) != 0) {
				if ((keyEvent.stateMask & SWT.CTRL) != 0) {
					super.keyReleaseOccured(keyEvent);
				}
			}
			return;
		}
		super.keyReleaseOccured(keyEvent);
	}

	@Override
