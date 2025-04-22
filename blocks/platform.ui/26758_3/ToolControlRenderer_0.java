	private boolean isRestoreMenuShowable(MToolControl toolControl) {
		return toolControl.getTags().contains(SHOW_RESTORE_MENU);
	}

	private boolean isHideable(MToolControl toolControl) {
		return toolControl.getTags().contains(HIDEABLE);
	}

