	/**
	 * Post a label event to the LabelEventJob
	 *
	 * Posts a generic label event. No specific elements are provided; all
	 * decorations shall be invalidated. Same as
	 * <code>postLabelEvent(null, true)</code>.
	 */
	private void postLabelEvent() {
		LabelEventJob.getInstance().postLabelEvent(this);
	}

	void fireLabelEvent() {
		final LabelProviderChangedEvent event = new LabelProviderChangedEvent(
				this);
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				fireLabelProviderChanged(event);
			}
		});
	}

