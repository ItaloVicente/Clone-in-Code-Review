    protected Listener fCancelListener = e -> {
	    setCanceled(true);
	    if (fCancelComponent != null) {
			fCancelComponent.setEnabled(false);
		}
	};
